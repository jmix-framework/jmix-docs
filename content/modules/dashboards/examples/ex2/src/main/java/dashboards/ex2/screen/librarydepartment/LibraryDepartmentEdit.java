package dashboards.ex2.screen.librarydepartment;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.LibraryDepartment;

@UiController("sample_LibraryDepartment.edit")
@UiDescriptor("library-department-edit.xml")
@EditedEntityContainer("libraryDepartmentDc")
public class LibraryDepartmentEdit extends StandardEditor<LibraryDepartment> {
}