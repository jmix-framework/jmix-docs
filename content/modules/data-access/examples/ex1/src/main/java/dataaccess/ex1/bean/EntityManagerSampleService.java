package dataaccess.ex1.bean;

import dataaccess.ex1.entity.Customer;
import dataaccess.ex1.entity.Foo;
import dataaccess.ex1.entity.Order;
import dataaccess.ex1.entity.Product;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.core.Metadata;
import io.jmix.data.PersistenceHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.UUID;

@Component("sample_EntityManagerSampleService")
public class EntityManagerSampleService {

    @Autowired
    private Metadata metadata;

    // tag::em-inject[]
    @PersistenceContext
    private EntityManager entityManager;

    // end::em-inject[]

    // tag::em-db1-inject[]
    @PersistenceContext(unitName = "db1")
    private EntityManager entityManagerForDb1;

    // end::em-db1-inject[]

    // tag::em-persist[]
    @Transactional
    public Customer createCustomer() {
        Customer customer = metadata.create(Customer.class);
        customer.setName("Bob");
        entityManager.persist(customer);
        return customer;
    }
    // end::em-persist[]

    // tag::em-db1-persist[]
    @Transactional("db1TransactionManager")
    public Foo createFoo() {
        Foo foo = metadata.create(Foo.class);
        foo.setName("foo1");
        entityManagerForDb1.persist(foo);
        return foo;
    }
    // end::em-db1-persist[]

    // tag::em-find[]
    @Autowired
    private FetchPlans fetchPlans;

    @Transactional
    public Order findOrder(UUID orderId) {
        FetchPlan fetchPlan = fetchPlans.builder(Order.class)
                .add("customer")
                .build();

        Map<String, Object> properties = PersistenceHints.builder()
                .withFetchPlan(fetchPlan)
                .build();

        return entityManager.find(Order.class, orderId, properties);
    }
    // end::em-find[]

    // tag::em-load-partial[]
    @Transactional
    public Order loadGraphOfPartialEntities(UUID orderId) {
        FetchPlan fetchPlan = fetchPlans.builder(Order.class)
                .addAll("number", "date", "customer.name")
                .partial()
                .build();

        Map<String, Object> properties = PersistenceHints.builder()
                .withFetchPlan(fetchPlan)
                .build();

        return entityManager.find(Order.class, orderId, properties);
    }
    // end::em-load-partial[]


    // tag::em-hard-delete[]
    @Transactional
    public void hardDelete(Product product) {
        entityManager.setProperty(PersistenceHints.SOFT_DELETION, false);
        entityManager.remove(product);
    }
    // end::em-hard-delete[]
}