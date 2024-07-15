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
package eapli.base.infrastructure.bootstrapers;

import java.util.Set;

import eapli.base.candidatemanagement.application.CandidateService;
import eapli.base.candidatemanagement.application.ListCandidatesController;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobmanagement.AddJobApplicationController;
import eapli.base.jobmanagement.AddJobOpeningController;
import eapli.base.jobmanagement.JobService;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.base.usermanagement.application.AddUserController;
import eapli.base.usermanagement.application.ListUsersController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class UsersBootstrapperBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersBootstrapperBase.class);

    final AddUserController userController = new AddUserController(AuthzRegistry.authorizationService(), AuthzRegistry.userService(), PersistenceContext.repositories().candidates(), PersistenceContext.repositories().customers());
    final ListUsersController listUserController = new ListUsersController();
    final ListCandidatesController listCandidatesController = new ListCandidatesController(AuthzRegistry.authorizationService(), new CandidateService());
    final AddJobOpeningController jobOpeningController = new AddJobOpeningController(AuthzRegistry.authorizationService(), PersistenceContext.repositories().jobOpenings());
    final AddJobApplicationController jobApplicationController = new AddJobApplicationController(AuthzRegistry.authorizationService(), PersistenceContext.repositories().jobApplications(), new JobService());
    final UserRepository userRepository = PersistenceContext.repositories().users();
    public UsersBootstrapperBase() {
        super();
    }

    /**
     * @param username

     * @param firstName
     * @param lastName
     * @param email
     * @param roles
     */
    protected SystemUser registerUser(final String username, final String firstName,
            final String lastName, final String email, final Set<Role> roles) {
        SystemUser u = null;
        try {
            u = userController.addUser(email, firstName, lastName,  roles);
            LOGGER.debug("»»» %s", username);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // assuming it is just a primary key violation due to the tentative
            // of inserting a duplicated user. let's just lookup that user
            u = listUserController.find(Username.valueOf(username)).orElseThrow(() -> e);
        }
        return u;
    }
    protected Candidate registerCandidate(final String email, final String firstName,
                                           final String lastName, final String requirementsFile, final String phoneNumber) {
        Candidate u = null;
        try {
            u = userController.addCandidate(email, firstName, lastName,  requirementsFile, phoneNumber);
            LOGGER.debug("»»» %s", email);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // assuming it is just a primary key violation due to the tentative
            // of inserting a duplicated user. let's just lookup that user
            u = listCandidatesController.find(EmailAddress.valueOf(email)).orElseThrow(() -> e);
        }
        return u;
    }
    protected JobOpening registerJobOpening(final String jobReference, final String title, final String address, final String jobDescription, final String contractType, final int numberVacancies, final String mode, final String company) {
        JobOpening u = null;
        try {
            SystemUser user = userRepository.ofIdentity(Username.valueOf("cus@gmail.com"))
                    .orElseThrow(() -> new IllegalArgumentException("Unknown user "));
            u = jobOpeningController.addJobOpening(jobReference, title, address, jobDescription, contractType, numberVacancies, mode, company, user);
            LOGGER.debug("»»» %s", jobReference);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // assuming it is just a primary key violation due to the tentative
            // of inserting a duplicated user. let's just lookup that user

        }
        return u;
    }

    protected JobApplication registerJobApplication(final String jobReference, final String email, final String cvPath, final String screeningResult, final int jobApplicationId) {
        JobApplication u = null;
        try {
            u = jobApplicationController.addJobApplication(jobReference, email, cvPath, screeningResult, jobApplicationId);
            LOGGER.debug("»»» %s", u.jobApplicationId().toString());
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // error
        }
        return u;
    }
}
