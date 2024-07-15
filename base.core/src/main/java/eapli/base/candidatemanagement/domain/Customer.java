package eapli.base.candidatemanagement.domain;

import eapli.base.candidatemanagement.DTO.CustomerDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@Entity
public class Customer implements AggregateRoot<CustomerCode> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @Column(nullable = false)
    private CustomerCode customerCode;

    @Column(nullable = false)
    private EmailAddress emailAddress;

    @Column(nullable = false)
    private PhoneNumber phoneNumber;

    @Column(nullable = false)
    private String companyAddress;

    @Column(nullable = false)
    private String companyName;

    @OneToOne(optional = false)
    private SystemUser systemUser;

    @ManyToOne
    private SystemUser costumerManager;

    public Customer(final SystemUser user, final CustomerCode customerCode, final EmailAddress email, final PhoneNumber phoneNumber, final String companyName, final String companyAddress, final SystemUser costumerManager) {

        this.systemUser = user;
        this.customerCode = customerCode;
        this.phoneNumber = phoneNumber;
        this.emailAddress = email;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.costumerManager = costumerManager;
    }

    protected Customer() {
        // for ORM only
    }

    public SystemUser user() {
        return this.systemUser;
    }


    public CustomerCode customerCode() {
        return this.customerCode;
    }

    public PhoneNumber phoneNumber() {
        return this.phoneNumber;
    }

    public EmailAddress email() {
        return this.emailAddress;
    }

    public String companyName() { return this.companyName; }

    public String companyAddress() { return this.companyAddress; }


    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof Candidate))
            return false;

        if (this == other)
            return true;

        final Customer that = (Customer) other;

        return this.user().sameAs(that.user()) && this.customerCode().equals(that.customerCode) && this.email().equals(that.emailAddress) && this.phoneNumber().equals(that.phoneNumber) && this.companyName().equals(that.companyName) && this.companyAddress().equals(that.companyAddress);
    }




    public CustomerDTO toDto() {
        return new CustomerDTO(this.systemUser.name(), this.customerCode, this.emailAddress, this.phoneNumber, this.companyName, this.companyAddress);
    }

    @Override
    public CustomerCode identity() {
        return this.customerCode;
    }
}
