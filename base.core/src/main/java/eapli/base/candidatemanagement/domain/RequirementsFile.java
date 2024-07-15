package eapli.base.candidatemanagement.domain;



import jakarta.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import lombok.EqualsAndHashCode;
@Embeddable
@EqualsAndHashCode
public class  RequirementsFile implements ValueObject, Comparable<RequirementsFile> {
    private static final long serialVersionUID = 1L;

    private String requirementsFile;

    private RequirementsFile(String requirementsFile) {

        this.requirementsFile = requirementsFile;
    }

    @Override
    public int compareTo(RequirementsFile other) {
        return this.requirementsFile.compareTo(other.requirementsFile);
    }
    protected RequirementsFile() {
        // for ORM only
    }

    public static RequirementsFile valueOf(String requirementsFile) {
        return new RequirementsFile(requirementsFile);
    }

    @Override
    public String toString() {

        return requirementsFile;
    }
}