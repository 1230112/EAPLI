package eapli.base.interviewmanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.base.interviewmanagement.DTO.InterviewApplicationDTO;
import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;

import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.domain.RankOrder;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.base.interviewmanagement.application.InterviewApplicationService;
import java.io.File;
import java.util.*;
import java.time.Duration;
@Entity
public class InterviewApplication implements AggregateRoot<JobApplicationId> , DTOable<InterviewApplicationDTO> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    @Id
    @OneToOne(optional = false)
    private JobApplication jobApplication;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private DateTime dateTime;

    @Column(nullable = true)
    private Duration timeSpent;

    @Column(nullable = true)
    private File answersFile;

    @Column(nullable = true)
    private Grade grade;





    public InterviewApplication(final JobApplication jobApplication, final DateTime dateTime, final Duration timeSpent, final File answersFile, final Grade grade) {

        Preconditions.noneNull(jobApplication, dateTime);
        this.jobApplication = jobApplication;
        this.dateTime = dateTime;
        this.timeSpent = timeSpent;
        this.answersFile = answersFile;
        this.grade = grade;
    }

    protected InterviewApplication() {
        // for ORM only
    }



    public JobApplication jobApplication() {
        return this.jobApplication;
    }

    public DateTime dateTime() {
        return this.dateTime;
    }

    public Duration timeSpent() {
        return this.timeSpent;
    }

    public File answersFile() {
        return this.answersFile;
    }

    public Grade grade() {
        return this.grade;
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
        if (!(other instanceof InterviewApplication))
            return false;
        if (this == other)
            return true;


        final InterviewApplication that = (InterviewApplication) other;

        return this.jobApplication.sameAs(that.jobApplication()) && this.dateTime.equals(that.dateTime());
    }

    public InterviewApplicationDTO toDTO() {
        return new InterviewApplicationDTO(this.jobApplication.jobApplicationId().identity(), this.dateTime.toCalendar(), this.timeSpent.toString(), this.answersFile.toString(), this.grade.toInteger());
    }



    @Override
    public JobApplicationId identity() {
        return this.jobApplication.jobApplicationId();
    }
}
