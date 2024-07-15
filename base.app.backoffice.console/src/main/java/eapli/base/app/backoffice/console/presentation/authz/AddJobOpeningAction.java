package eapli.base.app.backoffice.console.presentation.authz;

import eapli.framework.actions.Action;

public class AddJobOpeningAction implements Action {
    @Override
    public boolean execute() { return new AddJobOpeningUI().show(); }
}
