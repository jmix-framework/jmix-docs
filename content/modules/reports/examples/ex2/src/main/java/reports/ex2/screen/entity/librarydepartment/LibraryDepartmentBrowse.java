package reports.ex2.screen.entity.librarydepartment;

import io.jmix.ui.screen.*;
import reports.ex2.entity.LibraryDepartment;

@UiController("jmxrpr_LibraryDepartment.browse")
@UiDescriptor("library-department-browse.xml")
@LookupComponent("libraryDepartmentsTable")
public class LibraryDepartmentBrowse extends StandardLookup<LibraryDepartment> {
}