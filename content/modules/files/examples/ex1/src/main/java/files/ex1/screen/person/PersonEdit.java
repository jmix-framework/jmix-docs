package files.ex1.screen.person;

import files.ex1.entity.Person;
import io.jmix.ui.component.Button;
import io.jmix.ui.download.DownloadFormat;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Person.edit")
@UiDescriptor("person-edit.xml")
@EditedEntityContainer("personDc")
public class PersonEdit extends StandardEditor<Person> {

    // tag::file[]
    @Autowired
    private InstanceContainer<Person> personDc;

    @Autowired
    private Downloader downloader; // <1>

    @Subscribe("downloadBtn")
    public void onDownloadBtnClick(Button.ClickEvent event) {
        byte[] photo = personDc.getItem().getPhoto();
        downloader.download(
                photo,
                personDc.getItem().getName() + "-photo", // <2>
                DownloadFormat.JPG // <3>
        );
    }
    // end::file[]
}