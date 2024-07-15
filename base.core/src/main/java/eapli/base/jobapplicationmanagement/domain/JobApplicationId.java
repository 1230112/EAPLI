package eapli.base.jobapplicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class JobApplicationId implements ValueObject, Comparable<JobApplicationId> {
    private static final long serialVersionUID = 1L;

    private int jobApplicationId;

    private JobApplicationId(int jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    public int identity() { return this.jobApplicationId; }

    @Override
    public int compareTo(JobApplicationId other) { return Integer.compare(jobApplicationId, other.jobApplicationId); }

    protected JobApplicationId() {
        // for ORM only
    }

    public static JobApplicationId valueOf(int jobApplicationId) {
        Preconditions.noneNull(jobApplicationId, "Job Application Id should not be null");
        Preconditions.isPositive(jobApplicationId, "Job Application Id should be positive");
        return new JobApplicationId(jobApplicationId);
    }

    @Override
    public String toString() {
        return jobApplicationId + "";
    }
    }