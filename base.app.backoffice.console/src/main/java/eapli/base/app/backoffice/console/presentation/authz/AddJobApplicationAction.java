package eapli.base.app.backoffice.console.presentation.authz;

import eapli.framework.actions.Action;

public class AddJobApplicationAction implements Action {
    @Override
    public boolean execute() { return new AddJobApplicationUI().show(); }
}
