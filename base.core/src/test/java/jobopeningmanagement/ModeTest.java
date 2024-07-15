package jobopeningmanagement;

import eapli.base.jobopeningmanagement.domain.Mode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeTest {

    @Test
    void shouldCreateModeWhenValidStringIsProvided() {
        String validMode = "Mode1";
        Mode mode = Mode.valueOf(validMode);
        assertEquals(validMode, mode.toString());
    }

    @Test
    void shouldThrowExceptionWhenNullIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> Mode.valueOf(null));
    }

    @Test
    void shouldCompareToOtherModeCorrectlyWhenSame() {
        String mode1 = "Mode1";
        Mode m1 = Mode.valueOf(mode1);
        Mode m2 = Mode.valueOf(mode1);
        assertEquals(0, m1.compareTo(m2));
    }

    @Test
    void shouldCompareToOtherModeCorrectlyWhenDifferent() {
        String mode1 = "Mode1";
        String mode2 = "Mode2";
        Mode m1 = Mode.valueOf(mode1);
        Mode m2 = Mode.valueOf(mode2);
        assertEquals(-1, m1.compareTo(m2));
    }
}
