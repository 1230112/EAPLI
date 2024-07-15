package eapli.base.jobmanagement;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.candidatemanagement.domain.PhoneNumber;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationBuilder;
import eapli.base.jobapplicationmanagement.domain.ScreeningResult;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.usermanagement.application.AddUserController;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.File;
import java.util.*;

public class AddJobApplicationController {
    private final AuthorizationService authz;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobService jobService;
    private final AddUserController addUserController = new AddUserController(AuthzRegistry.authorizationService(), AuthzRegistry.userService(), PersistenceContext.repositories().candidates(), PersistenceContext.repositories().customers());


    public AddJobApplicationController(AuthorizationService authz, JobApplicationRepository jobApplicationRepository, JobService jobService) {
        this.authz = authz;
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobService = jobService;
    }

    public JobApplication addJobApplication(List<String> candidateDataList, List<String> ids, String cvPath) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.OPERATOR);

        String jobReference = ids.get(0);
        int jobApplicationId = Integer.parseInt(ids.get(1));
        String email = candidateDataList.get(1);
        List<String> names = List.of(candidateDataList.get(2).split(" "));
        String firstName = names.get(0);
        String lastName = names.get(1);

        try {
            EmailAddress.valueOf(email);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Email Address");
        }

        try {
            PhoneNumber.valueOf(candidateDataList.get(3));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Phone Number");
        }


        Optional<JobOpening> jo = jobService.findJobOpeningByReference(JobReference.valueOf(jobReference));
        if (jo.isEmpty()) {
            throw new IllegalArgumentException("Job Opening not found");
        }

        Optional<Candidate> candidate = jobService.findCandidateById(email);
        if (candidate.isEmpty()) {
            System.out.println("Candidate not found, creating new candidate...");
            System.out.println("-----------------------------");
            System.out.println("Email: " + email);
            System.out.printf("Name: %s %s\n", firstName, lastName);
            System.out.println("Phone Number: " + candidateDataList.get(3));
            candidate = Optional.of(addUserController.addCandidate(email, firstName, lastName, null, candidateDataList.get(3)));
            System.out.println("Candidate created successfully!");
        }

        File cv;
        try {
            cv = new File(cvPath);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CV path");
        }

        JobApplication ja = new JobApplicationBuilder().withJobApplicationId(jobApplicationId).withJobOpening(jo.get()).withCandidate(candidate.get()).withCv(cv).withScreeningResult(ScreeningResult.WAITING).build();
        return jobApplicationRepository.save(ja);
    }

    public JobApplication addJobApplication(final String jobReference, final String email, final String cvPath, final String screeningResult, final int jobApplicationId) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.OPERATOR);

        Optional<JobOpening> jo = jobService.findJobOpeningByReference(JobReference.valueOf(jobReference));
        if (jo.isEmpty()) {
            throw new IllegalArgumentException("Job Opening not found");
        }

        Optional<Candidate> candidate = jobService.findCandidateById(email);
        if (candidate.isEmpty()) {
            throw new IllegalArgumentException("Candidate not found");
        }

        File cv;
        try {
            cv = new File(cvPath);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CV path");
        }

        JobApplication ja = new JobApplicationBuilder().withJobApplicationId(jobApplicationId).withJobOpening(jo.get()).withCandidate(candidate.get()).withCv(cv).withScreeningResult(screeningResult).build();
        return jobApplicationRepository.save(ja);
    }

    public boolean isCandidateCreated(String email) {
        return jobService.isCandidateCreated(email);
    }
}
