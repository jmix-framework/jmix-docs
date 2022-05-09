package ui.ex1.screen.component.image;

import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.JmixApp;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Person;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

@UiController("sample_ImageResourceScreen")
@UiDescriptor("image-resource-screen.xml")
public class ImageResourceScreen extends Screen {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ImageResourceScreen.class);
    @Autowired
    private Image image;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Messages messages;
    @Inject
    protected JmixApp app;

    @Subscribe
    public void onInit(InitEvent event) {
        //tag::image-classpath[]
        image.setSource(ClasspathResource.class)
                .setPath("ui/ex1/screen/component/image/jmix-logo.png");
        //end::image-classpath[]

        //tag::image-file-resource[]
        File file = new File("D:\\JMIX\\jmix-logo.png");
        image.setSource(FileResource.class)
                .setFile(file);
        //end::image-file-resource[]


        UUID uuid = UUID.randomUUID();
        Person person = dataManager.load(Person.class)
                .id(uuid)
                .one();
        //tag::image-file-storage-resource[]
        image.setSource(FileStorageResource.class)
                .setFileReference(person.getImage());
        //end::image-file-storage-resource[]

        //tag::image-relative-path[]
        image.setSource(RelativePathResource.class)
                .setPath("images/logo.png");
        //end::image-relative-path[]

        try {
            URLConnection connection = new URL("https://picsum.photos/300").openConnection();
            InputStream inputStream = connection.getInputStream();
            //tag::image-stream[]
            byte[] picture;
            // picture = ...
            //end::image-stream[]
            picture = IOUtils.toByteArray(inputStream);
            //tag::image-stream[]
            image.setSource(StreamResource.class)
                    .setStreamSupplier(() -> new ByteArrayInputStream(picture));
            //end::image-stream[]
        } catch (IOException e) {
            throw new RuntimeException("Error getting image", e);
        }

        //tag::image-theme[]
        image.setSource(ThemeResource.class)
                .setPath("images/jmix-icon-login.svg");
        //end::image-theme[]

        //tag::image-url[]
        try {
            image.setSource(UrlResource.class)
                    .setUrl(new URL("https://www.jmix.io/images/jmix-logo.svg"));
        } catch (MalformedURLException e) {
            log.error("Invalid URL format", e);
        }
        //end::image-url[]

    }


}