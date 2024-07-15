package jobopeningmanagement;

import eapli.base.jobopeningmanagement.domain.JobReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobReferenceTest {

    @Test
    void shouldCreateJobReferenceWhenValidStringIsProvided() {
        String validJobReference = "JOB123";
        JobReference jobReference = JobReference.valueOf(validJobReference);
        assertEquals(validJobReference, jobReference.toString());
    }

    @Test
    void shouldThrowExceptionWhenEmptyStringIsProvided() {
        String emptyJobReference = "";
        assertThrows(IllegalArgumentException.class, () -> JobReference.valueOf(emptyJobReference));
    }

    @Test
    void shouldThrowExceptionWhenJobReferenceContainsSpaces() {
        String jobReferenceWithSpaces = "JOB 123";
        assertThrows(IllegalArgumentException.class, () -> JobReference.valueOf(jobReferenceWithSpaces));
    }

    @Test
    void shouldCompareToOtherJobReferenceCorrectly() {
        String jobReference1 = "JOB123";
        String jobReference2 = "JOB124";
        JobReference jr1 = JobReference.valueOf(jobReference1);
        JobReference jr2 = JobReference.valueOf(jobReference2);
        assertTrue(jr1.compareTo(jr2) < 0);
    }
}
