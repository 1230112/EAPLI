package eapli.base.candidatemanagement.repositories;

import java.util.Optional;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.base.usermanagement.domain.users.Email;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Username;

public interface CandidateRepository extends DomainRepository<EmailAddress, Candidate>{


    Optional<Candidate> findByEmail(EmailAddress email);
    Optional<Candidate> findByUsername(Username name);
    Iterable<Candidate> findAllActive();
}
