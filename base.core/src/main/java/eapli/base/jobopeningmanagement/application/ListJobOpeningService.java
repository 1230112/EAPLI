package eapli.base.jobopeningmanagement.application;

import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.ApplicationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

@ApplicationService
public class ListJobOpeningService {
    private final AuthorizationService authz;
    private final JobOpeningRepository jobOpeningRepository;

    public ListJobOpeningService(final AuthorizationService authz, final JobOpeningRepository jobOpeningRepository) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the repository. for
        // instance, unit testing.
        this.authz = authz;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    /**
     * @return
     */
    /*
    public Iterable<JobOpening> allJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.CUSTOMER_MANAGER);

        return jobOpeningRepository.findJobOpeningsAssignedToMeAnalysisPhase();
    }*/
}