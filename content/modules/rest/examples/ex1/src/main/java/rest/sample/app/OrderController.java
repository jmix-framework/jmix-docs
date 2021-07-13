package rest.sample.app;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/calculateTotalAmount")
    public ResponseEntity<OrderTotalAmount> calculateTotalAmount(@RequestParam int orderId) {

        BigDecimal totalAmount = orderService.calculateTotalAmount(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CACHE_CONTROL, "max-age=31536000")
                .body(new OrderTotalAmount(totalAmount, orderId));

    }
}