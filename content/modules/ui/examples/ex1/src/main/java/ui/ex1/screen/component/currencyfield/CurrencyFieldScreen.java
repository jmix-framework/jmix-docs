package ui.ex1.screen.component.currencyfield;

import io.jmix.core.Metadata;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Product;

@UiController("sample_CurrencyFieldScreen")
@UiDescriptor("currency-field-screen.xml")
public class CurrencyFieldScreen extends Screen {
    @Autowired
    protected InstanceContainer<Product> productDc;

    @Autowired
    protected Metadata metadata;

    @Subscribe
    public void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Product product = metadata.create(Product.class);
        productDc.setItem(product);
    }
}