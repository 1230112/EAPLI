package eapli.base.persistence.impl.inmemory;

import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryJobApplicationRepository extends InMemoryDomainRepository<JobApplication, JobApplicationId>
        implements JobApplicationRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<JobApplication> findByJobApplicationId(JobApplicationId jobReference) {
        return Optional.empty();
    }
    @Override
    public Iterable<JobApplication> findJobApplicationsAfterScreewningResultAndInterviewGradeIfItHasByJobReference(JobOpening jobOpening) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterable<JobApplication> findJobApplicationsAfterScreewningResultByJobReferenceForInterview(JobOpening jobOpening) {
        throw new UnsupportedOperationException();
    }
}
