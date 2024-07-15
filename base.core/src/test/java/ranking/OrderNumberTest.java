package ranking;

import eapli.base.jobapplicationmanagement.domain.OrderNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderNumberTest {
    private OrderNumber orderNumber;

    @BeforeEach
    void setUp() {
        orderNumber = OrderNumber.valueOf(1);
    }



    @Test
    @DisplayName("Should throw exception when creating OrderNumber with null input")
    void shouldThrowExceptionWhenCreatingOrderNumberWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> OrderNumber.valueOf(null));
    }

    @Test
    @DisplayName("Should throw exception when creating OrderNumber with negative input")
    void shouldThrowExceptionWhenCreatingOrderNumberWithNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> OrderNumber.valueOf(-1));
    }

    @Test
    @DisplayName("Should correctly compare two OrderNumbers")
    void shouldCorrectlyCompareTwoOrderNumbers() {
        OrderNumber orderNumber2 = OrderNumber.valueOf(2);
        assertTrue(orderNumber.compareTo(orderNumber2) < 0);
    }
}
