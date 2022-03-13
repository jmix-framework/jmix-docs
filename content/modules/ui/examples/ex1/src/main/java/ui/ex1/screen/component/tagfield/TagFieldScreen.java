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
    // tag::tag-comparator[]
    @Autowired
    protected TagField<Employee> tagFieldComparator;
    // end::tag-comparator[]
    // tag::click-listener[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::click-listener[]
    // tag::create-items[]
    @Autowired
    private TagField<Employee> employeesTagField;

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
        tagFieldComparator.setTagComparator((o1, o2) -> {
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

    // tag::simple-tag-field-search-executor[]
    @Install(to = "simpleTagField", subject = "searchExecutor")
    protected List<Employee> simpleTagFieldSearchExecutor(String searchString,
                                                          Map<String, Object> searchParams) {
        return dataManager.load(Employee.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }

    // end::simple-tag-field-search-executor[]
    // tag::tag-field-caption-provider[]
    @Install(to = "tagFieldCaption", subject = "tagCaptionProvider")
    protected String tagFieldCaptionTagCaptionProvider(Employee employee) {
        return employee.getName() + ", salary: " + employee.getSalary();
    }
    // end::tag-field-caption-provider[]
    @Install(to = "tagFieldCaption", subject = "searchExecutor")
    protected List<Employee> tagFieldCaptionSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Employee.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }

    @Install(to = "tagFieldClick", subject = "searchExecutor")
    protected List<Employee> tagFieldClickSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Employee.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }
    // tag::click-listener[]
    @Subscribe("tagFieldClick")
    protected void onTagFieldClickTagClick(TagField.TagClickEvent<Employee> event) {
        screenBuilders.editor(Employee.class, this)
                .editEntity(event.getItem())
                .show();
    }
    // end::click-listener[]
    @Install(to = "tagFieldComparator", subject = "searchExecutor")
    protected List<Employee> tagFieldComparatorSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Employee.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }

    @Install(to = "tagFieldStyle", subject = "searchExecutor")
    protected List<Employee> tagFieldStyleSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Employee.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }
    // tag::style-provider[]
    @Install(to = "tagFieldStyle", subject = "tagStyleProvider")
    protected String tagFieldStyleTagStyleProvider(Employee employee) {
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
