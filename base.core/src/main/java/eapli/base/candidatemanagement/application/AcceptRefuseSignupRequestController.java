package eapli.base.candidatemanagement.application;

import eapli.base.candidatemanagement.domain.SignupRequest;
public interface AcceptRefuseSignupRequestController {

    SignupRequest acceptSignupRequest(SignupRequest theSignupRequest);

    SignupRequest refuseSignupRequest(SignupRequest theSignupRequest);

    Iterable<SignupRequest> listPendingSignupRequests();
}
