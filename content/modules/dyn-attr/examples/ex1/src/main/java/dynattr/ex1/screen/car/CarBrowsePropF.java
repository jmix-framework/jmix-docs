package dynattr.ex1.screen.car;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Car;

@UiController("sample_Car.browse-propf")
@UiDescriptor("car-browse-propf.xml")
@LookupComponent("carsTable")
public class CarBrowsePropF extends StandardLookup<Car> {
}