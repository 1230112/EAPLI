package eapli.base.candidatemanagement.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;
import eapli.base.usermanagement.domain.users.Email;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.candidatemanagement.DTO.CandidateDTO;
import eapli.base.candidatemanagement.repositories.CandidateRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.ApplicationService;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

@ApplicationService
public class CandidateService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CandidateRepository repo = PersistenceContext.repositories().candidates();

    public Optional<CandidateDTO> findCandidateUserByEmail(final EmailAddress email) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.OPERATOR, BaseRoles.CUSTOMER_MANAGER);
        final Optional<Candidate> candidates = repo.ofIdentity(email);
        return convertToDto(candidates);
    }



    public Iterable<ListCandidatesDTO> findAll() {
        final Iterable<Candidate> candidates = repo.findAll();
        List<ListCandidatesDTO> sortedCandidates = StreamSupport.stream(candidates.spliterator(), false)
                .filter(c -> c.appStatus().toString().equals("ENABLE"))
                .map(Candidate::toListDto)
                .sorted(Comparator.comparing(c -> c.getName().firstName(), String::compareToIgnoreCase))
                .collect(Collectors.toList());
        return sortedCandidates;
    }

    private Iterable<ListCandidatesDTO> convertToDto(Iterable<Candidate> candidates) {
        return StreamSupport.stream(candidates.spliterator(), true)
                .map(Candidate::toListDto)
                .collect(Collectors.toUnmodifiableList());

    }

    public Optional<Candidate> userOfIdentity(final EmailAddress id) {
        return repo.ofIdentity(id);
    }
private Optional<CandidateDTO> convertToDto(Optional<Candidate> candidate) {
    return candidate.map(Candidate::toDto);
}

}