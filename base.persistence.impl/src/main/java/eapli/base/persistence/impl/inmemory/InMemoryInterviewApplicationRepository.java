package eapli.base.persistence.impl.inmemory;

import eapli.base.interviewmanagement.domain.InterviewApplication;
import eapli.base.interviewmanagement.repository.InterviewApplicationRepository;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryInterviewApplicationRepository extends InMemoryDomainRepository<InterviewApplication, JobApplicationId>
        implements InterviewApplicationRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<InterviewApplication> findByJobApplicationId(JobApplicationId jobReference) {
        return Optional.empty();
    }
    @Override
    public Iterable<InterviewApplication> findInterviewApplicationsByCustomerManager(SystemUser currentUser) {
        throw new UnsupportedOperationException();
    }
}