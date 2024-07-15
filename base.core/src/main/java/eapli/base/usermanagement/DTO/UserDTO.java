package eapli.base.usermanagement.DTO;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jakarta.persistence.Id;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String email;
    private String name;

    private Collection<Role> roles;

    public UserDTO( String email, String name, Collection<Role> roles) {

        this.email = email;
        this.name = name;
        this.roles = roles;
    }

    public UserDTO(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.roles = userDTO.getRoles();

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }



    public Collection<Role> getRoles() {
        return roles;
    }

    public static UserDTO from(SystemUser user) {
        return new UserDTO(user.email().toString(), user.name().toString(),
                new HashSet<>(user.roleTypes()));
    }

    @Override
    public String toString() {
        return "Email=" + email + ", Name=" + name + ", Role=" + roles;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashBuilder = new HashCodeBuilder().append(email).append(name).append(roles);

        return hashBuilder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (!(obj instanceof UserDTO))
            return false;

        UserDTO other = (UserDTO) obj;
        return other.hashCode() == this.hashCode();
    }
}
