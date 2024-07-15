package eapli.base.persistence.impl.inmemory;

import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryJobOpeningRepository extends InMemoryDomainRepository<JobOpening, JobReference>
        implements JobOpeningRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<JobOpening> findByJobReference(JobReference jobReference) {
        return Optional.empty();
    }
    @Override
    public Iterable<JobOpening> findJobOpeningsAssignedToMeAnalysisPhase(SystemUser currentUser) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterable<JobOpening> findJobOpeningsWithInterviewAssignedToMeInScreeningOrInterviewPhase(SystemUser currentUser) {
        throw new UnsupportedOperationException();
    }
}
