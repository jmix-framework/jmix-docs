package charts.ex1.screen.charts.data;

import charts.ex1.entity.TransportCount;
import io.jmix.charts.component.SerialChart;
import io.jmix.ui.data.impl.ContainerDataProvider;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_ContainerDataProviderScreen")
@UiDescriptor("container-data-provider-screen.xml")
public class ContainerDataProviderScreen extends Screen {
    // tag::container-data-provider[]
    @Autowired
    private CollectionContainer<TransportCount> transportCountsDc;

    @Autowired
    private SerialChart stackedArea;

    @Subscribe
    private void onInit(InitEvent event) {
        stackedArea.setDataProvider(new ContainerDataProvider(transportCountsDc));
        stackedArea.setCategoryField("year");
    }
    // end::container-data-provider[]
}