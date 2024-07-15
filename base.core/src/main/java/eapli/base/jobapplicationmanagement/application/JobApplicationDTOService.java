package eapli.base.jobapplicationmanagement.application;

import eapli.base.jobapplicationmanagement.DTO.JobApplicationDTO;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.ApplicationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

import java.util.ArrayList;
import java.util.List;

@ApplicationService
public class JobApplicationDTOService {

    private final AuthorizationService authz;
    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationDTOService(final AuthorizationService authz, final JobApplicationRepository jobApplicationRepository) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the repository. for
        // instance, unit testing.
        this.authz = authz;
        this.jobApplicationRepository = jobApplicationRepository;
    }
    public Iterable<JobApplicationDTO> listJobApplicationsAfterScreewningResultByJobReference(final JobOpening jobOpening) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR);

        final Iterable<JobApplication> jobApplications = this.jobApplicationRepository.findJobApplicationsAfterScreewningResultByJobReferenceForInterview(jobOpening);

        // transform for the presentation layer
        final List<JobApplicationDTO> ret = new ArrayList<>();
        jobApplications.forEach(e -> ret.add(e.toDTO()));
        return ret;
    }
    public Iterable<JobApplicationDTO> allJobApplicationsAfterScreewningResultByJobReference(final JobOpening jobOpening) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR);

        final Iterable<JobApplication> jobApplications = this.jobApplicationRepository.findJobApplicationsAfterScreewningResultAndInterviewGradeIfItHasByJobReference(jobOpening);

        // transform for the presentation layer
        final List<JobApplicationDTO> ret = new ArrayList<>();
        jobApplications.forEach(e -> ret.add(e.toDTO()));
        return ret;
    }
}
