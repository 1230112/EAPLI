package eapli.base.interviewmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Grade implements ValueObject {
    private static final long serialVersionUID = 1L;

    private Integer grade;

    private Grade(Integer grade) {
        Preconditions.noneNull(grade);
        validateGrade(grade);
        this.grade = grade;
    }



    protected Grade() {
        // for ORM only
    }

    public static Grade valueOf(Integer grade) {
        return new Grade(grade);
    }

    @Override
    public String toString() {
        return grade.toString();
    }

    private void validateGrade(Integer grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be an integer between 0 and 100");
        }
    }

    public Integer toInteger() {
return grade.intValue();

    }
}