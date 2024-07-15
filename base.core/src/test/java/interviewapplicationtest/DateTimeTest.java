package interviewapplicationtest;


import eapli.base.interviewmanagement.domain.DateTime;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeTest {

    @Test
    public void ensureDateTimeCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateTime.valueOf(null);
        });
    }

    @Test
    public void ensureInvalidDateThrowsException() {
        try {

            DateTime.valueOf(new GregorianCalendar(2025, Calendar.FEBRUARY, 30));
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("The date you entered is not valid. Please try again.");
        }
    }
    @Test
    public void ensureDateTimeCannotBeInPast() {
        Calendar pastDate = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            DateTime.valueOf(pastDate);
        });
    }

    @Test
    public void ensureDateTimeCanBeInFuture() {
        Calendar futureDate = new GregorianCalendar(3000, Calendar.JANUARY, 1);
        assertDoesNotThrow(() -> {
            DateTime.valueOf(futureDate);
        });
    }

    @Test
    public void ensureDateTimeToStringReturnsCorrectString() {
        Calendar date = new GregorianCalendar(2025, Calendar.JANUARY, 1);
        DateTime dateTime = DateTime.valueOf(date);
        assertEquals(date.toString(), dateTime.toString());
    }

    @Test
    public void ensureDateTimeToCalendarReturnsCorrectCalendar() {
        Calendar date = new GregorianCalendar(2025, Calendar.JANUARY, 1);
        DateTime dateTime = DateTime.valueOf(date);
        assertEquals(date, dateTime.toCalendar());
    }
}