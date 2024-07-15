package eapli.base.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.util.Locale;

@Embeddable
@EqualsAndHashCode
public class CustomerCode implements ValueObject, Comparable<CustomerCode> {
    private static final long serialVersionUID = 1L;

    private String customerCode;

    private CustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    protected CustomerCode() {
        // for ORM only
    }

    public static CustomerCode valueOf(String customerCode) {
        customerCode = customerCode.trim();
        customerCode = customerCode.toUpperCase(Locale.ROOT);
        Preconditions.nonEmpty(customerCode, "email address should neither be null nor empty");
        Preconditions.ensure(isCustomerCode(customerCode), "Invalid Customer Code format");
        return new CustomerCode(customerCode);
    }

    public static boolean isCustomerCode(String customerCode) {
        if (customerCode.length() > 10 || customerCode.length() < 3) {
            return false;
        }

        if (containsNumbers(customerCode)) {
            return false;
        }

        return true;
    }

    public static boolean containsNumbers(String customerCode) {
        for (int i = 0; i < customerCode.length(); i++) {
            if (Character.isDigit(customerCode.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(CustomerCode other) {
        return this.customerCode.compareTo(other.customerCode);
    }

    @Override
    public String toString() {
        return customerCode;
    }
}