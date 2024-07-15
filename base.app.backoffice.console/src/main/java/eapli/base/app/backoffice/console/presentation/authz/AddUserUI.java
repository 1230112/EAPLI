/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.backoffice.console.presentation.authz;

import java.util.HashSet;
import java.util.Set;
import eapli.base.app.common.console.presentation.candidate.CandidateDataWidget;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.app.common.console.presentation.customer.CustomerDataWidget;
import eapli.base.usermanagement.application.AddUserController;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * UI for adding a user to the application.
 *
 * Created by nuno on 22/03/16.
 */
public class AddUserUI extends AbstractUI {

    private final AddUserController theController = new AddUserController(AuthzRegistry.authorizationService(), AuthzRegistry.userService(), PersistenceContext.repositories().candidates(), PersistenceContext.repositories().customers());
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    @Override
    protected boolean doShow() {
        // FIXME avoid duplication with SignUpUI. reuse UserDataWidget from


        final Set<Role> roleTypes = new HashSet<>();


        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.OPERATOR)) {
            roleTypes.add(BaseRoles.CANDIDATE);
        } else if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.CUSTOMER_MANAGER)){
            roleTypes.add(BaseRoles.CUSTOMER);
        } else {
            boolean show;
            do {
                show = showRoles(roleTypes);
            } while (!show);
        }


        try {
            for (Role role : roleTypes) {
                if (role.equals(BaseRoles.CANDIDATE)) {
                    final CandidateDataWidget userData = new CandidateDataWidget();
                    userData.show();
                    Thread.sleep(1000);
                    theController.addCandidate(userData.email(), userData.firstName(),
                            userData.lastName(), userData.requirementsFile(), userData.phoneNumber());

                } else if (role.equals(BaseRoles.CUSTOMER)) {
                    final CustomerDataWidget userData = new CustomerDataWidget();
                    userData.show();
                    Thread.sleep(1000);
                    theController.addCustomer(userData.email(), userData.firstName(),
                            userData.lastName(), userData.phoneNumber(), userData.customerCode(), userData.companyName(), userData.companyAddress());

                } else {
                    final SystemUserDataWidget userData = new SystemUserDataWidget();
                    userData.show();
                    Thread.sleep(1000);
                    theController.addUser(userData.email(), userData.firstName(),
                            userData.lastName(), roleTypes);
                }
            }

        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println("That email is already in use.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private boolean showRoles(final Set<Role> roleTypes) {
        // TODO we could also use the "widget" classes from the framework...
        final Menu rolesMenu = buildRolesMenu(roleTypes);
        final MenuRenderer renderer = new VerticalMenuRenderer(rolesMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildRolesMenu(final Set<Role> roleTypes) {
        final Menu rolesMenu = new Menu();
        int counter = 0;
        rolesMenu.addItem(MenuItem.of(counter++, "No Role", Actions.SUCCESS));
        for (final Role roleType : theController.getRoleTypes()) {
            rolesMenu.addItem(MenuItem.of(counter++, roleType.toString(), () -> roleTypes.add(roleType)));
        }
        return rolesMenu;
    }

    @Override
    public String headline() {
        return "Add User";
    }
}
