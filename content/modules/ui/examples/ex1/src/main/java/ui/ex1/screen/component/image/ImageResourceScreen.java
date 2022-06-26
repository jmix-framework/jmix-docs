package ui.ex1.screen.component.image;

import io.jmix.core.DataManager;
import io.jmix.ui.JmixApp;
import io.jmix.ui.Notifications;
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

@UiController("sample_ImageResourceScreen")
@UiDescriptor("image-resource-screen.xml")
public class ImageResourceScreen extends Screen {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ImageResourceScreen.class);
    @Autowired
    private Image image;
    @Autowired
    private DataManager dataManager;
    @Inject
    protected JmixApp app;
    @Autowired
    private Notifications notifications;


    @Subscribe("classpathBtn")
    public void onClasspathBtnClick(Button.ClickEvent event) {
        //tag::image-classpath[]
        image.setSource(ClasspathResource.class)
                .setPath("ui/ex1/screen/component/image/jmix-logo.png");
        //end::image-classpath[]
    }

    @Subscribe("fileBtn")
    public void onFileBtnClick(Button.ClickEvent event) {

        File file = new File("D:\\JMIX\\jmix-logo.png");
        if (file.exists()) {
            //tag::image-file-resource[]
            image.setSource(FileResource.class)
                    .setFile(file);
            //end::image-file-resource[]
        } else {
            notifications.create()
                    .withCaption("Upload the D:\\JMIX\\jmix-logo.png file")
                    .show();
        }
    }

    @Subscribe("fileStorageBtn")
    public void onFileStorageBtnClick(Button.ClickEvent event) {
        try {
            Person person = dataManager.load(Person.class)
                    .all().one();
            if (person.getImage() != null)
                //tag::image-file-storage-resource[]
                image.setSource(FileStorageResource.class)
                        .setFileReference(person.getImage());
                //end::image-file-storage-resource[]
            else notifications.create()
                    .withCaption("Upload the image for the " + person.getName() + " person")
                    .show();
        } catch (IllegalStateException e) {
            notifications.create()
                    .withCaption("Create the person first!")
                    .show();
        }
    }

    @Subscribe("relativePathBtn")
    public void onRelativePathBtnClick(Button.ClickEvent event) {
        //tag::image-relative-path[]
        image.setSource(RelativePathResource.class)
                .setPath("images/jmix-icon-login.svg");
        //end::image-relative-path[]
    }

    @Subscribe("streamBtn")
    public void onStreamBtnClick(Button.ClickEvent event) {
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
    }

    @Subscribe("themeBtn")
    public void onThemeBtnClick(Button.ClickEvent event) {
        //tag::image-theme[]
        image.setSource(ThemeResource.class)
                .setPath("images/jmix-icon-login.svg");
        //end::image-theme[]
    }

    @Subscribe("urlBtn")
    public void onUrlBtnClick(Button.ClickEvent event) {
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