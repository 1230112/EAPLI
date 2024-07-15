package eapli.base.jobapplicationmanagement.repository;

import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface JobApplicationRepository extends DomainRepository<JobApplicationId, JobApplication> {
    Optional<JobApplication> findByJobApplicationId(JobApplicationId jobApplicationId);
    Iterable<JobApplication> findJobApplicationsAfterScreewningResultAndInterviewGradeIfItHasByJobReference(JobOpening jobOpening);
    Iterable<JobApplication> findJobApplicationsAfterScreewningResultByJobReferenceForInterview(JobOpening jobOpening);
}
