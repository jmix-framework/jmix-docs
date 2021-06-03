package ui.ex1.screen.entity.car;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Car;

@UiController("uiex1_Car.edit")
@UiDescriptor("car-edit.xml")
@EditedEntityContainer("carDc")
public class CarEdit extends StandardEditor<Car> {
}