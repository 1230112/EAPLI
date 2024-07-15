package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.interviewmanagement.domain.InterviewApplication;
import eapli.base.interviewmanagement.repository.InterviewApplicationRepository;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaInterviewApplicationRepository extends JpaAutoTxRepository<InterviewApplication, JobApplicationId, JobApplicationId>
        implements InterviewApplicationRepository {

    public JpaInterviewApplicationRepository(final TransactionalContext autoTx) {
        super(autoTx, "jobApplicationId");
    }

    public JpaInterviewApplicationRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "jobApplicationId");
    }


    @Override
    public Optional<InterviewApplication> findByJobApplicationId(JobApplicationId jobApplicationId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("jobApplicationId", jobApplicationId);
        return matchOne("e.jobApplicationId=:jobApplicationId", params);
    }

    @Override
    public Iterable<InterviewApplication> findInterviewApplicationsByCustomerManager(SystemUser currentUser){
        final TypedQuery<InterviewApplication> query = entityManager().createQuery(
                "SELECT ia FROM InterviewApplication ia " +
                        "JOIN ia.jobApplication ja " +
                        "JOIN ja.jobOpening jo " +
                        "WHERE jo.costumerManager = :currentUser",
                InterviewApplication.class);

        query.setParameter("currentUser", currentUser);
        return query.getResultList();
    }
}