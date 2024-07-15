package eapli.base.candidatemanagement.domain;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.base.candidatemanagement.domain.AppStatus;
import eapli.base.candidatemanagement.domain.RequirementsFile;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.validations.Preconditions;


    @Entity
    public class SignupRequest implements AggregateRoot<EmailAddress> {

        private static final long serialVersionUID = 1L;

        @Version
        private Long version;

        @EmbeddedId
        private EmailAddress email;


        private Password password;
        private Username username;
        private Name name;
        private RequirementsFile requirementsFile;
        private PhoneNumber phoneNumber;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AppStatus appStatus;
        @Temporal(TemporalType.DATE)
        @Column(nullable = false)
        private Calendar createdOn;

        /* package */ SignupRequest(final EmailAddress email, final Password password, final Name name, final RequirementsFile requirementsFile, final PhoneNumber phoneNumber, final AppStatus appStatus,
                                    final Calendar createdOn) {
            Preconditions.noneNull(email, password, name);

            this.email = email;

            this.password = password;
            this.name = name;
            this.requirementsFile = requirementsFile;
            this.phoneNumber = phoneNumber;
            // by default
            this.appStatus = appStatus;
            this.createdOn = createdOn;
        }

        protected SignupRequest() {
            // for ORM only
        }

        public void accept() {
            appStatus = AppStatus.valueOf("ENABLE");
        }

        public void refuse() {
            appStatus = AppStatus.valueOf("DISABLE");
        }

        @Override
        public boolean equals(final Object o) {
            return DomainEntities.areEqual(this, o);
        }

        @Override
        public int hashCode() {
            return DomainEntities.hashCode(this);
        }

        @Override
        public boolean sameAs(final Object other) {
            if (!(other instanceof SignupRequest)) {
                return false;
            }

            final SignupRequest that = (SignupRequest) other;
            if (this == that) {
                return true;
            }

            return  email.equals(that.email) && password.equals(that.password)
                    && name.equals(that.name) && requirementsFile.equals(that.requirementsFile) && phoneNumber.equals(that.phoneNumber) && appStatus.equals(that.appStatus);
        }


        @Override
        public EmailAddress identity() {
            return email;
        }



        public Name name() {
            return name;
        }

        public Username username() {
            return username;
        }

        public boolean isAccepted() {
            return appStatus == AppStatus.valueOf("ENABLE");
        }

        public boolean isRefused() {
            return appStatus == AppStatus.valueOf("DISABLE");
        }

        public EmailAddress email() {
            return email;
        }

        public Password password() {
            return password;
        }
        public RequirementsFile requirementsFile() {
            return requirementsFile;
        }
        public PhoneNumber phoneNumber() {
            return phoneNumber;
        }

        public AppStatus appStatus() {
            return appStatus;
        }
    }
