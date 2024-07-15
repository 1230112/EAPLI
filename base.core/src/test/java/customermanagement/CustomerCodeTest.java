package customermanagement;

import eapli.base.candidatemanagement.domain.CustomerCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerCodeTest {

    @Test
    void shouldCreateCustomerCodeWhenValidStringIsProvided() {
        String validCustomerCode = "VALIDCODE";
        CustomerCode customerCode = CustomerCode.valueOf(validCustomerCode);
        assertEquals(validCustomerCode, customerCode.toString());
    }

    @Test
    void shouldThrowExceptionWhenNullIsProvided() {
        assertThrows(NullPointerException.class, () -> CustomerCode.valueOf(null));
    }

    @Test
    void shouldThrowExceptionWhenEmptyStringIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> CustomerCode.valueOf(""));
    }

    @Test
    void shouldThrowExceptionWhenCustomerCodeContainsNumbers() {
        assertThrows(IllegalArgumentException.class, () -> CustomerCode.valueOf("CODE123"));
    }

    @Test
    void shouldThrowExceptionWhenCustomerCodeIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> CustomerCode.valueOf("CD"));
    }

    @Test
    void shouldThrowExceptionWhenCustomerCodeIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> CustomerCode.valueOf("LONGCUSTOMERCODE"));
    }

    @Test
    void shouldCompareToOtherCustomerCodeCorrectlyWhenSame() {
        String customerCode1 = "VALIDCODE";
        CustomerCode cc1 = CustomerCode.valueOf(customerCode1);
        CustomerCode cc2 = CustomerCode.valueOf(customerCode1);
        assertEquals(0, cc1.compareTo(cc2));
    }

    @Test
    void shouldCompareToOtherCustomerCodeCorrectlyWhenDifferent() {
        String customerCode1 = "VALIDCODE";
        String customerCode2 = "ANOTHER";
        CustomerCode cc1 = CustomerCode.valueOf(customerCode1);
        CustomerCode cc2 = CustomerCode.valueOf(customerCode2);
        assertTrue(cc1.compareTo(cc2) > 0);
    }

}
