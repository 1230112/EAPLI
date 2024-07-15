package eapli.base.app.backoffice.console.presentation.candidate;

import eapli.framework.actions.Action;

/**
 * Created by AJS on 08/04/2016.
 */
public class DisplayInfoCandidateAction implements Action {

    @Override
    public boolean execute() {
        return new DisplayInfoCandidateUI().show();
    }
}
