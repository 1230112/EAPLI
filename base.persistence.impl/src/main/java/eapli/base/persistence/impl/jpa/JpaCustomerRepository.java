package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.candidatemanagement.domain.Customer;
import eapli.base.candidatemanagement.domain.CustomerCode;
import eapli.base.candidatemanagement.repositories.CustomerRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaCustomerRepository extends JpaAutoTxRepository<Customer, CustomerCode, CustomerCode>
        implements CustomerRepository {

    public JpaCustomerRepository(final TransactionalContext autoTx) {
        super(autoTx, "customer code");
    }

    public JpaCustomerRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "customerCode");
    }



    public Optional<Customer> findByCustomerCode(final CustomerCode customerCode) {
        final Map<String, Object> params = new HashMap<>();
        params.put("email", customerCode);
        return matchOne("e.customerCode=:customerCode", params);
    }


    @Override
    public Optional<Customer> findByUsername(final Username email) {
        final Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return matchOne("e.email=:email", params);}

    @Override
    public Iterable<Customer> findAllActive() {
        return match("e.systemUser.active = true");
    }
}
