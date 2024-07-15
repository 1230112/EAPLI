package eapli.base.candidatemanagement.domain;


import eapli.framework.general.domain.model.EmailAddress;
import jakarta.persistence.*;
import eapli.base.usermanagement.domain.users.Email;
import eapli.base.candidatemanagement.DTO.CandidateDTO;
import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;
import eapli.base.candidatemanagement.domain.AppStatus;
import eapli.base.candidatemanagement.domain.RequirementsFile;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

@Entity
public class Candidate implements AggregateRoot<EmailAddress> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    @Id
    @Column(nullable = false)
    private EmailAddress email;

    @Column(nullable = true)
    private RequirementsFile requirementsFile;

    @Column(nullable = false)
    private PhoneNumber phoneNumber;
    /**
     * cascade = CascadeType. NONE as the systemUser is part of another aggregate
     */
    @Column (nullable = false)
    private AppStatus appStatus;
    @OneToOne(optional = false)
    private SystemUser systemUser;

    public Candidate(final SystemUser user, final EmailAddress email, final RequirementsFile requirementsFile, final PhoneNumber phoneNumber, final AppStatus appStatus) {

        this.email = email;
        this.systemUser = user;
        this.requirementsFile = requirementsFile;
        this.phoneNumber = phoneNumber;
        this.appStatus = appStatus;
    }

    protected Candidate() {
        // for ORM only
    }

    public SystemUser user() {
        return this.systemUser;
    }

    public EmailAddress email() {
        return this.email;
    }


    public RequirementsFile requirementsFile() {
        return this.requirementsFile;
    }

    public PhoneNumber phoneNumber() {
        return this.phoneNumber;
    }

    public AppStatus appStatus() {
        return this.appStatus;
    }
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

        final Candidate that = (Candidate) other;

        return this.user().sameAs(that.user()) && this.requirementsFile().equals(that.requirementsFile) && this.phoneNumber().equals(that.phoneNumber) && this.appStatus().equals(that.appStatus);
    }




    public CandidateDTO toDto() {
        return new CandidateDTO(this.email, this.systemUser.name(), this.requirementsFile, this.phoneNumber);
    }

    public ListCandidatesDTO toListDto() {
        return new ListCandidatesDTO(this.email, this.systemUser.name());
    }
    @Override
    public EmailAddress identity() {
        return this.email;
    }
}
