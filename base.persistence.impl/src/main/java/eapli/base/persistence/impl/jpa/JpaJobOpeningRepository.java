package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaJobOpeningRepository extends JpaAutoTxRepository<JobOpening, JobReference, JobReference>
        implements JobOpeningRepository {

    public JpaJobOpeningRepository(final TransactionalContext autoTx) {
        super(autoTx, "job reference");
    }

    public JpaJobOpeningRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "jobReference");
    }


    @Override
    public Optional<JobOpening> findByJobReference(JobReference jobReference) {
        final Map<String, Object> params = new HashMap<>();
        params.put("jobReference", jobReference);
        return matchOne("e.jobReference=:jobReference", params);
    }
    @Override
    public Iterable<JobOpening> findJobOpeningsAssignedToMeAnalysisPhase(SystemUser currentUser) {
        final TypedQuery<JobOpening> query = entityManager().createQuery(
                "SELECT jo FROM JobOpening jo WHERE jo.costumerManager = :currentUser AND NOT EXISTS (SELECT r FROM Ranking r WHERE r.jobOpening.jobReference = jo.jobReference)",
                JobOpening.class);

        query.setParameter("currentUser", currentUser);
        return query.getResultList();
    }
/**
 * ATENTION!!!
 * Function to find the job openings assigned to the user in the analysis phase if the
 * entities RecruimentProccess and RecruitmentProccessPhase were implemented
 *
 * @Override
 * public Iterable<JobOpening> findJobOpeningsAssignedToMeAnalysisPhase(SystemUser currentUser) {
 *     final TypedQuery<JobOpening> query = entityManager().createQuery(
 *             "SELECT jo FROM JobOpening jo " +
 *             "JOIN jo.recruitmentProcess rp " +
 *             "JOIN rp.recruitmentProcessPhases rpp " +
 *             "WHERE rpp.name = :phaseName " +
 *             "AND jo.costumerManager = :currentUser AND NOT EXISTS (SELECT r FROM Ranking r WHERE r.jobOpening.jobReference = jo.jobReference)" +
 *             "AND CURRENT_DATE BETWEEN rpp.startDate AND rpp.endDate",
 *             JobOpening.class);
 *
 *     query.setParameter("phaseName", "Analysis");
 *
 *     return query.getResultList();
 * }
 *
 */
@Override
public Iterable<JobOpening> findJobOpeningsWithInterviewAssignedToMeInScreeningOrInterviewPhase(SystemUser currentUser) {
        final TypedQuery<JobOpening> query = entityManager().createQuery(
                "SELECT jo FROM JobOpening jo WHERE jo.costumerManager = :currentUser",
                JobOpening.class);

        query.setParameter("currentUser", currentUser);
        return query.getResultList();
    }
}
/**
 * ATENTION!!!
 * Function to find the job openings assigned to the user in the screening phase or interview phase if the
 * entities RecruimentProccess and RecruitmentProccessPhase were implemented
 *
 * @Override
 * public Iterable<JobOpening> findJobOpeningsWithInterviewAssignedToMeInScreeningOrInterviewPhase(SystemUser currentUser) {
 *     final TypedQuery<JobOpening> query = entityManager().createQuery(
 *             "SELECT jo FROM JobOpening jo " +
 *             "JOIN jo.recruitmentProcess rp " +
 *             "JOIN rp.recruitmentProcessPhases rpp " +
 *             "WHERE (rpp.name = :phaseName1 OR rpp.name = :phaseName2) " +
 *             "AND jo.costumerManager = :currentUser " +
 *             "AND CURRENT_DATE BETWEEN rpp.startDate AND rpp.endDate" +
 *             "AND EXISTS (SELECT 1 FROM RecruitmentProcessPhase rpp2 WHERE rpp2.name = :phaseName2)",
 *             JobOpening.class);
 *
 *     query.setParameter("phaseName1", "Screening");
 *     query.setParameter("phaseName2", "Interview");
 *     query.setParameter("currentUser", currentUser);
 *
 *     return query.getResultList();
 * }
 *
 */