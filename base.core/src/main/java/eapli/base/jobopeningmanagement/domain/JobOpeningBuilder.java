package eapli.base.jobopeningmanagement.domain;

import eapli.base.candidatemanagement.domain.Customer;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class JobOpeningBuilder implements DomainFactory<JobOpening> {

    private JobReference jobReference;
    private String title;
    private String address;
    private String jobDescription;
    private ContractType contractType;
    private int numberVacancies;
    private Mode mode;
    private String company;
    private SystemUser costumerManager;

    public JobOpeningBuilder withJobReference(String jobReference) {
        this.jobReference = JobReference.valueOf(jobReference);
        return this;
    }

    public JobOpeningBuilder withJobReference(JobReference jobReference) {
        this.jobReference = jobReference;
        return this;
    }

    public JobOpeningBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public JobOpeningBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public JobOpeningBuilder withJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public JobOpeningBuilder withContractType(String contractType) {
        this.contractType = ContractType.valueOf(contractType);
        return this;
    }

    public JobOpeningBuilder withContractType(ContractType contractType) {
        this.contractType = contractType;
        return this;
    }

    public JobOpeningBuilder withNumberVacancies(int numberVacancies) {
        this.numberVacancies = numberVacancies;
        return this;
    }

    public JobOpeningBuilder withMode(String mode) {
        this.mode = Mode.valueOf(mode);
        return this;
    }

    public JobOpeningBuilder withMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public JobOpeningBuilder withCompany(String company) {
        this.company = company;
        return this;
    }

    public JobOpeningBuilder withCostumerManager(SystemUser costumerManager) {
        this.costumerManager = costumerManager;
        return this;
    }


    @Override
    public JobOpening build() {
        return new JobOpening(this.jobReference, this.title, this.address, this.jobDescription,
                this.contractType, this.numberVacancies, this.mode, this.company, this.costumerManager);
    }
}
