package eapli.base.candidatemanagement.domain.events;

import eapli.base.candidatemanagement.domain.AppStatus;
import eapli.base.candidatemanagement.domain.RequirementsFile;
import eapli.base.candidatemanagement.domain.SignupRequest;
import eapli.base.usermanagement.domain.users.Email;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class SignupAcceptedEvent extends DomainEventBase {

    private static final long serialVersionUID = 1L;

    private final SignupRequest theSignupRequest;

    public SignupAcceptedEvent(final SignupRequest theSignupRequest) {
        this.theSignupRequest = theSignupRequest;
    }

    public EmailAddress email() {
        return theSignupRequest.email();
    }

    public Password password() {
        return theSignupRequest.password();
    }

    public Name name() {
        return theSignupRequest.name();
    }

    public Username username() {
        return theSignupRequest.username();
    }

    public AppStatus appStatus() {
        return theSignupRequest.appStatus();
    }

    public RequirementsFile requirementsFile() {
        return theSignupRequest.requirementsFile();
    }


    @Override
    public String toString() {
        return "SignupAccepted(" + email() + ")";
    }
}
