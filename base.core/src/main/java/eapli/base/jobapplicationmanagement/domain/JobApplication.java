package eapli.base.jobapplicationmanagement.domain;


import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.jobapplicationmanagement.DTO.JobApplicationDTO;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;

import java.io.File;

@Entity
public class JobApplication implements AggregateRoot<JobApplicationId> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @Column(nullable = false)
    private JobApplicationId jobApplicationId;

    @ManyToOne(optional = false)
    private JobOpening jobOpening;

    @ManyToOne(optional = false)
    private Candidate candidate;

    @Column(nullable = false)
    private File cv;

    @Column(nullable = true)
    private ScreeningResult screeningResult;

    @Column(nullable = true)
    private String screeningJustification;

    @Column(nullable = true)
    private String finalResult;

    public JobApplication(final JobApplicationId jobApplicationId, final JobOpening jobOpening, final Candidate candidate, final File cv, final ScreeningResult screeningResult, final String screeningJustification, final String finalResult) {
        this.jobApplicationId = jobApplicationId;
        this.jobOpening = jobOpening;
        this.candidate = candidate;
        this.cv = cv;
        this.screeningResult = screeningResult;
        this.screeningJustification = screeningJustification;
        this.finalResult = finalResult;
    }


    protected JobApplication() {
        // for ORM only
    }
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof JobApplication))
            return false;

        if (this == other)
            return true;

        final JobApplication that = (JobApplication) other;

        return this.jobApplicationId().equals(that.jobApplicationId);
    }

    public JobApplicationId jobApplicationId() {
        return this.jobApplicationId;
    }

    public void addScreeningResultAndJustification(ScreeningResult screeningResult, String screeningJustification) {
        Preconditions.noneNull(screeningResult, "Screening Result should not be null");
        Preconditions.noneNull(screeningJustification, "Screening Justification should not be null");
        this.screeningResult = screeningResult;
        this.screeningJustification = screeningJustification;
    }

    public void addFinalResult(String finalResult) {
        Preconditions.noneNull(finalResult, "Final Result should not be null");
        this.finalResult = finalResult;
    }
    public JobOpening jobOpening() {
        return this.jobOpening;
    }
    public String ScreeningResult() { return this.screeningResult.toString(); }

    public String ScreeningJustification() { return this.screeningJustification; }

    public String FinalResult() { return this.finalResult; }

    public JobApplicationDTO toDTO() {
        return new JobApplicationDTO(jobApplicationId.identity(), jobOpening.identity().toString(), candidate.identity().toString(), cv.toString(), screeningResult.toString(), screeningJustification, finalResult);

    }
    @Override
    public JobApplicationId identity() {
        return this.jobApplicationId;
    }

}