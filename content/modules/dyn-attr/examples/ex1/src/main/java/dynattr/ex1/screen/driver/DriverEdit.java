package dynattr.ex1.screen.driver;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Driver;
import io.jmix.core.entity.EntityValues;

@UiController("sample_Driver.edit")
@UiDescriptor("driver-edit.xml")
@EditedEntityContainer("driverDc")
public class DriverEdit extends StandardEditor<Driver> {
}