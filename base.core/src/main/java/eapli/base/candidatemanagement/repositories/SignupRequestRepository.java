package eapli.base.candidatemanagement.repositories;
import eapli.base.usermanagement.domain.users.Email;
import eapli.base.candidatemanagement.domain.SignupRequest;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;

public interface SignupRequestRepository extends DomainRepository<EmailAddress, SignupRequest>{
    Iterable<SignupRequest> pendingSignupRequests();
}
