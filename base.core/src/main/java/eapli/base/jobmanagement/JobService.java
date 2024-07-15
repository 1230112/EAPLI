package eapli.base.jobmanagement;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.candidatemanagement.repositories.CandidateRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.domain.ScreeningResult;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();
    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
    private final CandidateRepository repo = PersistenceContext.repositories().candidates();

    public JobService() {
    }

    public Optional<JobApplication> findJobApplicationById(JobApplicationId jobApplicationId) {
        return this.jobApplicationRepository.findByJobApplicationId(jobApplicationId);
    }

    public Optional<JobOpening> findJobOpeningByReference(JobReference jobReference) {
        return this.jobOpeningRepository.findByJobReference(jobReference);
    }

    public Optional<Candidate> findCandidateById(String email) {
        return this.repo.findByEmail(EmailAddress.valueOf(email));
    }

    public boolean isCandidateCreated(String email) {
        return this.repo.findByEmail(EmailAddress.valueOf(email)).isPresent();
    }

    public JobApplication addScreeningResultAndJustification(JobApplication jobApplication, String screeningResult, String screeningJustification) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        jobApplication.addScreeningResultAndJustification(ScreeningResult.valueOf(screeningResult), screeningJustification);
        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication addFinalResult(JobApplication jobApplication, String finalResult) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        jobApplication.addFinalResult(finalResult);
        return jobApplicationRepository.save(jobApplication);
    }


}
