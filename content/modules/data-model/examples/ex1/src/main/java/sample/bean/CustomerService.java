package sample.bean;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.SaveContext;
import io.jmix.data.PersistenceHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomerService {

    // tag::data-manager[]
    @Autowired
    private DataManager dataManager;

    // end::data-manager[]

    // tag::hard-delete[]
    public void hardDeleteCustomer(Customer customer) {
        dataManager.save(
                new SaveContext()
                        .removing(customer)
                        .setSoftDeletion(false)
        );
    }
    // end::hard-delete[]

    // tag::load-hard-deleted[]
    public Customer loadHardDeletedCustomer(Id<Customer> customerId) {
        return dataManager.load(customerId).softDeletion(false).one();
    }
    // end::load-hard-deleted[]

    // tag::hard-delete-em[]
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void hardRemoveCustomerByEM(Customer customer) {
        entityManager.setProperty(PersistenceHints.SOFT_DELETION, false);
        entityManager.remove(customer);
    }
    // end::hard-delete-em[]
}
