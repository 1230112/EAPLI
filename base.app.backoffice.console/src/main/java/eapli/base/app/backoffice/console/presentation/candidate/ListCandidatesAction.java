package eapli.base.app.backoffice.console.presentation.candidate;

import eapli.base.app.backoffice.console.presentation.candidate.ListCandidatesUI;
import eapli.framework.actions.Action;

/**
 *
 * @author losa
 */
public class ListCandidatesAction implements Action {

    @Override
    public boolean execute() {
        return new ListCandidatesUI().show();
    }
}
