package dynattr.ex1.screen.car;

import io.jmix.core.DataManager;
import io.jmix.core.entity.EntityValues;
import io.jmix.dynattr.DynAttrQueryHints;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@UiController("sample_Car.browse")
@UiDescriptor("car-browse.xml")
@LookupComponent("carsTable")
public class CarBrowse extends StandardLookup<Car> {
    @Autowired
    private GroupTable<Car> carsTable;

    //tag::get-value[]
    @Autowired
    private DataManager dataManager;

    public void increaseLoadCapacity(Car car, int value) {
        Car carLoad = dataManager.load(Car.class)
                .id(car.getId())
                .hint(DynAttrQueryHints.LOAD_DYN_ATTR, true)
                .one();
        Double capacity = EntityValues.getValue(car, "+TruckLoadcapacity");
        EntityValues.setValue(car, "+TruckLoadcapacity", capacity + value);
        dataManager.save(car);
    }
    //end::get-value[]

    @Subscribe("changeBtn")
    public void onChangeBtnClick(Button.ClickEvent event) {
        increaseLoadCapacity(carsTable.getSingleSelected(), 10);
    }
}