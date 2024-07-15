package eapli.base.candidatemanagement.domain;


import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class PhoneNumber implements ValueObject, Comparable<PhoneNumber> {
    private static final long serialVersionUID = 1L;
    private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^\\+351\\d{9}$");

    private String phoneNumber;

    private PhoneNumber(String phoneNumber) {
        validatePortuguesePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int compareTo(PhoneNumber other) {
        return this.phoneNumber.compareTo(other.phoneNumber);
    }

    protected PhoneNumber() {
        // for ORM only
    }

    public static PhoneNumber valueOf(String phoneNumber) {
        Preconditions.ensure(PHONE_NUMBER_REGEX.matcher(phoneNumber).find(), "Invalid Portuguese phone number");
        return new PhoneNumber(phoneNumber);
    }

    @Override
    public String toString() {
        return phoneNumber;
    }

    private void validatePortuguesePhoneNumber(String phoneNumber) {
        // Portuguese phone numbers start with +351 and have 9 digits after
        String regex = "^\\+351\\d{9}$";
        if (!Pattern.matches(regex, phoneNumber)) {
            throw new IllegalArgumentException("Invalid Portuguese phone number");
        }
    }
}