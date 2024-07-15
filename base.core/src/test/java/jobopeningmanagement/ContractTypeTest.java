package jobopeningmanagement;

import eapli.base.jobopeningmanagement.domain.ContractType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContractTypeTest {

    @Test
    void shouldCreateContractTypeWhenValidStringIsProvided() {
        String validContractType = "ContractType1";
        ContractType contractType = ContractType.valueOf(validContractType);
        assertEquals(validContractType, contractType.toString());
    }

    @Test
    void shouldThrowExceptionWhenNullIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> ContractType.valueOf(null));
    }

    @Test
    void shouldCompareToOtherContractTypeCorrectlyWhenSame() {
        String contractType1 = "ContractType1";
        ContractType ct1 = ContractType.valueOf(contractType1);
        ContractType ct2 = ContractType.valueOf(contractType1);
        assertEquals(0, ct1.compareTo(ct2));
    }

    @Test
    void shouldCompareToOtherContractTypeCorrectlyWhenDifferent() {
        String contractType1 = "ContractType1";
        String contractType2 = "ContractType2";
        ContractType ct1 = ContractType.valueOf(contractType1);
        ContractType ct2 = ContractType.valueOf(contractType2);
        assertEquals(-1, ct1.compareTo(ct2));
    }
}
