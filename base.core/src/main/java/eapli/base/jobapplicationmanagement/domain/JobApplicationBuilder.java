package eapli.base.jobapplicationmanagement.domain;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.domain.model.DomainFactory;

import java.io.File;

public class JobApplicationBuilder implements DomainFactory<JobApplication> {

    private JobApplicationId jobApplicationId;
    private JobOpening jobOpening;
    private Candidate candidate;
    private File cv;
    private ScreeningResult screeningResult;
    private String screeningJustification;
    private String finalResult;

    public JobApplicationBuilder withJobApplicationId(int jobApplicationId) {
        this.jobApplicationId = JobApplicationId.valueOf(jobApplicationId);
        return this;
    }

    public JobApplicationBuilder withJobApplicationId(JobApplicationId jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
        return this;
    }

    public JobApplicationBuilder withJobOpening(JobOpening jobOpening) {
        this.jobOpening = jobOpening;
        return this;
    }

    public JobApplicationBuilder withCandidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    public JobApplicationBuilder withCv(File cv) {
        this.cv = cv;
        return this;
    }

    public JobApplicationBuilder withScreeningResult(String screeningResult) {
        this.screeningResult = ScreeningResult.valueOf(screeningResult);
        return this;
    }

    public JobApplicationBuilder withScreeningResult(ScreeningResult screeningResult) {
        this.screeningResult = screeningResult;
        return this;
    }

    public JobApplicationBuilder withScreeningJustification(String screeningJustification) {
        this.screeningJustification = screeningJustification;
        return this;
    }

    public JobApplicationBuilder withFinalResult(String finalResult) {
        this.finalResult = finalResult;
        return this;
    }

    @Override
    public JobApplication build() {
        return new JobApplication(this.jobApplicationId, this.jobOpening, this.candidate, this.cv, this.screeningResult, this.screeningJustification, this.finalResult);
    }
}
