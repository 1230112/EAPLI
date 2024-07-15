package eapli.base.interviewmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import eapli.base.interviewmanagement.application.InterviewApplicationService;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Embeddable
@EqualsAndHashCode
public class DateTime implements ValueObject {
    private static final long serialVersionUID = 1L;

    private Calendar dateTime;

    private DateTime(Calendar dateTime) {
        Preconditions.nonNull(dateTime);
        validateDateTime(dateTime);
        this.dateTime = dateTime;
    }



    protected DateTime() {
        // for ORM only
    }

    public static DateTime valueOf(Calendar dateTime) {
        return new DateTime(dateTime);
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    public Calendar toCalendar() {
        return this.dateTime;
    }
    private void validateDateTime(Calendar dateTime) {
        try {
            /*ATENTION!!
            * Here I should validate that the date inserted is between the startDate and endDate of the InterviewPhase.
            * It would be in the agregate of jobOpening, so we should create a method in any service where we can
            * access to these datas.
            * */

            LocalDate date = LocalDate.of(dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH) + 1, dateTime.get(Calendar.DAY_OF_MONTH));

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            String dayOfWeek = dayFormat.format(dateTime.getTime());

            System.out.println("The day of the week is: " + dayOfWeek);
            if (!date.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("The date you entered is not in the future. Please try again.");
            }
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("The date you entered is not valid. Please try again.");
        }
    }


}