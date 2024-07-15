package eapli.base.app.backoffice.console.presentation.authz;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.visitor.Visitor;

public class RolesPrinter implements Visitor<Role> {

    @Override
    public void visit(final Role role) {
        System.out.printf(role.toString());
    }
}
