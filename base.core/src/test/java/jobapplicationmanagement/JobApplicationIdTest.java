package jobapplicationmanagement;

import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationIdTest {

    @Test
    void shouldCreateJobApplicationIdWhenValidNumberIsProvided() {
        int validJobApplicationId = 123;
        JobApplicationId jobApplicationId = JobApplicationId.valueOf(validJobApplicationId);
        assertEquals(validJobApplicationId, jobApplicationId.identity());
    }

    @Test
    void shouldThrowExceptionWhenNegativeNumberIsProvided() {
        int negativeJobApplicationId = -123;
        assertThrows(IllegalArgumentException.class, () -> JobApplicationId.valueOf(negativeJobApplicationId));
    }

    @Test
    void shouldThrowExceptionWhenZeroIsProvided() {
        int zeroJobApplicationId = 0;
        assertThrows(IllegalArgumentException.class, () -> JobApplicationId.valueOf(zeroJobApplicationId));
    }

    @Test
    void shouldCompareToOtherJobApplicationIdCorrectly() {
        int jobApplicationId1 = 123;
        int jobApplicationId2 = 124;
        JobApplicationId ja1 = JobApplicationId.valueOf(jobApplicationId1);
        JobApplicationId ja2 = JobApplicationId.valueOf(jobApplicationId2);
        assertTrue(ja1.compareTo(ja2) < 0);
    }
}