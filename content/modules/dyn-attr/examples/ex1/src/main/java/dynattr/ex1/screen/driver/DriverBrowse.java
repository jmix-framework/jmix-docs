package dynattr.ex1.screen.driver;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Driver;
import io.jmix.core.entity.EntityValues;

@UiController("sample_Driver.browse")
@UiDescriptor("driver-browse.xml")
@LookupComponent("driversTable")
public class DriverBrowse extends StandardLookup<Driver> {
}