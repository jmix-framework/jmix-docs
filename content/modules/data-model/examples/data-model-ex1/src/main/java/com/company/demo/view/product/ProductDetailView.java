package com.company.demo.view.product;

import com.company.demo.entity.Product;

import com.company.demo.entity.ProductPart;
import com.company.demo.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;

@Route(value = "products/:id", layout = MainView.class)
@ViewController("Product.detail")
@ViewDescriptor("product-detail-view.xml")
@EditedEntityContainer("productDc")
public class ProductDetailView extends StandardDetailView<Product> {

    // tag::data-context-create[]
    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionPropertyContainer<ProductPart> partsDc;

    @Subscribe("createProductPart")
    public void onCreateProductPartClick(final ClickEvent<JmixButton> event) {
        ProductPart part = dataContext.create(ProductPart.class);
        partsDc.getMutableItems().add(part);
    }
    // end::data-context-create[]
}