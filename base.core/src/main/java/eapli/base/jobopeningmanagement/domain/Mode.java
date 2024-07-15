package eapli.base.jobopeningmanagement.domain;


import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class Mode implements ValueObject, Comparable<Mode>{

    private String mode;

    public Mode(final String mode) {
        this.mode = mode;
    }

    public Mode() {
        // for ORM only
    }

    public static Mode valueOf(String mode) {
        Preconditions.noneNull(mode, "Mode should not be null");
        return new Mode(mode); 
    }

    @Override
    public int compareTo(Mode o) {
        if(this.mode.equals(o.mode)) return 0;
        return -1;
    }

    @Override
    public String toString() { return mode; }
}
