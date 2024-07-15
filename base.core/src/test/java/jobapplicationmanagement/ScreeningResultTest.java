package jobapplicationmanagement;

import eapli.base.jobapplicationmanagement.domain.ScreeningResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningResultTest {

    @Test
    void shouldReturnAcceptedWhenAcceptedStringIsProvided() {
        ScreeningResult result = ScreeningResult.ACCEPTED;
        assertEquals(result, result.getResultOf("Accepted"));
    }

    @Test
    void shouldReturnRejectedWhenRejectedStringIsProvided() {
        ScreeningResult result = ScreeningResult.REJECTED;
        assertEquals(result, result.getResultOf("Rejected"));
    }

    @Test
    void shouldReturnWaitingWhenWaitingStringIsProvided() {
        ScreeningResult result = ScreeningResult.WAITING;
        assertEquals(result, result.getResultOf("Waiting"));
    }

    @Test
    void shouldThrowExceptionWhenInvalidStringIsProvided() {
        ScreeningResult result = ScreeningResult.ACCEPTED;
        assertThrows(IllegalArgumentException.class, () -> result.getResultOf("Invalid"));
    }
}
