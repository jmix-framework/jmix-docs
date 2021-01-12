package datamodel.ex1.screen.product;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Product;

@UiController("sample_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {
}