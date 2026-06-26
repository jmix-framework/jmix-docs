package com.company.demo.bean;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import com.company.demo.entity.OrderLine;
import com.company.demo.entity.Product;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.EntityUpdateDispatcher;
import io.jmix.core.FetchPlan;
import io.jmix.core.SaveContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class OrderUpdateServiceTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    EntityUpdateDispatcher entityUpdateDispatcher;

    @Autowired
    DataSource dataSource;

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from ORDER_LINE");
        jdbc.execute("delete from ORDER_");
        jdbc.execute("delete from PRODUCT");
        jdbc.execute("delete from CUSTOMER");
    }

    @Test
    void testSaveDelegatesToService() {
        Customer customer = dataManager.create(Customer.class);
        customer.setName("Bob");
        customer = dataManager.save(customer);

        Product product = dataManager.create(Product.class);
        product.setName("Widget");
        product.setPrice(BigDecimal.valueOf(100));
        product = dataManager.save(product);

        Order order = dataManager.create(Order.class);
        order.setCustomer(customer);
        order.setDate(LocalDate.now());

        OrderLine line = dataManager.create(OrderLine.class);
        line.setOrder(order);
        line.setProduct(product);
        line.setQuantity(3.0);
        order.setLines(java.util.List.of(line));

        // generic mechanisms save entities through the dispatcher
        entityUpdateDispatcher.save(dataManager, new SaveContext().saving(order, line));

        // the service computed the total amount: 3 * 100
        Order savedOrder = dataManager.load(Order.class).id(order.getId()).one();
        assertEquals(0, savedOrder.getAmount().compareTo(BigDecimal.valueOf(300)));

        // the service incremented the customer's orders count
        Customer savedCustomer = dataManager.load(Customer.class).id(customer.getId()).one();
        assertEquals(1, savedCustomer.getOrdersCount());
    }

    @Test
    void testRemoveDelegatesToService() {
        Customer customer = dataManager.create(Customer.class);
        customer.setName("Bob");
        customer = dataManager.save(customer);

        Order order = dataManager.create(Order.class);
        order.setCustomer(customer);
        order.setDate(LocalDate.now());

        entityUpdateDispatcher.save(dataManager, new SaveContext().saving(order));

        Customer afterSave = dataManager.load(Customer.class).id(customer.getId()).one();
        assertEquals(1, afterSave.getOrdersCount());

        Order toRemove = dataManager.load(Order.class).id(order.getId())
                .fetchPlan(fp -> fp.addFetchPlan(FetchPlan.BASE).add("customer"))
                .one();
        entityUpdateDispatcher.remove(dataManager, java.util.List.of(toRemove));

        // the order is removed and the customer's orders count is decremented
        assertFalse(dataManager.load(Order.class).id(order.getId()).optional().isPresent());
        Customer afterRemove = dataManager.load(Customer.class).id(customer.getId()).one();
        assertEquals(0, afterRemove.getOrdersCount());
    }
}
