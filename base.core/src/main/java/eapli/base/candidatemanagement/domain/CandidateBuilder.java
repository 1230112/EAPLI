package eapli.base.candidatemanagement.domain;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class CandidateBuilder implements DomainFactory<Candidate> {
    private SystemUser systemUser;
    private EmailAddress email;

    private RequirementsFile requirementsFile;
    private PhoneNumber phoneNumber;
    private AppStatus appStatus;

    public CandidateBuilder withAppStatus(final AppStatus appStatus) {
        this.appStatus = appStatus;
        return this;
    }
    public CandidateBuilder withAppStatus(final String appStatus) {
        this.appStatus = AppStatus.valueOf(appStatus);
        return this;
    }

    public CandidateBuilder withSystemUser(final SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    public CandidateBuilder withEmail(final EmailAddress email) {
        this.email = email;
        return this;
    }

    public CandidateBuilder withEmail(final String email) {
        this.email =  EmailAddress.valueOf(email);
        return this;
    }


    public CandidateBuilder withRequirementsFile(final RequirementsFile requirementsFile) {
        this.requirementsFile =  requirementsFile;
        return this;
    }
    public CandidateBuilder withRequirementsFile(final String requirementsFile) {
        this.requirementsFile =  RequirementsFile.valueOf(requirementsFile);
        return this;
    }

    public CandidateBuilder withPhoneNumber(final String phoneNumber) {
        this.phoneNumber = PhoneNumber.valueOf(phoneNumber);
        return this;
    }
    public CandidateBuilder withPhoneNumber(final PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public Candidate build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        return new Candidate(this.systemUser, this.email, this.requirementsFile, this.phoneNumber, this.appStatus);
    }
}
