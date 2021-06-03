package ui.ex1.screen.component.tagfield;

import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.TagField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Employee;

import java.util.List;
import java.util.Map;

@UiController("sample_TagFieldScreen")
@UiDescriptor("tag-field-screen.xml")
public class TagFieldScreen extends Screen {
    // tag::click-listener[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::click-listener[]

    // tag::create-items[]
    // tag::tag-comparator[]
    @Autowired
    private TagField<Employee> employeesTagField;
    // end::tag-comparator[]
    @Autowired
    private Metadata metadata;
    // end::create-items[]

    @Autowired
    private DataManager dataManager;

    @Install(to = "employeesTagField", subject = "searchExecutor")
    private List<Employee> employeesTagFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Employee.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }

    // tag::create-items[]
    // tag::tag-comparator[]

    @Subscribe
    public void onInit(InitEvent event) {
        // end::tag-comparator[]
        employeesTagField.setEnterPressHandler(new TagField.NewTagProvider<Employee>() {
            @Override
            public Employee create(String name) {
                Employee employee = metadata.create(Employee.class);
                employee.setName(name);
                return employee;
            }
        });
        // end::create-items[]

        // tag::tag-comparator[]
        employeesTagField.setTagComparator((o1, o2) -> {
            Double salary1 = o1.getSalary();
            Double salary2 = o2.getSalary();
            if (salary1 == null) {
                return 1;
            } else if (salary2 == null) {
                return -1;
            }
            return salary2.compareTo(salary1);
        });
        // end::tag-comparator[]

        // tag::create-items[]
        // tag::tag-comparator[]
    }
    // end::tag-comparator[]
    // end::create-items[]

    // tag::caption-provider[]
    @Install(to = "employeesTagField", subject = "tagCaptionProvider")
    private String employeesTagFieldTagCaptionProvider(Employee employee) {
        return employee.getName() + ", salary: " + employee.getSalary();
    }
    // end::caption-provider[]

    // tag::click-listener[]
    @Subscribe("employeesTagField")
    public void onEmployeesTagFieldTagClick(TagField.TagClickEvent<Employee> event) {
        screenBuilders.editor(Employee.class, this)
                .editEntity(event.getItem())
                .show();
    }
    // end::click-listener[]

    // tag::style-provider[]
    @Install(to = "employeesTagField", subject = "tagStyleProvider")
    private String employeesTagFieldTagStyleProvider(Employee employee) {
        if (employee != null) {
            switch (employee.getPosition()) {
                case DEV:
                    return "developer";
                case BA:
                    return "business-analyst";
                case PM:
                    return "project-manager";
                case CEO:
                    return "chief-executive-officer";
            }
        }
        return null;
    }
    // end::style-provider[]

}
