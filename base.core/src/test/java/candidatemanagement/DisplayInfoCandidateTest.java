package candidatemanagement;



import eapli.base.candidatemanagement.DTO.CandidateDTO;
import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;
import eapli.base.candidatemanagement.application.CandidateService;
import eapli.base.candidatemanagement.application.DisplayInfoCandidateController;
import eapli.base.candidatemanagement.application.ListCandidatesController;
import eapli.base.candidatemanagement.domain.PhoneNumber;
import eapli.base.candidatemanagement.domain.RequirementsFile;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import eapli.framework.infrastructure.authz.domain.model.Name;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DisplayInfoCandidateTest {
    private DisplayInfoCandidateController controller;
    private CandidateService candidateService;
    private AuthorizationService authzRegistry;
    @BeforeEach
    public void setUp() {
        authzRegistry = Mockito.mock(AuthorizationService.class);
        candidateService = Mockito.mock(CandidateService.class);
        controller = new DisplayInfoCandidateController(authzRegistry, candidateService);

    }
    @Test
    public void testfindCandidateUserByEmail() {
        CandidateDTO candidate1 = new CandidateDTO();
        EmailAddress email1 = EmailAddress.valueOf("hola@gmail.com");
        Name name1 = Name.valueOf("juan", "perez");
        RequirementsFile requirementsFile1 = RequirementsFile.valueOf("file1");
        PhoneNumber phoneNumber1 = PhoneNumber.valueOf("+351234234234");
        candidate1.setEmail(email1);
        candidate1.setName(name1);
        candidate1.setRequirementsFile(requirementsFile1);
        candidate1.setPhoneNumber(phoneNumber1);

        Optional<CandidateDTO> candidate = Optional.of(candidate1);

        when(candidateService.findCandidateUserByEmail(email1)).thenReturn(candidate);

        Optional<CandidateDTO> result = controller.findCandidateUserByEmail(email1);
        assertEquals(candidate, result);
    }
}
