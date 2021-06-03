package ui.ex1.screen.entity.car;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Car;

@UiController("uiex1_Car.browse")
@UiDescriptor("car-browse.xml")
@LookupComponent("carsTable")
public class CarBrowse extends StandardLookup<Car> {
}