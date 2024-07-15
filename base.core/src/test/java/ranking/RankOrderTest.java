package ranking;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.jobapplicationmanagement.domain.*;
import eapli.base.jobopeningmanagement.domain.ContractType;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.domain.Mode;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import static org.junit.jupiter.api.Assertions.*;

public class RankOrderTest {
    private JobApplication jobApplication;
    private OrderNumber orderNumber;
    private RankOrder rankOrder;
    private JobOpening jobOpening;
    private SystemUser customerManager;

private Candidate candidate1;
    @BeforeEach
    void setUp() {
        customerManager = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER).build();

        candidate1 = new Candidate(new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CANDIDATE).build(), EmailAddress.valueOf("1@email.com"), null, null, null);

        jobOpening = new JobOpening(JobReference.valueOf("REF1"), "Title1", "Address1", "JobDescription1", ContractType.valueOf("ContractType1"), 1, Mode.valueOf("Mode1"), "Company1", customerManager);

        jobApplication = new JobApplication(JobApplicationId.valueOf(1), jobOpening, candidate1, null, ScreeningResult.ACCEPTED, null, null);


        rankOrder = new RankOrder(jobApplication, OrderNumber.valueOf(1));
    }

    @Test
    @DisplayName("Should create RankOrder with valid inputs")
    void shouldCreateRankOrderWithValidInputs() {
        OrderNumber orderNumber = OrderNumber.valueOf(1);
        assertEquals(jobApplication, rankOrder.jobApplication());
        assertEquals(orderNumber, rankOrder.number());
    }

    @Test
    @DisplayName("Should throw exception when creating RankOrder with null JobApplication")
    void shouldThrowExceptionWhenCreatingRankOrderWithNullJobApplication() {
        assertThrows(IllegalArgumentException.class, () -> new RankOrder(null, orderNumber));
    }

    @Test
    @DisplayName("Should throw exception when creating RankOrder with null OrderNumber")
    void shouldThrowExceptionWhenCreatingRankOrderWithNullOrderNumber() {
        assertThrows(IllegalArgumentException.class, () -> new RankOrder(jobApplication, null));
    }

    @Test
    @DisplayName("Should return correct JobApplication")
    void shouldReturnCorrectJobApplication() {
        assertEquals(jobApplication, rankOrder.jobApplication());
    }

    @Test
    @DisplayName("Should return correct OrderNumber")
    void shouldReturnCorrectOrderNumber() {
        OrderNumber orderNumber = OrderNumber.valueOf(1);
        assertEquals(orderNumber, rankOrder.number());
    }
}