package com.company.jmixreports.screen.librarydepartment;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.LibraryDepartment;

@UiController("jmxrpr_LibraryDepartment.browse")
@UiDescriptor("library-department-browse.xml")
@LookupComponent("libraryDepartmentsTable")
public class LibraryDepartmentBrowse extends StandardLookup<LibraryDepartment> {
}