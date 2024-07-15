package interviewapplicationtest;



import eapli.base.interviewmanagement.domain.Grade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void ensureGradeCannotBeNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Grade.valueOf(null);
        });
    }

    @Test
    public void ensureGradeCannotBeNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Grade.valueOf(-1);
        });
    }

    @Test
    public void ensureGradeCannotBeOver100() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Grade.valueOf(101);
        });
    }

    @Test
    public void ensureGradeCanBeZero() {
        Assertions.assertDoesNotThrow(() -> {
            Grade.valueOf(0);
        });
    }

    @Test
    public void ensureGradeCanBe100() {
        Assertions.assertDoesNotThrow(() -> {
            Grade.valueOf(100);
        });
    }

    @Test
    public void ensureGradeToStringReturnsCorrectString() {
        Grade grade = Grade.valueOf(85);
        Assertions.assertEquals("85", grade.toString());
    }

    @Test
    public void ensureGradeToIntegerReturnsCorrectInteger() {
        Grade grade = Grade.valueOf(85);
        Assertions.assertEquals(85, grade.toInteger());
    }
}