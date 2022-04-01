package dataaccess.ex1.bean;

import dataaccess.ex1.entity.Customer;
import dataaccess.ex1.entity.Order;
import io.jmix.core.DataManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

@Component("sample_TransactionService")
public class TransactionService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DataManager dataManager;

    // tag::transactional[]
    @Transactional // <1>
    public void makeDiscountsForAll() {
        List<Order> orders = dataManager.load(Order.class)
                .query("select o from sample_Order o where o.customer is not null")
                .list();
        for (Order order : orders) {
            BigDecimal newTotal = orderService.calculateDiscount(order);
            order.setAmount(newTotal);
            dataManager.save(order);
            Customer customer = customerService.updateCustomerGrade(order.getCustomer());
            dataManager.save(customer);
        }
    }
    // end::transactional[]


    // tag::transaction-template-inject[]
    @Autowired
    @Qualifier("db1TransactionTemplate") // <1>
    private TransactionTemplate db1TransactionTemplate;
    // end::transaction-template-inject[]

    // tag::transactional-additional-ds[]
    @Transactional("db1TransactionManager")
    public void makeChangesInDb1DataStore() {
        // ...
    }
    // end::transactional-additional-ds[]

}