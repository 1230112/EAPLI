package eapli.base.app.backoffice.console.presentation.interviewapplication;

import eapli.base.app.common.console.presentation.jobApplication.JobApplicationDTOPrinter;
import eapli.base.app.common.console.presentation.jobOpening.JobOpeningDTOPrinter;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobapplicationmanagement.DTO.JobApplicationDTO;
import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.base.interviewmanagement.application.ScheaduleInterviewController;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ScheaduleInterviewUI extends AbstractUI {

    // dependency injection - when constructing the object one must inject the dependencies to
    // infrastructure objects it needs. this should be handled by a DI/IoC container like Spring
    // Framework
    private final ScheaduleInterviewController theController = new ScheaduleInterviewController(
            AuthzRegistry.authorizationService(), PersistenceContext.repositories().interviewApplications(), PersistenceContext.repositories().jobOpenings(), PersistenceContext.repositories().users(), PersistenceContext.repositories().jobApplications());

    @Override
    protected boolean doShow() {

        final Iterable<JobOpeningDTO> jobOpening = this.theController.listMyJobOpeningsForScheaduleInterview();
        if (!jobOpening.iterator().hasNext()) {
            System.out.println("You don't have any JobOpenings assigned. Nothing to set.");
            return false;
        }
        SelectWidget<JobOpeningDTO> selector = new SelectWidget<JobOpeningDTO>("Job Openings:", jobOpening,
                new JobOpeningDTOPrinter());
        selector.show();
        final JobOpeningDTO theJobOpening = selector.selectedElement();
        final Iterable<JobApplicationDTO> jobApplication = this.theController.listAcceptedApplicationsForInterview(theJobOpening);
        if (!jobApplication.iterator().hasNext()) {
            System.out.println("You don't have any JobApplications assigned. Nothing to set.");
            return false;
        }




            SelectWidget<JobApplicationDTO> selectorapli = new SelectWidget<>("Job Applications:", jobApplication,
                    new JobApplicationDTOPrinter());
            selectorapli.show();
            final JobApplicationDTO theJobApplication = selectorapli.selectedElement();

        Calendar dateTime = null;
        boolean validDate = false;
        do {
            try {
                int year = Console.readInteger("Enter Year (yyyy): ");
                int month = Console.readInteger("Enter Month (1-12): ");
                int day = Console.readInteger("Enter Day (1-31): ");
                LocalDate date = LocalDate.of(year, month, day);

                int hour = Console.readInteger("Enter Hour (0-23): ");
                int minute = Console.readInteger("Enter Minute (0-59): ");
                int second = Console.readInteger("Enter Second (0-59): ");

                dateTime = Calendar.getInstance();
                dateTime.set(year, month - 1, day, hour, minute, second);
                validDate = true;
            } catch (DateTimeException e) {
                System.out.println("The date you entered is not valid. Please try again.");
            }
        } while (!validDate);



        try {
            this.theController.scheaduleInterview(dateTime, theJobApplication);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to set a interview which already exists in the database.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Scheadule Interview";
    }
}