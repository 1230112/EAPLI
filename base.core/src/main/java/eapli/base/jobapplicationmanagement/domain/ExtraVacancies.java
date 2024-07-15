package eapli.base.jobapplicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Embeddable
@EqualsAndHashCode
public class ExtraVacancies implements ValueObject {
    private static final long serialVersionUID = 1L;

    private Float extraVacancies;

    private ExtraVacancies(Float extraVacancies) {
        Preconditions.nonNull(extraVacancies);

        this.extraVacancies = extraVacancies;
        validateExtraVacancies(extraVacancies);
    }



    protected ExtraVacancies() {
        // for ORM only
    }

    public static ExtraVacancies valueOf(Float extraVacancies) {
        return new ExtraVacancies(extraVacancies);
    }

    @Override
    public String toString() {
        return extraVacancies.toString();
    }

public Float toFloat() {
        return this.extraVacancies;
    }
    private void validateExtraVacancies(Float extraVacancies) {
        if(extraVacancies < 0  ) {
            throw new IllegalArgumentException("The number of extra vacancies must be a positive integer.");
        }
    }


}
