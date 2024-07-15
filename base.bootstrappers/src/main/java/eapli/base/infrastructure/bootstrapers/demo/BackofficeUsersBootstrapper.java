/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import java.util.HashSet;
import java.util.Set;

import eapli.base.infrastructure.bootstrapers.UsersBootstrapperBase;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * @author Paulo Gandra Sousa
 */
public class BackofficeUsersBootstrapper extends UsersBootstrapperBase implements Action {

    @SuppressWarnings("squid:S2068")


    @Override
    public boolean execute() {
        registerCustomerManager("cus@gmail.com", "Cus", "Tomer", "cus@gmail.com");
        registerOperator("ope@gmail.com", "Ope", "Rator", "ope@gmail.com");
        registerCandidates("elena@gmail.com", "Elena", "Molero", "Informatic Engineering Student", "+351123123123");
        registerCandidates("ana@gmail.com", "Ana", "Cano", "Industrial Engineering Student", "+351123123678");

        registerOperator("johny.doe@gmail.com", "Johny", "Cash", "johny.doe@gmail.com");
        registerCustomerManager("master.chef@gmail.com","Master", "Chef", "master.chef@gmail.com");
        registerLanguageEngineer("ling.anat@gmail.com", "Linguini", "Anatoli", "ling.anat@gmail.com");
        registerJobOpenings("jobreference1", "title1",  "address1", "jobDescription1", "contractType1", 1, "mode1", "company1");
        registerJobOpenings("jobreference2", "title1",  "address1", "jobDescription1", "contractType1", 1, "mode1", "company1");

        registerJobApplications("jobreference1", "elena@gmail.com", "AppDocs/candidate_elena.txt", "WAITING", 001);
        registerJobApplications("jobreference1", "ana@gmail.com", "AppDocs/candidate_ana.txt", "ACCEPTED", 002);
        registerJobApplications("jobreference2", "elena@gmail.com", "AppDocs/candidate_elena2.txt", "ACCEPTED", 003);

        return true;
    }




    private void registerCustomerManager(final String username,
                                     final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.CUSTOMER_MANAGER);

        registerUser(username,  firstName, lastName, email, roles);
    }
    private void registerJobOpenings(final String jobReference, final String title, final String address, final String jobDescription, final String contractType, final int numberVacancies, final String mode, final String company) {
        registerJobOpening(jobReference, title, address, jobDescription, contractType, numberVacancies, mode, company);
    }

    private void registerJobApplications(final String jobReference, final String email, final String cvPath, final String ScreeningResult, final int jobApplicationId) {
        registerJobApplication(jobReference, email, cvPath, ScreeningResult, jobApplicationId);
    }

    private void registerLanguageEngineer(final String username,
                                     final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.LANGUAGE_ENGINEER);

        registerUser(username,  firstName, lastName, email, roles);
    }

    private void registerOperator(final String username,
                                         final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.OPERATOR);

        registerUser(username,  firstName, lastName, email, roles);
    }

    private void registerCandidates(final String email, final String firstName, final String lastName, final String requirementsFile, final String phoneNumber) {
        registerCandidate(email, firstName, lastName, requirementsFile, phoneNumber);
    }
}
