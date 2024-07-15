package eapli.base.app.backoffice.console.presentation.authz;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobmanagement.AddJobOpeningController;
import eapli.base.jobopeningmanagement.domain.ContractType;
import eapli.base.jobopeningmanagement.domain.Mode;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import java.util.HashSet;
import java.util.Set;

public class AddJobOpeningUI extends AbstractUI {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AddJobOpeningController theController = new AddJobOpeningController(authz, PersistenceContext.repositories().jobOpenings());

    @Override
    protected boolean doShow() {
        try {
            final String title = Console.readLine("Title/Function:");
            final String address = Console.readLine("Job Address:");
            final String jobDescription = Console.readLine("Job Description:");

            final Set<ContractType> contractTypeSet = new HashSet<>();
            boolean show;
            do {
                show = showContractType(contractTypeSet);
            } while (!show);
            if (contractTypeSet.isEmpty()) {
                contractTypeSet.add(ContractType.valueOf(Console.readLine("Contract Type:")));
            }
            final String contractType = contractTypeSet.iterator().next().toString();

            int numberVacancies;
            while (true) {
                try {
                    numberVacancies = Integer.parseInt(Console.readLine("Number of Vacancies:"));
                    break;
                } catch (final NumberFormatException e) {
                    System.out.println("Please enter a valid number!");
                }
            }

            final Set<Mode> modeSet = new HashSet<>();
            show = false;
            do {
                show = showMode(modeSet);
            } while (!show);
            if (modeSet.isEmpty()) {
                modeSet.add(Mode.valueOf(Console.readLine("Mode:")));
            }
            final String mode = modeSet.iterator().next().toString();

            final String company = Console.readLine("Company:");

            theController.addJobOpening(title, address, jobDescription, contractType, numberVacancies, mode, company);
            return true;
        } catch (final Exception e) {
            System.out.println("An error occurred. Please try again.");
            return false;
        }
    }

    private boolean showContractType(final Set<ContractType> contractType){
        final Menu contractTypeMenu = buildContractTypeMenu(contractType);
        final MenuRenderer renderer = new VerticalMenuRenderer(contractTypeMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildContractTypeMenu(final Set<ContractType> contractType){
        final Menu contractTypeMenu = new Menu();
        int counter = 0;
        contractTypeMenu.addItem(MenuItem.of(counter++, "Full Time", () -> contractType.add(ContractType.valueOf("Full Time"))));
        contractTypeMenu.addItem(MenuItem.of(counter++, "Part Time", () -> contractType.add(ContractType.valueOf("Part Time"))));
        contractTypeMenu.addItem(MenuItem.of(counter++, "Other", () -> contractType.add(ContractType.valueOf("Other"))));
        return contractTypeMenu;
    }

    private boolean showMode(final Set<Mode> modeSet){
        final Menu modeMenu = buildModeMenu(modeSet);
        final MenuRenderer renderer = new VerticalMenuRenderer(modeMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildModeMenu(final Set<Mode> modeSet){
        final Menu modeMenu = new Menu();
        int counter = 0;
        modeMenu.addItem(MenuItem.of(counter++, "Onsite", () -> modeSet.add(Mode.valueOf("Onsite"))));
        modeMenu.addItem(MenuItem.of(counter++, "Remote", () -> modeSet.add(Mode.valueOf("Remote"))));
        modeMenu.addItem(MenuItem.of(counter++, "Hybrid", () -> modeSet.add(Mode.valueOf("Hybrid"))));
        modeMenu.addItem(MenuItem.of(counter++, "Other", () -> modeSet.add(Mode.valueOf("Other"))));
        return modeMenu;
    }

    @Override
    public String headline() { return "Add Job Opening"; }
}
