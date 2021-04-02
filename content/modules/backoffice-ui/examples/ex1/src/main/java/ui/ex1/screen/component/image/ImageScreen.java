package ui.ex1.screen.component.image;

import io.jmix.core.FileRef;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.UrlResource;
import io.jmix.ui.component.data.value.ContainerValueSource;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Person;

import java.net.MalformedURLException;
import java.net.URL;

@UiController("sample_ImageScreen")
@UiDescriptor("image-screen.xml")
public class ImageScreen extends Screen {
    // tag::value-source[]
    @Autowired
    private GroupTable<Person> personsTable;
    @Autowired
    private UiComponents uiComponents;

    // end::value-source[]

    // tag::listener[]
    @Autowired
    private Notifications notifications;
    // end::listener[]

    // tag::set-source[]
    // tag::listener[]
    @Autowired
    private Image<FileRef> programmaticImage;

    // end::set-source[]
    // end::listener[]

    // tag::value-source[]
    // tag::set-source[]
    // tag::listener[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::listener[]
        // end::set-source[]
        personsTable.addGeneratedColumn("image", entity -> {
            Image<FileRef> image = uiComponents.create(Image.NAME);
            image.setValueSource(new ContainerValueSource<>(personsTable.getInstanceContainer(entity), "image"));
            image.setHeight("100px");
            image.setScaleMode(Image.ScaleMode.CONTAIN);
            return image;
        });
        // end::value-source[]

        // tag::set-source[]
        String address = "https://www.cuba-platform.com/sites/all/themes/cuba_adaptive/img/upper-header-logo.png";
        URL url = null;
        try {
            url = new URL(address);
            programmaticImage.setSource(UrlResource.class).setUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // end::set-source[]

        // tag::listener[]
        programmaticImage.addClickListener(clickEvent -> {
            if (clickEvent.isDoubleClick())
                notifications.create()
                        .withCaption("Double clicked")
                        .show();
        });
        // end::listener[]

        // tag::listener[]
        // tag::value-source[]
        // tag::set-source[]
    }
    // end::value-source[]
    // end::set-source[]
    // end::listener[]
}