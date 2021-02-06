package dataaccess.ex1.bean;

import dataaccess.ex1.entity.Customer;
import dataaccess.ex1.entity.Order;
import dataaccess.ex1.entity.OrderLine;
import dataaccess.ex1.entity.Product;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.Id;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    @Autowired
    OrderService orderService;

    @BeforeEach
    void setUp() {
        Customer customer1 = dataManager.create(Customer.class);
        customer1.setName("Alice");
        customer1.setEmail("alice@company.com");

        Product product1 = dataManager.create(Product.class);
        product1.setName("MacBook Pro");
        product1.setPrice(BigDecimal.valueOf(2500));

        Order order1 = dataManager.create(Order.class);
        order1.setCustomer(customer1);
        order1.setDate(LocalDate.now());
        order1.setAmount(BigDecimal.valueOf(5000));

        OrderLine orderLine1 = dataManager.create(OrderLine.class);
        orderLine1.setOrder(order1);
        orderLine1.setProduct(product1);
        orderLine1.setQuantity(2.0);

        dataManager.save(customer1, order1, orderLine1, product1);
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from ORDER_LINE");
        jdbc.execute("delete from ORDER");
        jdbc.execute("delete from PRODUCT");
        jdbc.execute("delete from CUSTOMER_GRADE_CHANGE");
        jdbc.execute("delete from CUSTOMER");
    }

    @Test
    void testQuery() {
        List<Order> orders = orderService.loadOrdersByProduct("MacBook Pro");
        assertEquals(1, orders.size());
    }

    @Test
    void testLoadValues() {
        String str = orderService.getCustomerPurchases(LocalDate.now());
        assertEquals("Alice : 5000.00", str);
    }

    @Test
    void testLoadValue() {
        BigDecimal total = orderService.getTotal(LocalDate.now());
        assertEquals(total.compareTo(BigDecimal.valueOf(5000.00)), 0);
    }

    @Test
    void testLoadByCondition() {
        List<Order> orders = orderService.loadByCondition();
        assertEquals(1, orders.size());
    }

    @Test
    void testLoadSorted() {
        List<Order> orders = orderService.loadSorted();
        assertEquals(1, orders.size());
    }

    @Test
    void testCreateOrderWithCustomer() {
        Order order = orderService.createOrderWithCustomer();
        assertNotNull(order.getCustomer());
    }

    @Test
    void testRemoveOrderWithCustomer() {
        Order order = orderService.createOrderWithCustomer();
        assertNotNull(order.getCustomer());

        orderService.removeOrderWithCustomer(order);

        assertFalse(dataManager.load(Order.class).id(order.getId()).optional().isPresent());
        assertFalse(dataManager.load(Customer.class).id(order.getCustomer().getId()).optional().isPresent());
    }

    @Test
    void testHardDelete() {
        Product product1 = dataManager.create(Product.class);
        product1.setName("MacBook Pro");
        dataManager.save(product1);

        orderService.hardDelete(product1);

        assertFalse(dataManager.load(Id.of(product1)).softDeletion(false).optional().isPresent());
    }
}
