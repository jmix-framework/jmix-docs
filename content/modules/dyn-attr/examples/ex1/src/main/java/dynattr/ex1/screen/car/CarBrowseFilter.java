package dynattr.ex1.screen.car;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Car;

@UiController("sample_Car.browse-filter")
@UiDescriptor("car-browse-filter.xml")
@LookupComponent("carsTable")
public class CarBrowseFilter extends StandardLookup<Car> {
}