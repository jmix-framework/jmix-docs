package ui.ex1.screen.component.twincolumn;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Employee;

@UiController("sample_TwinColumnScreen")
@UiDescriptor("twin-column-screen.xml")
public class TwinColumnScreen extends Screen {
    // tag::option-caption-provider[]
    @Install(to = "twinColumn", subject = "optionCaptionProvider")
    private String twinColumnOptionCaptionProvider(Employee employee) {
        return employee.getName() + ", salary: " + employee.getSalary();
    }
    // end::option-caption-provider[]
}