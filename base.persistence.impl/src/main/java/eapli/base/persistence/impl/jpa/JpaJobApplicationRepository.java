package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.domain.ScreeningResult;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaJobApplicationRepository extends JpaAutoTxRepository<JobApplication, JobApplicationId, JobApplicationId>
        implements JobApplicationRepository {

    public JpaJobApplicationRepository(final TransactionalContext autoTx) {
        super(autoTx, "jobApplicationId");
    }

    public JpaJobApplicationRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "jobApplicationId");
    }


    @Override
    public Optional<JobApplication> findByJobApplicationId(JobApplicationId jobApplicationId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("jobApplicationId", jobApplicationId);
        return matchOne("e.jobApplicationId=:jobApplicationId", params);
    }
    @Override
    public Iterable<JobApplication> findJobApplicationsAfterScreewningResultAndInterviewGradeIfItHasByJobReference(JobOpening jobOpening) {
        final TypedQuery<JobApplication> query = entityManager().createQuery(
                "SELECT d1 FROM JobApplication d1 WHERE d1.jobOpening= :jobOpening AND d1.screeningResult = :screeningResult AND d1.jobApplicationId NOT IN (SELECT d2.jobApplication.jobApplicationId FROM InterviewApplication d2)",
                JobApplication.class);

        query.setParameter("jobOpening", jobOpening);
        query.setParameter("screeningResult", ScreeningResult.ACCEPTED);
        return query.getResultList();
    }
    /**
     * ATENTION!!! Function to find the job applications after the screening result AND AFTER BEING GRADE I THE INTERVIEW IF THIS PHASE EXIST
     * by job reference. I dont have the classes with the phases of the recruitment process, so I have to comment it
     * @Override
     * public Iterable<JobApplication> findJobApplicationsAfterScreewningResultAndInterviewGradeIfItHasByJobReference(JobOpening jobOpening) {
     *     final TypedQuery<JobApplication> query = entityManager().createQuery(
     *             "SELECT d1 FROM JobApplication d1 WHERE d1.jobOpening= :jobOpening
     *             AND d1.screeningResult = :screeningResult
     *             AND d1.jobApplicationId NOT IN (SELECT d2.jobApplication.jobApplicationId FROM InterviewApplication d2
     *             WHERE CASE WHEN EXISTS (SELECT rp FROM RecruitmentProcess rp JOIN RecruitmentProcessPhase rpp
     *             WHERE rpp.name = :phaseName AND rp.jobOpening = :jobOpening) THEN d2.grade IS NOT NULL ELSE TRUE END)",
     *             JobApplication.class);
     *
     *     query.setParameter("jobOpening", jobOpening);
     *     query.setParameter("phaseName", "Interview");
     *     query.setParameter("screeningResult", ScreeningResult.ACCEPTED);
     *     return query.getResultList();
     * }
    * */
    @Override
    public Iterable<JobApplication> findJobApplicationsAfterScreewningResultByJobReferenceForInterview(JobOpening jobOpening) {
        final TypedQuery<JobApplication> query = entityManager().createQuery(
                "SELECT d1 FROM JobApplication d1 WHERE d1.jobOpening= :jobOpening AND d1.screeningResult = :screeningResult AND d1.jobApplicationId NOT IN (SELECT d2.jobApplication.jobApplicationId FROM InterviewApplication d2)",
                JobApplication.class);

        query.setParameter("jobOpening", jobOpening);
        query.setParameter("screeningResult", ScreeningResult.ACCEPTED);
        return query.getResultList();
    }
}