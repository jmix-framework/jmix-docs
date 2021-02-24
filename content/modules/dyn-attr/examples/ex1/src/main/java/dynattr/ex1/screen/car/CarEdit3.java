package dynattr.ex1.screen.car;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Car;

@UiController("sample_Car.edit3")
@UiDescriptor("car-edit3.xml")
@EditedEntityContainer("carDc")
public class CarEdit3 extends StandardEditor<Car> {
}