package eapli.base.jobopeningmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class JobReference implements ValueObject, Comparable<JobReference> {
    private static final long serialVersionUID = 1L;

    private String jobReference;

    private JobReference(String jobReference) {
        this.jobReference = jobReference;
    }
    @Override
    public int compareTo(JobReference other) {
        return this.jobReference.compareTo(other.jobReference);
    }

    protected JobReference() {
        // for ORM only
    }

    public static JobReference valueOf(String jobReference) {
        Preconditions.nonEmpty(jobReference, "Job Reference should not be empty");
        Preconditions.ensure(StringPredicates.isSingleWord(jobReference), "Job Reference should have no spaces");
        return new JobReference(jobReference);
    }

    @Override
    public String toString() {
        return jobReference;
    }
    }