package eapli.base.persistence.impl.inmemory;

import eapli.base.jobapplicationmanagement.domain.Ranking;
import eapli.base.jobapplicationmanagement.repository.RankingRepository;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryRankingRepository extends InMemoryDomainRepository<Ranking, JobReference>
        implements RankingRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<Ranking> findByJobReference(JobReference jobReference) {
        return Optional.empty();
    }
}