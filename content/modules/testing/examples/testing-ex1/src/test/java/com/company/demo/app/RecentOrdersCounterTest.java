package com.company.demo.app;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import io.jmix.core.TimeSource;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// tag::recent-order-counter-test[]
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecentOrdersCounterTest {

    TimeSource timeSourceMock = mock(TimeSource.class); // <1>

    LocalDateTime MAR_01_2020 = LocalDate.of(2020, 3, 1).atStartOfDay();

    @Test
    void given_itIs2020_and_customerWithOneOrderIn2020_when_countFromThisYear_then_resultIs1() {

        // given:
        when(timeSourceMock.now())
                .thenReturn(ZonedDateTime.of(MAR_01_2020, ZoneId.systemDefault()));  // <2>

        // and:
        RecentOrdersCounter counter = new RecentOrdersCounter(timeSourceMock); // <3>

        // and:
        Customer customer = new Customer();
        Order orderFrom2020 = orderWithDate(LocalDate.of(2020, 2, 5));
        Order orderFrom2019 = orderWithDate(LocalDate.of(2019, 5, 1));
        customer.setOrders(List.of(orderFrom2020, orderFrom2019));

        // when:
        long recentOrdersCount = counter.countFromThisYear(customer);

        // then:
        assertThat(recentOrdersCount)
                .isEqualTo(1);
    }
    // end::recent-order-counter-test[]

    private static Order orderWithDate(LocalDate date) {
        Order order = new Order();
        order.setDate(date);
        return order;
    }
}
