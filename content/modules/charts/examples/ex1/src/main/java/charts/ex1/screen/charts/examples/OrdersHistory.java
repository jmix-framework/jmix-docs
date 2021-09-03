package charts.ex1.screen.charts.examples;

import charts.ex1.entity.Order;
import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.ui.component.Timer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// tag::orders-history1[]
@UiController("sample_OrdersHistory")
@UiDescriptor("orders-history.xml")
public class OrdersHistory extends Screen {

    @Autowired
    private Metadata metadata; // <1>
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private CollectionContainer<Order> ordersDc;

    private final Random random = new Random(42);

    // end::orders-history1[]

    // tag::queue[]
    private Queue<Order> itemsQueue = new LinkedList<>();

    // end::queue[]

    // tag::orders-history2[]
    @Subscribe
    private void onInit(InitEvent event) { // <2>
        Order initialValue = metadata.create(Order.class);
        initialValue.setAmount(new BigDecimal(random.nextInt(1000) + 100));
        initialValue.setDate(timeSource.currentTimestamp());

        ordersDc.getMutableItems().add(initialValue); // <3>
    }

    // end::orders-history2[]

    // tag::orders-history3[]
    @Subscribe("updateChartTimer")
    private void onTimerTick(Timer.TimerActionEvent event) { // <4>
        Order orderHistory = metadata.create(Order.class);
        orderHistory.setAmount(new BigDecimal(random.nextInt(1000) + 100));
        orderHistory.setDate(timeSource.currentTimestamp());
        ordersDc.getMutableItems().add(orderHistory);
        // end::orders-history3[]
        // tag::queue-code[]

        itemsQueue.add(orderHistory); //<1>

        if (itemsQueue.size() > 10) { // <2>
            ordersDc.getMutableItems().remove(0);
            System.out.println("in");
        }
        // end::queue-code[]
        // tag::orders-history4[]
        System.out.println("get");
    }
}
// end::orders-history4[]