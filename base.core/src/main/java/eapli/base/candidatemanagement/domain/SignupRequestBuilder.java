package eapli.base.candidatemanagement.domain;

import java.util.Calendar;

import eapli.base.usermanagement.domain.users.Email;
import org.springframework.security.crypto.password.PasswordEncoder;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.time.util.CurrentTimeCalendars;

public class SignupRequestBuilder implements DomainFactory<SignupRequest> {

    private final PasswordPolicy policy;
    private final PasswordEncoder encoder;

    private String email;
    private String username;

    private Password password;

    private String firstName;
    private String lastName;
    private String requirementsFile;
     private String phoneNumber;

     private String appStatus;
    private Calendar createdOn;

    public SignupRequestBuilder(final PasswordPolicy policy, final PasswordEncoder encoder) {
        this.policy = policy;
        this.encoder = encoder;
    }

    public SignupRequestBuilder withData(final String email, final String firstName, final String lastName, final String requirementsFile, final String phoneNumber) {
        withEmail(email);
        withPassword();
        withName(firstName, lastName);
        withRequirementsFile(requirementsFile);
        withPhoneNumber(phoneNumber);
        withAppStatus("ENABLE");
        return this;
    }



    public SignupRequestBuilder withPassword() {
        password = Password.encodedAndValid(PasswordGenerator.generate(policy, encoder), policy, encoder)
                .orElseThrow(IllegalArgumentException::new);
        return this;
    }

    public SignupRequestBuilder withAppStatus(final String appStatus) {
        this.appStatus = appStatus;
        return this;
    }
    public SignupRequestBuilder withName(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        return this;
    }

    public SignupRequestBuilder withUsername(final String username) {
        this.username = username;
        return this;
    }

    public SignupRequestBuilder withEmail(final String email) {
        this.email = email;
        return this;
    }

    public SignupRequestBuilder withRequirementsFile(final String requirementsFile) {
        this.requirementsFile = requirementsFile;
        return this;
    }

    public SignupRequestBuilder withPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public SignupRequestBuilder createdOn(final Calendar createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @Override
    public SignupRequest build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        if (createdOn != null) {
            createdOn = CurrentTimeCalendars.now();
        }
        return new SignupRequest(EmailAddress.valueOf(email), password,
                Name.valueOf(firstName, lastName), RequirementsFile.valueOf(requirementsFile), PhoneNumber.valueOf(phoneNumber), AppStatus.valueOf(appStatus), createdOn);
    }
}
