/*package candidatemanagement;

import eapli.base.candidatemanagement.DTO.CandidateDTO;
import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;

import eapli.base.candidatemanagement.domain.*;
import eapli.base.candidatemanagement.repositories.CustomerRepository;
import eapli.base.usermanagement.application.AddUserController;
import eapli.base.candidatemanagement.application.ListCandidatesController;
import eapli.base.candidatemanagement.repositories.CandidateRepository;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import eapli.framework.infrastructure.authz.domain.model.Name;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AddCandidateTest {
    private AddUserController controller;
    private CandidateRepository candidateRepo;
    private CustomerRepository customerRepository;
    private UserManagementService userManagementService;
    private AuthorizationService authzRegistry;
    @BeforeEach
    public void setUp() {
        authzRegistry = Mockito.mock(AuthorizationService.class);
        candidateRepo = Mockito.mock(CandidateRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        userManagementService = Mockito.mock(UserManagementService.class);
        controller = new AddUserController(authzRegistry, userManagementService, candidateRepo, customerRepository);

    }
    @Test
    public void testAddCandidate() {
        CandidateDTO candidate1 = new CandidateDTO();
        EmailAddress email1 = EmailAddress.valueOf("hola@gmail.com");
        Name name1 = Name.valueOf("juan", "perez");
        RequirementsFile requirementsFile1 = RequirementsFile.valueOf("file1");
        PhoneNumber phoneNumber1 = PhoneNumber.valueOf("+351234234234");
        AppStatus status = AppStatus.valueOf("ENABLE");
        String password = PasswordGenerator.generateWithoutPolicyAndEncoder();
        candidate1.setEmail(email1);
        candidate1.setName(name1);
        candidate1.setRequirementsFile(requirementsFile1);
        candidate1.setPhoneNumber(phoneNumber1);
        SystemUser user = controller.addUser(email1.toString(), password, name1.firstName(), name1.lastName(), new HashSet<>(), Calendar.getInstance());
        Candidate s = new Candidate(user, email1, requirementsFile1, phoneNumber1, status);
        when(candidateRepo.save(s)).thenReturn(s);
        Candidate result = controller.addCandidate("hola@gmail.com", "juan", "perez", "file1","+351234234234" );
        assertEquals(candidate1, result);
    }
}*/