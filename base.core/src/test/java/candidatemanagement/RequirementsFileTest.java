package candidatemanagement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import eapli.base.candidatemanagement.domain.RequirementsFile;
import org.junit.Test;
public class RequirementsFileTest {
    @Test
    public void ensureRequirementsFileCanBeEmpty() {
        RequirementsFile requirementsFile = RequirementsFile.valueOf("");
        assertEquals("", requirementsFile.toString());
    }
    @Test
    public void ensureTwoRequirementsFilesAreEqualIfTheyHaveTheSameValue() {
        RequirementsFile requirementsFile1 = RequirementsFile.valueOf("ABC");
        RequirementsFile requirementsFile2 = RequirementsFile.valueOf("ABC");
        assertEquals(requirementsFile1, requirementsFile2);
    }
    @Test
    public void ensureHashCodeIsTheSameForTwoRequirementsFilesWithTheSameValue() {
        RequirementsFile requirementsFile1 = RequirementsFile.valueOf("ABC");
        RequirementsFile requirementsFile2 = RequirementsFile.valueOf("ABC");
        assertEquals(requirementsFile1.hashCode(), requirementsFile2.hashCode());
    }

    @Test
    public void ensureToStringReturnsTheRequirementsFileValue() {
        RequirementsFile requirementsFile = RequirementsFile.valueOf("ABC");
        assertEquals(requirementsFile.toString(), "ABC");
    }



    @Test
    public void ensureTwoRequirementsFilesAreNotEqualIfTheyHaveDifferentValues() {
        RequirementsFile requirementsFile1 = RequirementsFile.valueOf("ABC");
        RequirementsFile requirementsFile2 = RequirementsFile.valueOf("DEF");
        assertNotEquals(requirementsFile1, requirementsFile2);
    }

}
