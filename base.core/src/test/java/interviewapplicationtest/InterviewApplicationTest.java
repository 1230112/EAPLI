package interviewapplicationtest;
import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.interviewmanagement.domain.DateTime;
import eapli.base.interviewmanagement.domain.InterviewApplication;
import eapli.base.jobapplicationmanagement.domain.*;
import eapli.base.jobopeningmanagement.domain.ContractType;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.domain.Mode;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.io.util.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class InterviewApplicationTest {

    private JobOpening jobOpening;
    private Candidate candidate1;
    private Candidate candidate2;
    private JobApplication jobApplication1;
    private JobApplication jobApplication2;


    private SystemUser customerManager;

    @BeforeEach
    void setUp() {
        customerManager = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER).build();
        jobOpening = new JobOpening(JobReference.valueOf("REF1"), "Title1", "Address1", "JobDescription1", ContractType.valueOf("ContractType1"), 1, Mode.valueOf("Mode1"), "Company1", customerManager);
        candidate1 = new Candidate(new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CANDIDATE).build(), EmailAddress.valueOf("1@email.com"), null, null, null);
        candidate2 = new Candidate(new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("2@email.com", "Password2", "Second", "Last", "2@email.com").withRoles(BaseRoles.CANDIDATE).build(), EmailAddress.valueOf("2@email.com"), null, null, null);
        jobApplication1 = new JobApplication(JobApplicationId.valueOf(1), jobOpening, candidate1, null, ScreeningResult.ACCEPTED, null, null);
        jobApplication2 = new JobApplication(JobApplicationId.valueOf(2), jobOpening, candidate2, null, ScreeningResult.ACCEPTED, null, null);


    }


    @Test
    @DisplayName("Should Create InterviewApplication")
    void shouldCreateRankOrder() {
        int year = 2025;
        int month = 2;
        int day = 9;


        int hour = 18;
        int minute = 30;
        int second = 0;

         Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month - 1, day, hour, minute, second);

        InterviewApplication interviewApplication = new InterviewApplication(jobApplication1, DateTime.valueOf(dateTime), null, null, null);


        assertEquals(jobApplication1, interviewApplication.jobApplication());
        assertEquals(DateTime.valueOf(dateTime), interviewApplication.dateTime());
    }
    @Test
    public void ensureJobApplicationAndDateTimeCannotBeNull() {
        int year = 2025;
        int month = 2;
        int day = 9;


        int hour = 18;
        int minute = 30;
        int second = 0;

        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month - 1, day, hour, minute, second);
        assertThrows(IllegalArgumentException.class, () -> {
            new InterviewApplication(null, DateTime.valueOf(dateTime), null, null, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new InterviewApplication(jobApplication1, null, null, null, null);
        });

        assertDoesNotThrow(() -> {
            new InterviewApplication(jobApplication1, DateTime.valueOf(dateTime), null, null, null);
        });
    }
    @Test
    @DisplayName("Control null parameters")
    public void ensureJobApplicationIsSame() {
        int year = 2025;
        int month = 2;
        int day = 9;


        int hour = 18;
        int minute = 30;
        int second = 0;

        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month - 1, day, hour, minute, second);
        InterviewApplication interviewApplication = new InterviewApplication(jobApplication1, DateTime.valueOf(dateTime), null, null, null);

        assertEquals(jobApplication1, interviewApplication.jobApplication());
    }

    @Test
    @DisplayName("Ensure identity is same as job application id")
    public void ensureIdentityIsSameAsJobApplicationId() {
        int year = 2025;
        int month = 2;
        int day = 9;


        int hour = 18;
        int minute = 30;
        int second = 0;

        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month - 1, day, hour, minute, second);
        InterviewApplication interviewApplication = new InterviewApplication(jobApplication1, DateTime.valueOf(dateTime), null, null, null);

        assertEquals(jobApplication1.jobApplicationId(), interviewApplication.identity());
    }

    @Test
    @DisplayName("Ensure sameAs returns true for same object")
    public void ensureSameAsReturnsTrueForSameObject() {
        int year = 2025;
        int month = 2;
        int day = 9;


        int hour = 18;
        int minute = 30;
        int second = 0;

        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month - 1, day, hour, minute, second);
        InterviewApplication interviewApplication = new InterviewApplication(jobApplication1, DateTime.valueOf(dateTime), null, null, null);

        assertTrue(interviewApplication.sameAs(interviewApplication));
    }

    @Test
    @DisplayName("Ensure sameAs returns false for different object")
    public void ensureSameAsReturnsFalseForDifferentObject() {
        int year = 2025;
        int month = 2;
        int day = 9;


        int hour = 18;
        int minute = 30;
        int second = 0;

        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month - 1, day, hour, minute, second);
        InterviewApplication interviewApplication = new InterviewApplication(jobApplication1, DateTime.valueOf(dateTime), null, null, null);

        InterviewApplication other = new InterviewApplication(jobApplication2, DateTime.valueOf(dateTime), null, null, null);

        assertFalse(interviewApplication.sameAs(other));
    }
}