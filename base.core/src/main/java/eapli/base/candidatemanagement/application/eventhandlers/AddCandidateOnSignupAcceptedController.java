package eapli.base.candidatemanagement.application.eventhandlers;

import java.util.Optional;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.candidatemanagement.domain.CandidateBuilder;
import eapli.base.candidatemanagement.domain.events.NewUserRegisteredFromSignupEvent;
import eapli.base.candidatemanagement.repositories.CandidateRepository;
import eapli.framework.functional.Functions;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
class AddCandidateOnSignupAcceptedController {
    private final UserRepository repo = PersistenceContext.repositories().users();
    private final CandidateRepository candidatesRepository = PersistenceContext.repositories().candidates();

    public Candidate addCandidate(final NewUserRegisteredFromSignupEvent event) {
        final Optional<SystemUser> newUser = findCandidate(event);
        return newUser.map(u -> createCandidate(event, u)).orElseThrow(IllegalStateException::new);
    }

    private Candidate createCandidate(final NewUserRegisteredFromSignupEvent event, SystemUser u) {
        final var candidate = new CandidateBuilder()
                .withEmail(event.email()).withSystemUser(u).build();
        return candidatesRepository.save(candidate);
    }

    @SuppressWarnings("squid:S1488")
    private Optional<SystemUser> findCandidate(final NewUserRegisteredFromSignupEvent event) {
        // since we are using events, the actual user may not yet be
        // created, so lets give it a time and wait
        final Optional<SystemUser> newUser = Functions.retry(() -> repo.ofIdentity(event.username()), 500, 30);
        return newUser;
    }
}