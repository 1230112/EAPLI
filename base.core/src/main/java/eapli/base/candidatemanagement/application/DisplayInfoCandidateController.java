package eapli.base.candidatemanagement.application;

import java.util.Optional;

import eapli.base.candidatemanagement.DTO.CandidateDTO;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author losa
 */
@UseCaseController
public class DisplayInfoCandidateController{

    private final AuthorizationService authz;
    private final CandidateService userSvc;

    public DisplayInfoCandidateController(final AuthorizationService authz, final CandidateService service) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the service. for
        // instance, unit testing.
        this.authz = authz;
        this.userSvc = service;
    }
    public Optional<CandidateDTO> findCandidateUserByEmail(final EmailAddress email) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);

        return userSvc.findCandidateUserByEmail(email);
    }


}
