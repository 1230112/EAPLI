package eapli.base.candidatemanagement.application;

import java.util.HashSet;
import java.util.Set;


import eapli.base.candidatemanagement.repositories.SignupRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.candidatemanagement.domain.SignupRequest;
import eapli.base.candidatemanagement.domain.CandidateBuilder;
import eapli.base.candidatemanagement.repositories.CandidateRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

@UseCaseController
public class AcceptRefuseSignupRequestControllerTxImpl
        implements AcceptRefuseSignupRequestController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userService = AuthzRegistry.userService();

    private final TransactionalContext txCtx = PersistenceContext.repositories().newTransactionalContext();
    private final CandidateRepository candidateRepository = PersistenceContext.repositories().candidates(txCtx);
    private final SignupRequestRepository signupRequestsRepository = PersistenceContext.repositories()
            .signupRequests(txCtx);
    /*
     * (non-Javadoc)
     *
     * @see eapli.base.candidatemanagement.application.
     * AcceptRefuseSignupRequestController#acceptSignupRequest(eapli.base.
     * candidatemanagement.domain.SignupRequest)
     */
    @Override
    public SignupRequest acceptSignupRequest(SignupRequest theSignupRequest) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.ADMIN, BaseRoles.OPERATOR);
        if (theSignupRequest == null) {
            throw new IllegalArgumentException();
        }

        // explicitly begin a transaction
        txCtx.beginTransaction();

        final SystemUser newUser = createSystemUserForCandidate(theSignupRequest);
        createCandidate(theSignupRequest, newUser);
        theSignupRequest = acceptTheSignupRequest(theSignupRequest);

        // explicitly commit the transaction
        txCtx.commit();

        return theSignupRequest;
    }

    private SignupRequest acceptTheSignupRequest(final SignupRequest theSignupRequest) {
        theSignupRequest.accept();
        return this.signupRequestsRepository.save(theSignupRequest);
    }

    private void createCandidate(final SignupRequest theSignupRequest, final SystemUser newUser) {
        final CandidateBuilder candidateBuilder = new CandidateBuilder();
        candidateBuilder.withEmail(theSignupRequest.email())
                .withSystemUser(newUser);
        candidateRepository.save(candidateBuilder.build());
    }

    //
    // add system user
    //
    private SystemUser createSystemUserForCandidate(final SignupRequest theSignupRequest) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.CANDIDATE);
        return userService.registerUser(theSignupRequest.username(), theSignupRequest.password(),
                theSignupRequest.name(), theSignupRequest.email(), roles);
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.ecourse.candidatemanagement.application.
     * AcceptRefuseSignupRequestController#refuseSignupRequest(eapli.ecourse.
     * .candidatemanagement.domain.SignupRequest)
     */
    @Override
    public SignupRequest refuseSignupRequest(SignupRequest theSignupRequest) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.ADMIN, BaseRoles.OPERATOR);

        if (theSignupRequest == null) {
            throw new IllegalArgumentException();
        }

        // explicitly begin a transaction
        txCtx.beginTransaction();

        theSignupRequest.refuse();
        theSignupRequest = signupRequestsRepository.save(theSignupRequest);

        // explicitly commit the transaction
        txCtx.commit();

        return theSignupRequest;
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.base.candidatemanagement.application.
     * AcceptRefuseSignupRequestController#listPendingSignupRequests()
     */
    @Override
    public Iterable<SignupRequest> listPendingSignupRequests() {
        return signupRequestsRepository.pendingSignupRequests();
    }
}