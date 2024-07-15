package eapli.base.interviewmanagement.domain;

import eapli.base.candidatemanagement.domain.CandidateBuilder;
import eapli.base.candidatemanagement.domain.PhoneNumber;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.RankOrder;
import eapli.base.interviewmanagement.domain.InterviewApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.framework.domain.model.DomainFactory;

import java.io.File;
import java.time.Duration;
import java.util.Calendar;
import java.util.Set;

public class InterviewApplicationBuilder implements DomainFactory<InterviewApplication> {
    private InterviewApplication interviewApplication;
    //private JobReference jobReference;
    private JobApplication jobApplication;
    private DateTime dateTime;
    private Duration timeSpent;
    private File answersFile;
    private Grade grade;



    public InterviewApplicationBuilder withJobApplication(final JobApplication jobApplication) {
        this.jobApplication = jobApplication;
        return this;
    }

    public InterviewApplicationBuilder withDateTime(final DateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
    public InterviewApplicationBuilder withDateTime(final Calendar dateTime) {
        this.dateTime = DateTime.valueOf(dateTime);
        return this;
    }

    public InterviewApplicationBuilder withTimeSpent(final Duration timeSpent) {
        this.timeSpent = timeSpent;
        return this;
    }

    public InterviewApplicationBuilder withAnswersFile(final File answersFile) {
        this.answersFile = answersFile;
        return this;
    }

    public InterviewApplicationBuilder withGrade(final Grade grade) {
        this.grade = grade;
        return this;
    }

   public InterviewApplicationBuilder withGrade(final Integer grade) {
        this.grade = Grade.valueOf(grade);
        return this;
    }
    private InterviewApplication buildOrThrow() {
        if (interviewApplication != null) {
            return interviewApplication;
        }
        if (jobApplication != null ) {
            interviewApplication = new InterviewApplication(jobApplication, dateTime, timeSpent, answersFile, grade);
            return interviewApplication;
        } else {
            throw new IllegalStateException();
        }
    }






    @Override
    public InterviewApplication build() {
        final InterviewApplication ret = buildOrThrow();
        // make sure we will create a new instance if someone reuses this builder and do
        // not change
        // the previously built dish.
        interviewApplication = null;
        return ret;
    }
}