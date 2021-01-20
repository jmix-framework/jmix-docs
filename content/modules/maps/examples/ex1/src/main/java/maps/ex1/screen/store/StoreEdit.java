package maps.ex1.screen.store;

import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Store.edit")
@UiDescriptor("store-edit.xml")
@EditedEntityContainer("storeDc")
public class StoreEdit extends StandardEditor<Store> {

    // tag::image-icon[]
    @Autowired
    private GeometryStyles geometryStyles;

    @Install(to = "map.storeLayer", subject = "styleProvider")
    private GeometryStyle mapStoreLayerStyleProvider(Store store) {
        return geometryStyles.point()
                .withImageIcon("classpath:/ex1/src/main/resources/maps/ex1/jmix_icon.png")
                .setIconSize(33, 33);
    }
    // end::image-icon[]

}