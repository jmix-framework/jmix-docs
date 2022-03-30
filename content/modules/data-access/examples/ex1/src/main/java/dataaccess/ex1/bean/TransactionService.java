package dataaccess.ex1.bean;

import dataaccess.ex1.entity.Customer;
import dataaccess.ex1.entity.Order;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
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
            Customer customer = dataManager.load(Customer.class)
                    .id(order.getCustomer().getId())
                    .one();
            customer = customerService.updateCustomerGrade(customer);
            dataManager.save(customer);
        }
    }
    // end::transactional[]


}