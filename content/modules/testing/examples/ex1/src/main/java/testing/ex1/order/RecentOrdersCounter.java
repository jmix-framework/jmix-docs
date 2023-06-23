package testing.ex1.order;

import io.jmix.core.TimeSource;
import org.springframework.stereotype.Component;
import testing.ex1.entity.Customer;
import testing.ex1.entity.Order;

// tag::recent-order-counter[]
@Component
public class RecentOrdersCounter {
    private final TimeSource timeSource;

    public RecentOrdersCounter(TimeSource timeSource) {
        this.timeSource = timeSource;
    }

    public long countFromThisYear(Customer customer) {
        return customer.getOrders().stream()
                .filter(this::fromThisYear)
                .count();
    }

    private boolean fromThisYear(Order order) {
        int thisYear = timeSource.now().toLocalDate().getYear();
        return thisYear == order.getDate().getYear();
    }
}
// end::recent-order-counter[]
