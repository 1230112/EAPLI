package eapli.base.jobopeningmanagement.domain;




import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

@Entity
public class JobOpening implements AggregateRoot<JobReference>, DTOable<JobOpeningDTO> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @Column(nullable = false)
    private JobReference jobReference;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private ContractType contractType;

    @Column(nullable = false)
    private int numberVacancies;

    @Column(nullable = false)
    private Mode mode;

    @Column(nullable = false)
    private String company;

    @ManyToOne
    private SystemUser costumerManager;


    public JobOpening(final JobReference jobReference, final String title, final String address, final String jobDescription,
                      final ContractType contractType, final int numberVacancies,
                      final Mode mode, final String company, final SystemUser costumerManager) {
        Preconditions.nonNull(jobReference, "Job Reference should not be null");
        Preconditions.nonNull(title, "Title should not be null");
        Preconditions.nonNull(address, "Address should not be null");
        Preconditions.nonNull(jobDescription, "Job Description should not be null");
        Preconditions.nonNull(contractType, "Contract Type should not be null");
        Preconditions.nonNull(mode, "Mode should not be null");
        Preconditions.nonNull(company, "Company should not be null");
        Preconditions.nonNull(costumerManager, "Costumer Manager should not be null");
        this.jobReference = jobReference;
        this.title = title;
        this.address = address;
        this.jobDescription = jobDescription;
        this.contractType = contractType;
        this.numberVacancies = numberVacancies;
        this.mode = mode;
        this.company = company;
        this.costumerManager = costumerManager;
    }

    protected JobOpening() { /* for ORM only*/ }


    public JobReference jobReference() { return this.jobReference; }
    public String title() { return this.title; }
    public String address() { return this.address; }

    public String jobDescription() { return this.jobDescription; }

    public ContractType contractType() { return this.contractType; }

    public int numberVacancies() { return this.numberVacancies; }

    public Mode mode() { return this.mode; }

    public String company() { return this.company; }

    public SystemUser costumerManager() { return this.costumerManager; }
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
        if (!(other instanceof JobOpening))
            return false;

        if (this == other)
            return true;

        final JobOpening that = (JobOpening) other;

        return this.jobReference().equals(that.jobReference) && this.title().equals(that.title) && this.address().equals(that.address) && this.jobDescription().equals(that.jobDescription) && this.contractType().equals(that.contractType) && (this.numberVacancies() == that.numberVacancies()) && this.mode().equals(that.mode) && this.company().equals(that.company) &&  this.costumerManager().sameAs(that.costumerManager);
    }


    public JobOpeningDTO toDTO() {
        return new JobOpeningDTO(jobReference.toString(), title, address, jobDescription, contractType.toString(), numberVacancies, mode.toString(), company, costumerManager.identity().toString());

    }

    @Override
    public JobReference identity() {
        return this.jobReference;
    }

}