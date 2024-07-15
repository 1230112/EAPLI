package eapli.base.candidatemanagement.repositories;

import eapli.base.candidatemanagement.domain.Customer;
import eapli.base.candidatemanagement.domain.CustomerCode;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Username;

import java.util.Optional;

public interface CustomerRepository extends DomainRepository<CustomerCode, Customer> {


    Optional<Customer> findByCustomerCode(CustomerCode customerCode);
    Optional<Customer> findByUsername(Username name);
    Iterable<Customer> findAllActive();
}
