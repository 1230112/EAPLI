package jobapplicationmanagement;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.domain.ScreeningResult;
import eapli.base.jobopeningmanagement.domain.ContractType;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.domain.Mode;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JobApplicationTest {
    private JobApplication jobApplication1;
    private JobApplication jobApplication2;
    private JobApplication jobApplication3;
    private JobApplication jobApplication4;

    private JobOpening jobOpening1;
    private JobOpening jobOpening2;

    private Candidate candidate1;
    private Candidate candidate2;


    @Before
    public void setUp() {
        jobOpening1 = new JobOpening(JobReference.valueOf("111"), "Title1", "Addres1", "Description1", ContractType.valueOf("FULL TIME"), 1, Mode.valueOf("PRESENCIAL"), "Company1", new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("cuma@email.com", "Password1", "Custu", "Mana", "cuma@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER).build());
        jobOpening2 = new JobOpening(JobReference.valueOf("222"), "Title2", "Addres2", "Description2", ContractType.valueOf("PART TIME"), 2, Mode.valueOf("REMOTE"), "Company2", new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("cuma@email.com", "Password1", "Custu", "Mana", "cuma@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER).build());

        candidate1 = new Candidate(new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CANDIDATE).build(), EmailAddress.valueOf("1@email.com"), null, null, null);
        candidate2 = new Candidate(new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("2@email.com", "Password2", "Second", "Last", "2@email.com").withRoles(BaseRoles.CANDIDATE).build(), EmailAddress.valueOf("2@email.com"), null, null, null);

        jobApplication1 = new JobApplication(JobApplicationId.valueOf(1), jobOpening1, candidate1, null, ScreeningResult.WAITING, null, null);
        jobApplication2 = new JobApplication(JobApplicationId.valueOf(2), jobOpening2, candidate2, null, ScreeningResult.WAITING, null, null);
        jobApplication3 = new JobApplication(JobApplicationId.valueOf(3), jobOpening1, candidate1, null, ScreeningResult.WAITING, null, null);
        jobApplication4 = new JobApplication(JobApplicationId.valueOf(2), jobOpening2, candidate2, null, ScreeningResult.WAITING, null, null);
    }

    @Test
    public void identity() {
        assertEquals(1, jobApplication1.identity().identity());
        assertEquals(2, jobApplication2.identity().identity());
        assertNotEquals(3, jobApplication4.identity().identity());
    }

    @Test
    public void testEquals() {
        assertEquals(jobApplication2, jobApplication4);
        assertEquals(jobApplication4, jobApplication2);
        assertNotEquals(jobApplication1, jobApplication2);
        assertNotEquals(jobApplication1, jobApplication3);
        assertNotEquals(jobApplication1, jobApplication4);
    }

    @Test
    public void testHashCode() {
        assertEquals(jobApplication2.hashCode(), jobApplication4.hashCode());
        assertEquals(jobApplication4.hashCode(), jobApplication2.hashCode());
        assertEquals(jobApplication1.hashCode(), jobApplication1.hashCode());
        assertNotEquals(jobApplication1.hashCode(), jobApplication2.hashCode());
        assertNotEquals(jobApplication1.hashCode(), jobApplication3.hashCode());
        assertNotEquals(jobApplication1.hashCode(), jobApplication4.hashCode());
    }

    @Test
    public void testResults() {
        assertNull(jobApplication1.FinalResult());
        assertEquals("Waiting", jobApplication1.ScreeningResult());
        assertNull(jobApplication1.ScreeningJustification());


        jobApplication1.addScreeningResultAndJustification(ScreeningResult.ACCEPTED, "Justification1");
        jobApplication1.addFinalResult("FinalResult1");

        assertEquals("Accepted", jobApplication1.ScreeningResult());
        assertEquals("Justification1", jobApplication1.ScreeningJustification());
        assertEquals("FinalResult1", jobApplication1.FinalResult());

        jobApplication2.addScreeningResultAndJustification(ScreeningResult.ACCEPTED, "Justification1");
        jobApplication2.addFinalResult("FinalResult1");

        assertEquals(jobApplication1.ScreeningResult(), jobApplication2.ScreeningResult());
        assertEquals(jobApplication1.ScreeningJustification(), jobApplication2.ScreeningJustification());
        assertEquals(jobApplication1.FinalResult(), jobApplication2.FinalResult());
    }

    @Test
    public void shouldAddScreeningResultAndJustification() {
        jobApplication1.addScreeningResultAndJustification(ScreeningResult.ACCEPTED, "Justification1");
        assertEquals("Accepted", jobApplication1.ScreeningResult());
        assertEquals("Justification1", jobApplication1.ScreeningJustification());
    }

    @Test
    public void shouldAddFinalResult() {
        jobApplication1.addFinalResult("FinalResult1");
        assertEquals("FinalResult1", jobApplication1.FinalResult());
    }

    @Test
    public void shouldNotAddScreeningResultAndJustificationWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> jobApplication1.addScreeningResultAndJustification(null, null));
    }

    @Test
    public void shouldNotAddFinalResultWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> jobApplication1.addFinalResult(null));
    }

}
