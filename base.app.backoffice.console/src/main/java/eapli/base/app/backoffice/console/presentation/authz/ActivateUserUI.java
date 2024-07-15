package eapli.base.app.backoffice.console.presentation.authz;

import eapli.base.usermanagement.application.ActivateUserController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ActivateUserUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeactivateUserUI.class);

    private final ActivateUserController theController = new ActivateUserController();

    @Override
    protected boolean doShow() {
        final List<SystemUser> list = new ArrayList<>();
        final Iterable<SystemUser> iterable = this.theController.deactivatedUsers();
        if (!iterable.iterator().hasNext()) {
            System.out.println("There is no registered User");
        } else {
            int cont = 1;
            System.out.println("SELECT User to activate\n");
            // FIXME use select widget, see, ChangeDishTypeUI
            System.out.printf("%-6s%-23s%-15s%-15s%n", "Nº:", "UEmail", "Firstname", "Lastname");
            for (final SystemUser user : iterable) {
                list.add(user);
                System.out.printf("%-6d%-23s%-15s%-15s%n", cont, user.email(), user.name().firstName(),
                        user.name().lastName());
                cont++;
            }
            final int option = Console.readInteger("Enter user nº to activate or 0 to finish ");
            if (option == 0) {
                System.out.println("No user selected");
            } else {
                try {
                    this.theController.activateUser(list.get(option - 1));
                } catch (IntegrityViolationException | ConcurrencyException ex) {
                    LOGGER.error("Error performing the operation", ex);
                    System.out.println(
                            "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
                }
            }
        }
        return true;
    }

    @Override
    public String headline() {
        return "Deactivate User";
    }
}
