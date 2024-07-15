package eapli.base.jobopeningmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.ApplicationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.ArrayList;
import java.util.List;

@ApplicationService
public class JobOpeningDTOService {

    private final AuthorizationService authz;
    private final JobOpeningRepository jobOpeningRepository;

    public JobOpeningDTOService(final AuthorizationService authz, final JobOpeningRepository jobOpeningRepository) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the repository. for
        // instance, unit testing.
        this.authz = authz;
        this.jobOpeningRepository = jobOpeningRepository;
    }
    public Iterable<JobOpeningDTO> allJobOpeningsAssignedToMeAnalysisPhase() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR);
        SystemUser currentUser =  authz.session().get().authenticatedUser();
        final Iterable<JobOpening> jobOpenings = this.jobOpeningRepository.findJobOpeningsAssignedToMeAnalysisPhase(currentUser);

        // transform for the presentation layer
        final List<JobOpeningDTO> ret = new ArrayList<>();
        jobOpenings.forEach(e -> ret.add(e.toDTO()));
        return ret;
    }
    public Iterable<JobOpeningDTO> listJobOpeningsWithInterviewAssignedToMeInScreeningOrInterviewPhase(SystemUser currentUser){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR);
        final Iterable<JobOpening> jobOpenings = this.jobOpeningRepository.findJobOpeningsWithInterviewAssignedToMeInScreeningOrInterviewPhase(currentUser);

        // transform for the presentation layer
        final List<JobOpeningDTO> ret = new ArrayList<>();
        jobOpenings.forEach(e -> ret.add(e.toDTO()));
        return ret;
    }
}