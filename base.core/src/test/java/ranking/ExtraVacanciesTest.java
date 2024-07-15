package ranking;


import eapli.base.jobapplicationmanagement.domain.ExtraVacancies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExtraVacanciesTest {
    private ExtraVacancies extraVacancies;

    @BeforeEach
    void setUp() {
        extraVacancies = ExtraVacancies.valueOf(1.0f);
    }

    @Test
    @DisplayName("Should create ExtraVacancies with valid input")
    void shouldCreateExtraVacanciesWithValidInput() {
        assertNotNull(extraVacancies);
        assertEquals(1.0f, extraVacancies.toFloat());
    }

    @Test
    @DisplayName("Should throw exception when creating ExtraVacancies with null input")
    void shouldThrowExceptionWhenCreatingExtraVacanciesWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> ExtraVacancies.valueOf(null));
    }

    @Test
    @DisplayName("Should throw exception when creating ExtraVacancies with negative input")
    void shouldThrowExceptionWhenCreatingExtraVacanciesWithNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> ExtraVacancies.valueOf(-1.0f));
    }

    @Test
    @DisplayName("Should return correct float value")
    void shouldReturnCorrectFloatValue() {
        assertEquals(1.0f, extraVacancies.toFloat());
    }
}
