package jobopeningmanagement;

import eapli.base.jobopeningmanagement.domain.*;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobOpeningTest {

    SystemUser costumerManager;
    JobOpening jobOpening;

    @BeforeEach
    public void setUp() {
        costumerManager = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER).build();
        jobOpening = new JobOpening(JobReference.valueOf("REF1"), "Title1", "Address1", "JobDescription1", ContractType.valueOf("ContractType1"), 1, Mode.valueOf("Mode1"), "Company1", costumerManager);
    }

    @Test
    void shouldCreateJobOpeningWithValidParameters() {
        JobReference jobReference = JobReference.valueOf("REF1");
        String title = "Title1";
        String address = "Address1";
        String jobDescription = "JobDescription1";
        ContractType contractType = ContractType.valueOf("ContractType1");
        int numberVacancies = 1;
        Mode mode = Mode.valueOf("Mode1");
        String company = "Company1";

        assertEquals(jobReference, jobOpening.jobReference());
        assertEquals(title, jobOpening.title());
        assertEquals(address, jobOpening.address());
        assertEquals(jobDescription, jobOpening.jobDescription());
        assertEquals(contractType, jobOpening.contractType());
        assertEquals(numberVacancies, jobOpening.numberVacancies());
        assertEquals(mode, jobOpening.mode());
        assertEquals(company, jobOpening.company());
        assertEquals(costumerManager, jobOpening.costumerManager());
    }

    @Test
    void shouldThrowExceptionWhenNullJobReferenceIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening(null, "Title1", "Address1", "JobDescription1", ContractType.valueOf("ContractType1"), 1, Mode.valueOf("Mode1"), "Company1", costumerManager));
    }

    @Test
    void shouldThrowExceptionWhenNullTitleIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening(JobReference.valueOf("REF1"), null, "Address1", "JobDescription1", ContractType.valueOf("ContractType1"), 1, Mode.valueOf("Mode1"), "Company1", costumerManager));
    }

}
