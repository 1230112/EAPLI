 package candidatemanagement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


import eapli.base.candidatemanagement.domain.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.Test;

import eapli.base.usermanagement.domain.BaseRoles;

import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
public class CandidateTest {
    private SystemUser getDummyUser() {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with("elenaa@gmail,com", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.CUSTOMER)
                .build();
    }

    private SystemUser getSecondDummyUser() {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with("username2@gmail.com", "duMMy2", "dummY", "dummY", "b@a.ro").withRoles(BaseRoles.CUSTOMER)
                .build();
    }
    @Test
    public void testCandidateCreation() {
        SystemUser user = getDummyUser();
        PhoneNumber phoneNumber = PhoneNumber.valueOf("+351678654543");
        RequirementsFile requirementsFile = RequirementsFile.valueOf("ABC");
        EmailAddress email = EmailAddress.valueOf("elenaa@gmail.com");
        AppStatus appStatus = AppStatus.valueOf("ENABLE");
        AppStatus appStatus2 = AppStatus.valueOf("DISABLE");
        Candidate candidate = new Candidate(user, email, requirementsFile, phoneNumber, appStatus);
        assertEquals(user, candidate.user());
        assertEquals(phoneNumber, candidate.phoneNumber());
        assertEquals(requirementsFile, candidate.requirementsFile());
        assertEquals(email, candidate.email());
        assertEquals(appStatus, candidate.appStatus());
        assertNotEquals(appStatus2, candidate.appStatus());
    }




    @Test
    public void testCandidateEquality() {
        SystemUser user1 = getDummyUser();
        SystemUser user2 = getSecondDummyUser();
        PhoneNumber phoneNumber1 = PhoneNumber.valueOf("+351678654543");
        PhoneNumber phoneNumber2 = PhoneNumber.valueOf("+351567345546");
        RequirementsFile requirementsFile1 = RequirementsFile.valueOf("ABC");
        RequirementsFile requirementsFile2 = RequirementsFile.valueOf("DEF");
        EmailAddress email1 = EmailAddress.valueOf("elenaa@gmail.com");
        EmailAddress email2 = EmailAddress.valueOf("elena2@gmail.com");
        AppStatus appStatus = AppStatus.valueOf("ENABLE");
        Candidate candidate1 = new Candidate(user1, email1, requirementsFile1, phoneNumber1, appStatus);
        Candidate candidate2 = new Candidate(user2, email2, requirementsFile2, phoneNumber2, appStatus);
        Candidate candidate3 = new Candidate(user1, email1, requirementsFile1, phoneNumber1, appStatus);
        assertNotEquals(candidate1, candidate2);
        assertEquals(candidate1, candidate3);
    }

    @Test
    public void testCandidateHashCode() {
        SystemUser user1 = getDummyUser();
        SystemUser user2 = getSecondDummyUser();
        PhoneNumber phoneNumber1 = PhoneNumber.valueOf("+351678654543");
        PhoneNumber phoneNumber2 = PhoneNumber.valueOf("+351765543432");
        RequirementsFile requirementsFile1 = RequirementsFile.valueOf("ABC");
        RequirementsFile requirementsFile2 = RequirementsFile.valueOf("DEF");
        EmailAddress email1 = EmailAddress.valueOf("elenaa@gmail.com");
        EmailAddress email2 = EmailAddress.valueOf("elena2@gmail.com");
        AppStatus appStatus = AppStatus.valueOf("ENABLE");
        Candidate candidate1 = new Candidate(user1, email1, requirementsFile1, phoneNumber1, appStatus);
        Candidate candidate2 = new Candidate(user2, email2, requirementsFile2, phoneNumber2,    appStatus);
        Candidate candidate3 = new Candidate(user1, email1, requirementsFile1, phoneNumber1, appStatus);
        assertNotEquals(candidate1.hashCode(), candidate2.hashCode());
        assertEquals(candidate1.hashCode(), candidate3.hashCode());
    }



    @Test
    public void testCandidateBuilder() {
        SystemUser user = getDummyUser();
        PhoneNumber phoneNumber = PhoneNumber.valueOf("+351678654543");
        RequirementsFile requirementsFile = RequirementsFile.valueOf("ABC");
        EmailAddress email = EmailAddress.valueOf("elenaa@gmail.com");
        AppStatus appStatus = AppStatus.valueOf("ENABLE");
        Candidate candidate = new Candidate(user, email, requirementsFile, phoneNumber, appStatus);
        CandidateBuilder candidateBuilder = new CandidateBuilder();
        candidateBuilder.withSystemUser(user);
        candidateBuilder.withPhoneNumber(phoneNumber);
        candidateBuilder.withRequirementsFile(requirementsFile);
        candidateBuilder.withEmail(email);
        assertEquals(candidate, candidateBuilder.build());
    }
}
