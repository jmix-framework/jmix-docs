package ui.ex1.screen.data;

import io.jmix.core.Sort;
import io.jmix.core.querycondition.Condition;
import org.springframework.stereotype.Component;
import ui.ex1.entity.Customer;

import java.util.ArrayList;
import java.util.List;

@Component("uiex1_CustomerService")
public class CustomerService {

    public List<Customer> loadCustomers(Condition loadContext, Sort sort, int firstResult, int maxResults) {
        return new ArrayList<>();
    }
}
