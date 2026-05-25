package rest.sample.app;

import java.math.BigDecimal;

public class ProductInformation {
    private final String name;
    private final String productId;
    private final BigDecimal price;

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductInformation(String name, String productId, BigDecimal price) {
        this.name = name;
        this.productId = productId;
        this.price = price;
    }
}
