package eapli.base.interviewmanagement.repository;

import eapli.base.interviewmanagement.domain.InterviewApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Optional;

public interface InterviewApplicationRepository extends DomainRepository<JobApplicationId, InterviewApplication> {


    Optional<InterviewApplication> findByJobApplicationId(JobApplicationId jobApplicationId);
    Iterable<InterviewApplication> findInterviewApplicationsByCustomerManager(SystemUser currentUser);
}