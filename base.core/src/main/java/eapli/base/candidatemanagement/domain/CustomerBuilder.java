package eapli.base.candidatemanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jakarta.persistence.Column;

public class CustomerBuilder implements DomainFactory<Customer> {
    private SystemUser systemUser;

    private CustomerCode customerCode;
    private PhoneNumber phoneNumber;
    private EmailAddress email;
    private String companyAddress;
    private String companyName;
    private SystemUser costumerManager;


    public CustomerBuilder withSystemUser(final SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    public CustomerBuilder withCustomerCode(final CustomerCode customerCode) {
        this.customerCode =  customerCode;
        return this;
    }

    public CustomerBuilder withCustomerCode(final String customerCode) {
        this.customerCode =  CustomerCode.valueOf(customerCode);
        return this;
    }

    public CustomerBuilder withEmail(final EmailAddress email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder withEmail(final String email) {
        this.email =  EmailAddress.valueOf(email);
        return this;
    }

    public CustomerBuilder withPhoneNumber(final String phoneNumber) {
        this.phoneNumber = PhoneNumber.valueOf(phoneNumber);
        return this;
    }
    public CustomerBuilder withPhoneNumber(final PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CustomerBuilder withCompanyName(final String companyName) {
        this.companyName = companyName;
        return this;
    }

    public CustomerBuilder withCompanyAddress(final String companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    public CustomerBuilder withCostumerManager(final SystemUser costumerManager) {
        this.costumerManager = costumerManager;
        return this;
    }

    @Override
    public Customer build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor

        return new Customer(this.systemUser, this.customerCode, this.email, this.phoneNumber, this.companyName, this.companyAddress, this.costumerManager);
    }
}
