package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.jobapplicationmanagement.domain.Ranking;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobapplicationmanagement.repository.RankingRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaRankingRepository extends JpaAutoTxRepository<Ranking, JobReference, JobReference>
        implements RankingRepository {

    public JpaRankingRepository(final TransactionalContext autoTx) {
        super(autoTx, "job reference");
    }

    public JpaRankingRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "jobReference");
    }


    @Override
    public Optional<Ranking> findByJobReference(JobReference jobReference) {
        final Map<String, Object> params = new HashMap<>();
        params.put("jobReference", jobReference);
        return matchOne("e.jobReference=:jobReference", params);
    }
}