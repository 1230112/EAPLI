package eapli.base.usermanagement.domain.users;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Email implements ValueObject, Comparable<Email> {

    private String email;

    public Email(final String email){
        Preconditions.nonNull(email);
        this.email = validateEmail(email);
    }

    protected Email(){
        // for ORM
    }

    public static Email valueOf(final String email){ return new Email(email); }

    private String validateEmail(final String email){
        String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        if(!m.matches()){
            throw new IllegalArgumentException("Invalid email address!!!");
        }
        return email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Email)) { return false; }
        final Email that = (Email) o;
        return this.email.equals(that.email);
    }

    @Override
    public int hashCode() { return email.hashCode(); }
    @Override
    public String toString() { return email; }
    @Override
    public int compareTo(final Email o) { return email.compareTo(o.email); }
}
