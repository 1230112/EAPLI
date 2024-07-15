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

import eapli.base.usermanagement.application.ListUsersController;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.visitor.Visitor;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author losa
 */
@SuppressWarnings({ "squid:S106" })
public class ListUsersUI extends AbstractListUI<SystemUser> {
    private static final int ROLE_OPTION = 1;
    private static final int ALL_OPTION = 2;
    private static final int EXIT_OPTION = 0;

    private Iterable<SystemUser> listOfUsers;
    private ListUsersController theController = new ListUsersController();

    @Override
    protected boolean doShow(){
        System.out.println("1. Specific Role\n2. All\n0. Return");
        int option = Console.readOption(ROLE_OPTION, ALL_OPTION, EXIT_OPTION);
        listOfUsers = theController.allUsers();
        switch (option){
            case ROLE_OPTION:
                System.out.println("Specific Role");
                final SelectWidget<Role> selector = new SelectWidget<>("Roles Available", List.of(this.theController.allRoles()), new RolesPrinter());
                selector.show();
                final Role role = selector.selectedElement();
                List<SystemUser> usersOfRole = new LinkedList<>();
                for (SystemUser systemUser : listOfUsers){
                    if (systemUser.hasAny(role)){
                        usersOfRole.add(systemUser);
                    }
                }
                listOfUsers = usersOfRole;
                break;

            case ALL_OPTION:
                System.out.println("All");
                break;
            default:
                return false;
        }
        return super.doShow();
    }

    @Override
    public String headline() {
        return "List Users";
    }

    @Override
    protected String emptyMessage() {
        return "No data.";
    }

    @Override
    protected Iterable<SystemUser> elements() { return listOfUsers; }

    @Override
    protected Visitor<SystemUser> elementPrinter() {
        return new SystemUserPrinter();
    }

    @Override
    protected String elementName() {
        return "User";
    }

    @Override
    protected String listHeader() {
        return String.format("#  %-23s%-15s%-15s", "EMAIL", "F. NAME", "L. NAME");
    }
}
