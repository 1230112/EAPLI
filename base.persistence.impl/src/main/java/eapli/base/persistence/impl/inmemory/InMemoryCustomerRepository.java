package eapli.base.persistence.impl.inmemory;

import eapli.base.candidatemanagement.domain.Customer;
import eapli.base.candidatemanagement.domain.CustomerCode;
import eapli.base.candidatemanagement.repositories.CustomerRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryCustomerRepository extends InMemoryDomainRepository<Customer, CustomerCode>
        implements CustomerRepository {

    static {
        InMemoryInitializer.init();
    }



    @Override
    public Optional<Customer> findByCustomerCode(final CustomerCode customerCode) {
        return matchOne(e -> e.user().identity().equals(customerCode));
    }
    @Override
    public Optional<Customer> findByUsername(final Username username) {
        return matchOne(e -> e.user().username().equals(username));
    }
    @Override
    public Iterable<Customer> findAllActive() {
        return match(e -> e.user().isActive());
    }
}
