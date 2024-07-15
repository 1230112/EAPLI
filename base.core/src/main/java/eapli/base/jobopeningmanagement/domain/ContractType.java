package eapli.base.jobopeningmanagement.domain;


import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ContractType implements ValueObject, Comparable<ContractType>{

    private String contractType;

    public ContractType(final String contractType) {
        this.contractType = contractType;
    }

    public ContractType() {
        // for ORM only
    }

    public static ContractType valueOf(String contractType) {
        Preconditions.noneNull(contractType, "Contract Type should not be null");
        return new ContractType(contractType);
    }

    @Override
    public int compareTo(ContractType o) {
        if(this.contractType.equals(o.contractType)) return 0;
        return -1;
    }

    @Override
    public String toString() { return contractType; }
}
