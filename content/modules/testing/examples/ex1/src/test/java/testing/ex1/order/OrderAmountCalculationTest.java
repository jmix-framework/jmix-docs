package testing.ex1.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing.ex1.entity.OrderLine;
import testing.ex1.entity.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


// tag::unit-test-total-amount-calculation[]
class OrderAmountCalculationTest {

    private OrderAmountCalculation orderAmountCalculation;
    private static final BigDecimal USD499 = BigDecimal.valueOf(499.0);
    private Product iPad;

    @Test
    void calculateTotalAmount() {

        // given:
        orderAmountCalculation = new OrderAmountCalculation(); //<1>

        // and:
        iPad = new Product(); //<2>
        iPad.setName("Apple iPad");
        iPad.setPrice(USD499);

        // and:
        OrderLine twoIpads = new OrderLine();
        twoIpads.setProduct(iPad);
        twoIpads.setQuantity(2.0);

        // when:
        var totalAmount = orderAmountCalculation.calculateTotalAmount(
                List.of(twoIpads)
        );

        // then:
        assertThat(totalAmount) //<3>
                .isEqualByComparingTo(BigDecimal.valueOf(998.0));
    }
}
// end::unit-test-total-amount-calculation[]
