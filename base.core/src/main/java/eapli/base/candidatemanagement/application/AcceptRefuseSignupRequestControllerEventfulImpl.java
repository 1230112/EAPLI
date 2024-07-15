package eapli.base.candidatemanagement.application;

import eapli.base.candidatemanagement.domain.SignupRequest;
import eapli.base.candidatemanagement.domain.events.SignupAcceptedEvent;
import eapli.base.candidatemanagement.repositories.SignupRequestRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.validations.Preconditions;
import lombok.RequiredArgsConstructor;
@UseCaseController
public class AcceptRefuseSignupRequestControllerEventfulImpl
        implements AcceptRefuseSignupRequestController {

    private final SignupRequestRepository signupRequestsRepository;
    private final AuthorizationService authorizationService;
    private final EventPublisher dispatcher;

    /**
     * Constructor.
     * <p>
     * We are using constructor dependency injection to facilitate testing of this
     * controller.
     * <p>
     * This boilerplate code could be avoided by leveraging Lombok's
     * {@link RequiredArgsConstructor}
     *
     * @param signupRequestsRepository
     * @param authorizationService
     * @param dispatcher
     */
    public AcceptRefuseSignupRequestControllerEventfulImpl(
            final SignupRequestRepository signupRequestsRepository,
            final AuthorizationService authorizationService, final EventPublisher dispatcher) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the
        // controller
        // can be used in different scenarios with different implementations of the
        // repository. for
        // instance, unit testing.
        this.signupRequestsRepository = signupRequestsRepository;
        this.authorizationService = authorizationService;
        this.dispatcher = dispatcher;
    }

    @Override
    @SuppressWarnings("squid:S1226")
    public SignupRequest acceptSignupRequest(SignupRequest theSignupRequest) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.ADMIN, BaseRoles.OPERATOR);

        Preconditions.nonNull(theSignupRequest);

        theSignupRequest = markSignupRequestAsAccepted(theSignupRequest);
        return theSignupRequest;
    }

    /**
     * Modify Signup Request to accepted.
     *
     * @param theSignupRequest
     *
     * @return
     *
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    @SuppressWarnings("squid:S1226")
    private SignupRequest markSignupRequestAsAccepted(SignupRequest theSignupRequest) {
        // do just what is needed in the scope of this use case
        theSignupRequest.accept();
        theSignupRequest = signupRequestsRepository.save(theSignupRequest);

        // notify interested parties (if any)
        final DomainEvent event = new SignupAcceptedEvent(theSignupRequest);
        dispatcher.publish(event);

        return theSignupRequest;
    }

    @Override
    public SignupRequest refuseSignupRequest(final SignupRequest theSignupRequest) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.ADMIN, BaseRoles.OPERATOR);

        Preconditions.nonNull(theSignupRequest);

        theSignupRequest.refuse();
        return signupRequestsRepository.save(theSignupRequest);
    }

    /**
     * @return
     */
    @Override
    public Iterable<SignupRequest> listPendingSignupRequests() {
        return signupRequestsRepository.pendingSignupRequests();
    }
}
