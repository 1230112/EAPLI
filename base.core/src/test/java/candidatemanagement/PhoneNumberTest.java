package candidatemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import eapli.base.candidatemanagement.domain.PhoneNumber;
import org.junit.Test;
public class PhoneNumberTest {
    @Test
    public void ensureItIsNotPossibleToCreateAPhoneNumberWithOtherPrefix() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumber.valueOf("+34789678567"));
    }

    @Test
    public void ensureItIsNotPossibleToCreateAPhoneNumberWithOtherAmountOfDigit() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumber.valueOf("+35167"));
    }
    @Test
    public void ensureItIsNotPossibleToCreateAnPhoneNumberWithBlankValue() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumber.valueOf(""));
    }
    @Test
    public void ensureTwoPhoneNumbersAreEqualIfTheyHaveTheSameValue() {
        PhoneNumber phoneNumber1 = PhoneNumber.valueOf("+351678456345");
        PhoneNumber phoneNumber2 = PhoneNumber.valueOf("+351678456345");
        assertEquals(phoneNumber1, phoneNumber2);
    }
    @Test
    public void ensureHashCodeIsTheSameForTwoPhoneNumbersWithTheSameValue() {
        PhoneNumber phoneNumber1 = PhoneNumber.valueOf("+351678456345");
        PhoneNumber phoneNumber2 = PhoneNumber.valueOf("+351678456345");
        assertEquals(phoneNumber1.hashCode(), phoneNumber2.hashCode());
    }
    @Test
    public void ensureToStringReturnsThePhoneNumberValue() {
        PhoneNumber phoneNumber = PhoneNumber.valueOf("+351678456345");
        assertEquals(phoneNumber.toString(), "+351678456345");
    }



    @Test
    public void ensureTwoPhoneNumbersAreNotEqualIfTheyHaveDifferentValues() {
        PhoneNumber phoneNumber1 = PhoneNumber.valueOf("+351678456345");
        PhoneNumber phoneNumber2 = PhoneNumber.valueOf("+351890678456");
        assertNotEquals(phoneNumber1, phoneNumber2);
    }

}
