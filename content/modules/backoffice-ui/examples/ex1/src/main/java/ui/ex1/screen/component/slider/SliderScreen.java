package ui.ex1.screen.component.slider;

import io.jmix.core.Metadata;
import io.jmix.ui.component.HasContextHelp;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Product;

import java.math.BigDecimal;

@UiController("sample_SliderScreen")
@UiDescriptor("slider-screen.xml")
public class SliderScreen extends Screen {
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