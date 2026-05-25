package rest.sample.app;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service("sample_OrderService")
public class OrderService {

    public BigDecimal calculateTotalAmount(int orderId) {
        return new BigDecimal(new Random().nextInt(10000));
    }
}