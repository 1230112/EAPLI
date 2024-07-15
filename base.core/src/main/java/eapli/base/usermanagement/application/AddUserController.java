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
package eapli.base.usermanagement.application;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import eapli.base.candidatemanagement.domain.*;
import eapli.base.candidatemanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.candidatemanagement.repositories.CandidateRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.time.util.CurrentTimeCalendars;

/**
 *
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class AddUserController {

    private final AuthorizationService authz;
    private final UserManagementService userSvc;
    private final CandidateRepository candidateRepository;
    private final CustomerRepository customerRepository;

    public AddUserController(final AuthorizationService authz, final UserManagementService service, final CandidateRepository repository, final CustomerRepository customerRepository) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the repository. for
        // instance, unit testing.
        this.authz = authz;
        this.userSvc = service;
        this.candidateRepository = repository;
        this.customerRepository = customerRepository;
    }
    /**
     * Get existing RoleTypes available to the user.
     *
     * @return a list of RoleTypes
     */
    public Role[] getRoleTypes() {
        return BaseRoles.nonUserValues();
    }

    public SystemUser addUser(final String email, final String password, final String firstName,
                              final String lastName,
                               final Set<Role> roles, final Calendar createdOn) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR);

        return userSvc.registerNewUser(email, password, firstName, lastName, email, roles,
                createdOn);
    }

    public SystemUser addUser(final String customerCode, final String password, final String firstName,
                              final String lastName, final String email,
                              final Set<Role> roles, final Calendar createdOn) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR);

        return userSvc.registerNewUser(customerCode, password, firstName, lastName, email, roles,
                createdOn);
    }

    public SystemUser addUser(final String email, final String firstName,
                              final String lastName, final Set<Role> roles) {
        String password = PasswordGenerator.generateWithoutPolicyAndEncoder();
        return addUser(email, password, firstName, lastName, roles, CurrentTimeCalendars.now());
    }
    public Candidate addCandidate(final String email, final String firstName,
                              final String lastName, final String requirementsFile, final String phoneNumber) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.OPERATOR);
        Set<Role> roles = new HashSet<Role>();
        roles.add(BaseRoles.CANDIDATE);
        String password = PasswordGenerator.generateWithoutPolicyAndEncoder();
        SystemUser u = addUser(email, password, firstName, lastName, roles, CurrentTimeCalendars.now());
        Candidate s = new CandidateBuilder().withSystemUser(u).withEmail(email).withRequirementsFile(requirementsFile).withPhoneNumber(phoneNumber).withAppStatus("ENABLE").build();

        return candidateRepository.save(s);
    }

    public Customer addCustomer(final String email, final String firstName,
                                  final String lastName, final String phoneNumber, final String customerCode,
                                  final String companyName, final String companyAddress) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        Set<Role> roles = new HashSet<Role>();
        roles.add(BaseRoles.CUSTOMER);
        String password = PasswordGenerator.generateWithoutPolicyAndEncoder();
        SystemUser cm;
        try {
            cm = AuthzRegistry.authorizationService().session().get().authenticatedUser();
        } catch (Exception e) {
            cm = null;
        }
        SystemUser u = addUser(customerCode, password, firstName, lastName, email, roles, CurrentTimeCalendars.now());
        Customer s = new CustomerBuilder().withEmail(email).withSystemUser(u).withCustomerCode(customerCode)
                .withPhoneNumber(phoneNumber).withCompanyName(companyName).withCompanyAddress(companyAddress).withCostumerManager(cm).build();

        return customerRepository.save(s);
    }
}
