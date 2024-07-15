package eapli.base.jobopeningmanagement.repositories;

import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.domain.repositories.LockableDomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Optional;

public interface JobOpeningRepository extends DomainRepository<JobReference, JobOpening>, LockableDomainRepository<JobReference, JobOpening> {


    Optional<JobOpening> findByJobReference(JobReference jobReference);
    Iterable<JobOpening> findJobOpeningsAssignedToMeAnalysisPhase(SystemUser currentUser);
    Iterable<JobOpening> findJobOpeningsWithInterviewAssignedToMeInScreeningOrInterviewPhase(SystemUser currentUser);
}
