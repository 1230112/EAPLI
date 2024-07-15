package eapli.base.candidatemanagement.application;

import java.util.Optional;

import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;
import eapli.base.candidatemanagement.domain.Candidate;
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
public class ListCandidatesController {

    private final AuthorizationService authz;
    private final CandidateService userSvc;

    public ListCandidatesController(final AuthorizationService authz, final CandidateService service) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the service. for
        // instance, unit testing.
        this.authz = authz;
        this.userSvc = service;
    }
    public Iterable<ListCandidatesDTO> getAll() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.OPERATOR);

        return userSvc.findAll();
    }

    public Optional<Candidate> find(final EmailAddress u) {
        return userSvc.userOfIdentity(u);
    }


}