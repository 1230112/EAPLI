


package candidatemanagement;

import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;
import eapli.base.candidatemanagement.application.CandidateService;
import eapli.base.candidatemanagement.application.ListCandidatesController;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import eapli.framework.infrastructure.authz.domain.model.Name;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ListCandidatesControllerTest {
    private ListCandidatesController controller;
    private CandidateService candidateService;
    private AuthorizationService authzRegistry;
    @BeforeEach
    public void setUp() {
        authzRegistry = Mockito.mock(AuthorizationService.class);
        candidateService = Mockito.mock(CandidateService.class);
        controller = new ListCandidatesController(authzRegistry, candidateService);

    }
    @Test
    public void testGetAllCandidates() {
        ListCandidatesDTO candidate1 = new ListCandidatesDTO();
        EmailAddress email1 = EmailAddress.valueOf("hola@gmail.com");
        EmailAddress email2 = EmailAddress.valueOf("hola2@gmail.com");
        Name name1 = Name.valueOf("juan", "perez");
        Name name2 = Name.valueOf("juana", "perez");
        candidate1.setEmail(email1);
        candidate1.setName(name1);
        ListCandidatesDTO candidate2 = new ListCandidatesDTO();
        candidate2.setEmail(email2);
        candidate2.setName(name2);

        Iterable<ListCandidatesDTO> candidates = Arrays.asList(candidate1, candidate2);

        when(candidateService.findAll()).thenReturn(candidates);

        Iterable<ListCandidatesDTO> result = controller.getAll();
        assertEquals(candidates, result);
    }
    @Test
    @DisplayName("Times called should be 1")
    void shouldReturnAllCandidates() {
        when(candidateService.findAll()).thenReturn(mock(Iterable.class));
        candidateService.findAll();
        verify(candidateService, times(1)).findAll();
    }
}


