package eapli.base.candidatemanagement.domain.events;

import eapli.base.candidatemanagement.domain.AppStatus;
import eapli.base.candidatemanagement.domain.RequirementsFile;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class NewUserRegisteredFromSignupEvent extends DomainEventBase {

    private static final long serialVersionUID = 1L;




    private final EmailAddress email;
    private final Username newUser;


    public NewUserRegisteredFromSignupEvent( final Username newUser,
            final EmailAddress email) {
        this.newUser = newUser;
        this.email = email;
    }


    public EmailAddress email() {
        return email;
    }
    public Username username() {
        return newUser;
    }

    @Override
    public String toString() {
        return "NewUserFromsignup(" + username() + ")";
    }

}
