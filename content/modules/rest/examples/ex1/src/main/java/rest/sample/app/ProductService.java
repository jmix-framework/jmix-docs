package rest.sample.app;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("sample_ProductService")
public class ProductService {

    public ProductInformation getProductInformation(String productId) {
        return new ProductInformation(
                "Apple iPhone",
                productId,
                BigDecimal.valueOf(499.99)
        );
    }
}
