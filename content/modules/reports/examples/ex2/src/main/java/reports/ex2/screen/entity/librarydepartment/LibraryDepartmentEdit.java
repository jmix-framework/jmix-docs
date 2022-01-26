package reports.ex2.screen.entity.librarydepartment;

import io.jmix.ui.screen.*;
import reports.ex2.entity.LibraryDepartment;

@UiController("jmxrpr_LibraryDepartment.edit")
@UiDescriptor("library-department-edit.xml")
@EditedEntityContainer("libraryDepartmentDc")
public class LibraryDepartmentEdit extends StandardEditor<LibraryDepartment> {
}