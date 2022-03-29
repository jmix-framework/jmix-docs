package dataaccess.ex1.bean;

import dataaccess.ex1.entity.Customer;
import dataaccess.ex1.entity.CustomerGrade;
import dataaccess.ex1.entity.CustomerGradeChange;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.LogicalCondition;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.UUID;

// tag::inject-dm[]
@Component
public class CustomerService {

    @Autowired
    private DataManager dataManager;
    // end::inject-dm[]

    // tag::load-by-id[]
    Customer loadById(UUID customerId) {
        return dataManager.load(Customer.class) // <1>
                .id(customerId)                 // <2>
                .one();                         // <3>
    }
    // end::load-by-id[]

    // tag::load-by-generic-id[]
    Customer loadByGenericId(Id<Customer> customerId) {
        return dataManager.load(customerId).one();
    }
    // end::load-by-generic-id[]

    // tag::load-optional[]
    Customer loadOrCreate(UUID customerId) {
        return dataManager.load(Customer.class)
                .id(customerId)
                .optional() // <1>
                .orElse(dataManager.create(Customer.class));
    }
    // end::load-optional[]

    // tag::load-by-ids[]
    List<Customer> loadByIds(UUID id1, UUID id2) {
        return dataManager.load(Customer.class)
                .ids(id1, id2)
                .list();
    }
    // end::load-by-ids[]


    // tag::load-all[]
    List<Customer> loadAll() {
        return dataManager.load(Customer.class).all().list();
    }
    // end::load-all[]

    // tag::load-by-query[]
    List<Customer> loadByQuery() {
        return dataManager.load(Customer.class)
                .query("e.email like ?1 and e.grade = ?2", "%@company.com", CustomerGrade.PLATINUM)
                .list();
    }
    // end::load-by-query[]

    // tag::load-by-full-query[]
    List<Customer> loadByFullQuery() {
        return dataManager.load(Customer.class)
                .query("select c from sample_Customer c where c.email like :email and c.grade = :grade")
                .parameter("email", "%@company.com")
                .parameter("grade", CustomerGrade.PLATINUM)
                .list();
    }
    // end::load-by-full-query[]

    // tag::load-page-by-query[]
    List<Customer> loadPageByQuery(int offset, int limit) {
        return dataManager.load(Customer.class)
                .query("e.grade = ?1", CustomerGrade.BRONZE)
                .firstResult(offset)
                .maxResults(limit)
                .list();
    }
    // end::load-page-by-query[]

    // tag::load-by-conditions[]
    List<Customer> loadByConditions() {
        return dataManager.load(Customer.class)
                .condition(                                                      // <1>
                    LogicalCondition.and(                                        // <2>
                        PropertyCondition.contains("email", "@company.com"),     // <3>
                        PropertyCondition.equal("grade", CustomerGrade.PLATINUM) // <3>
                    )
                )
                .list();
    }
    // end::load-by-conditions[]

    // tag::load-by-condition[]
    List<Customer> loadByCondition() {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.contains("email", "@company.com"))
                .list();
    }
    // end::load-by-condition[]

    // tag::load-and-sort[]
    List<Customer> loadSorted() {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.contains("email", "@company.com"))
                .sort(Sort.by("name"))
                .list();
    }
    // end::load-and-sort[]

    // tag::load-by-query-sorted[]
    List<Customer> loadByQuerySorted() {
        return dataManager.load(Customer.class)
                .query("e.grade = ?1 order by e.name", CustomerGrade.BRONZE)
                .list();
    }
    // end::load-by-query-sorted[]

    // tag::save[]
    Customer saveCustomer(Customer entity) {
        return dataManager.save(entity);
    }
    // end::save[]

    // tag::remove[]
    void removeCustomer(Customer entity) {
        dataManager.remove(entity);
    }
    // end::remove[]

    // tag::remove-list[]
    void removeCustomers(List<Customer> entities) {
        dataManager.remove(entities);
    }
    // end::remove-list[]

    // tag::remove-by-id[]
    void removeCustomer(UUID customerId) {
        dataManager.remove(Id.of(customerId, Customer.class));
    }
    // end::remove-by-id[]

    // unused example
/*    @Transactional
    void updateCustomerGrade(UUID customerId, CustomerGrade newGrade) {
        Customer customer = dataManager.load(Customer.class)
                .id(customerId)
                .optional() // <1>
                .orElse(dataManager.create(Customer.class));
        String oldGrade = customer.getGrade().getId();
        customer.setGrade(newGrade);
        dataManager.save(customer);
        CustomerGradeChange change = dataManager.create(CustomerGradeChange.class);
        change.setCustomer(customer);
        change.setOldGrade(CustomerGrade.fromId(oldGrade));
        change.setNewGrade(newGrade);
        dataManager.save(change);
    }*/

    // tag::transaction-template-inject[]
    @Autowired
    private TransactionTemplate transactionTemplate;
    // end::transaction-template-inject[]

    // tag::transaction-template-without-result[]
    public void createCustomer() {
        transactionTemplate.executeWithoutResult(status -> {
            Customer customer = dataManager.create(Customer.class);
            customer.setName("Alice");
            dataManager.save(customer);
        });
    }
    // end::transaction-template-without-result[]



}
