package dynattr.ex1.screen.car;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Car;

@UiController("sample_Car.edit2")
@UiDescriptor("car-edit2.xml")
@EditedEntityContainer("carDc")
public class CarEdit2 extends StandardEditor<Car> {
}