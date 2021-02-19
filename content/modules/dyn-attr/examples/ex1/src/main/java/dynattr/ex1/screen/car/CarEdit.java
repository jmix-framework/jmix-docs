package dynattr.ex1.screen.car;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Car;

@UiController("sample_Car.edit")
@UiDescriptor("car-edit.xml")
@EditedEntityContainer("carDc")
public class CarEdit extends StandardEditor<Car> {
}