package modularity.sample.ext.screen.department;

import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import modularity.sample.base.screen.department.DepartmentBrowse;

// tag::extend[]
@UiController("base_Department.browse")
@UiDescriptor("ext-department-browse.xml")
public class ExtDepartmentBrowse extends DepartmentBrowse {
// end::extend[]
}