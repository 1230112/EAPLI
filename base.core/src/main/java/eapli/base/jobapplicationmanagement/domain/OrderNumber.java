package eapli.base.jobapplicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class OrderNumber implements ValueObject, Comparable<OrderNumber> {
    private static final long serialVersionUID = 1L;

    private Integer orderNumber;

    private OrderNumber(Integer orderNumber) {
        Preconditions.noneNull(orderNumber);

        this.orderNumber = orderNumber;
        if (orderNumber < 0) {
            throw new IllegalArgumentException("Order number must be a positive integer.");
        }
    }

    @Override
    public int compareTo(OrderNumber other) {
        return this.orderNumber.compareTo(other.orderNumber);
    }

    protected OrderNumber() {
        // for ORM only
    }

    public static OrderNumber valueOf(Integer orderNumber) {
        return new OrderNumber(orderNumber);
    }

    @Override
    public String toString() {
        return orderNumber.toString();
    }
}