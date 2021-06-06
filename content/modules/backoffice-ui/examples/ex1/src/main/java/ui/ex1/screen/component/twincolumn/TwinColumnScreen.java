package ui.ex1.screen.component.twincolumn;

import io.jmix.core.MetadataTools;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TwinColumn;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Employee;

import java.util.Collection;

@UiController("sample_TwinColumnScreen")
@UiDescriptor("twin-column-screen.xml")
public class TwinColumnScreen extends Screen {
    // tag::option-caption-provider[]
    @Install(to = "twinColumn", subject = "optionCaptionProvider")
    private String twinColumnOptionCaptionProvider(Employee employee) {
        return employee.getName() + ", salary: " + employee.getSalary();
    }
    // end::option-caption-provider[]

    // tag::show-values[]
    @Autowired
    private TwinColumn<Employee> twinColumn;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MetadataTools metadataTools;

    @Subscribe("button")
    protected void onButtonClick(Button.ClickEvent event) {
        StringBuilder sb = new StringBuilder();
        Collection<Employee> value = twinColumn.getValue();
        if (value == null) {
            sb.append("null");
        } else {
            for (Employee employee : value) {
                sb.append(metadataTools.getInstanceName(employee))
                        .append("\n");
            }
        }
        notifications.create()
                .withCaption(sb.toString())
                .show();
    }
    // end::show-values[]
}