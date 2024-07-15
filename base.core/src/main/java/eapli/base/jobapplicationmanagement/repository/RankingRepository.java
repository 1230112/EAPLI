package eapli.base.jobapplicationmanagement.repository;

import java.util.Optional;

import eapli.base.jobapplicationmanagement.domain.Ranking;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.framework.domain.repositories.DomainRepository;

public interface RankingRepository extends DomainRepository<JobReference, Ranking>{


    Optional<Ranking> findByJobReference(JobReference jobReference);

}