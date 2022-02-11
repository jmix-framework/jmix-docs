package dashboards.ex2.screen.librarydepartment;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.LibraryDepartment;

@UiController("sample_LibraryDepartment.browse")
@UiDescriptor("library-department-browse.xml")
@LookupComponent("libraryDepartmentsTable")
public class LibraryDepartmentBrowse extends StandardLookup<LibraryDepartment> {
}