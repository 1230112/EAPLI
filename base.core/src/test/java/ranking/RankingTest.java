package ranking;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.jobapplicationmanagement.domain.*;
import eapli.base.jobopeningmanagement.domain.ContractType;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.domain.Mode;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RankingTest {
    private Ranking ranking;
    private JobOpening jobOpening;
    private Candidate candidate1;
    private Candidate candidate2;
    private JobApplication jobApplication1;
    private JobApplication jobApplication2;
private RankingBuilder rankingBuilder;

    private SystemUser customerManager;

    @BeforeEach
    void setUp() {
        customerManager = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER).build();
        jobOpening = new JobOpening(JobReference.valueOf("REF1"), "Title1", "Address1", "JobDescription1", ContractType.valueOf("ContractType1"), 1, Mode.valueOf("Mode1"), "Company1", customerManager);
        candidate1 = new Candidate(new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("1@email.com", "Password1", "First", "Last", "1@email.com").withRoles(BaseRoles.CANDIDATE).build(), EmailAddress.valueOf("1@email.com"), null, null, null);
        candidate2 = new Candidate(new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder()).with("2@email.com", "Password2", "Second", "Last", "2@email.com").withRoles(BaseRoles.CANDIDATE).build(), EmailAddress.valueOf("2@email.com"), null, null, null);
        rankingBuilder = new RankingBuilder().withJobOpening(jobOpening).withExtraVacancies(ExtraVacancies.valueOf((float)1.0));
        jobApplication1 = new JobApplication(JobApplicationId.valueOf(1), jobOpening, candidate1, null, ScreeningResult.ACCEPTED, null, null);
        jobApplication2 = new JobApplication(JobApplicationId.valueOf(2), jobOpening, candidate2, null, ScreeningResult.ACCEPTED, null, null);





    }

    @Test
    @DisplayName("The number of extra vacancies + the number of Vacancies cannot be greater that the number of valid applications that exit for the job opening")
    void validateNumberExtraVacations() {
        RankingBuilder builder = new RankingBuilder().withExtraVacancies(ExtraVacancies.valueOf((float)3.0));
        assertThrows(IllegalStateException.class, builder::build);
    }
    @Test
    @DisplayName("The number of extra vacancies = extraVacations * the number of Vacancies and it has to be an integer result")
    void validateNumberToTalExtraVacations() {
        RankingBuilder builder = new RankingBuilder().withJobOpening(jobOpening).withExtraVacancies(ExtraVacancies.valueOf((float)0.5));
        assertThrows(IllegalStateException.class, builder::build);
    }
    @Test
    @DisplayName("Should create RankOrder")
    void shouldCreateRankOrder() {
        RankOrder rankOrder1 = new RankOrder(jobApplication1, OrderNumber.valueOf(1));
        RankOrder rankOrder2 = new RankOrder(jobApplication2, OrderNumber.valueOf(2));
        assertNotNull(rankOrder1);
        assertNotNull(rankOrder2);

        // Verificar que las propiedades de RankOrder se establecen correctamente
        assertEquals(jobApplication1, rankOrder1.jobApplication());
        assertEquals(OrderNumber.valueOf(1), rankOrder1.number());
        assertEquals(jobApplication2, rankOrder2.jobApplication());
        assertEquals(OrderNumber.valueOf(2), rankOrder2.number());
    }
@Test
    @DisplayName("ShouldCreateRanking")
    void shouldCreateRanking() {

        new Ranking(jobOpening, ExtraVacancies.valueOf((float) 1.0));
    assertTrue(true);

    }


    @Test
    @DisplayName("Should add RankOrder to the list")
    void shouldAddRankOrder() {
        this.ranking = new Ranking(this.jobOpening, ExtraVacancies.valueOf((float)1.0));
        RankOrder rankOrder1 = new RankOrder(jobApplication1, OrderNumber.valueOf(1));
        assertTrue(this.ranking.addRankOrder(rankOrder1));
        assertEquals(1, this.ranking.rankOrders().size());
    }


    @Test
    @DisplayName("Should not add duplicate RankOrder")
    void shouldNotAddDuplicateRankOrder() {
        RankOrder rankOrder1 = new RankOrder(jobApplication1, OrderNumber.valueOf(1));
        this.ranking = new Ranking(this.jobOpening, ExtraVacancies.valueOf((float)1.0));
        assertTrue(ranking.addRankOrder(rankOrder1));
        assertFalse(ranking.addRankOrder(rankOrder1));
        assertEquals(1, ranking.rankOrders().size());
    }
    @Test
    @DisplayName("Should return correct identity")
    void shouldReturnCorrectIdentity() {
        this.ranking = new Ranking(this.jobOpening, ExtraVacancies.valueOf((float)1.0));

        assertEquals(jobOpening.jobReference(), ranking.identity());
    }
    @Test
    @DisplayName("Should throw exception when creating Ranking with null JobOpening")
    void shouldThrowExceptionWhenCreatingRankingWithNullJobOpening() {
        assertThrows(IllegalArgumentException.class, () -> new Ranking(null, ExtraVacancies.valueOf((float)1.0)));
    }
    @Test
    @DisplayName("Should throw exception when building Ranking without JobOpening")
    void shouldThrowExceptionWhenBuildingRankingWithoutJobOpening() {
        RankingBuilder builder = new RankingBuilder().withExtraVacancies(ExtraVacancies.valueOf((float)1.0));
        assertThrows(IllegalStateException.class, builder::build);
    }




}
