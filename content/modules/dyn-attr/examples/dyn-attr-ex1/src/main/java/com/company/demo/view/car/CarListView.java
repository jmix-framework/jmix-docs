package com.company.demo.view.car;

import com.company.demo.entity.Car;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.entity.EntityValues;
import io.jmix.dynattr.DynAttrQueryHints;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route(value = "cars", layout = MainView.class)
@ViewController("Car.list")
@ViewDescriptor("car-list-view.xml")
@LookupComponent("carsDataGrid")
@DialogMode(width = "64em")
public class CarListView extends StandardListView<Car> {

    //tag::get-value[]
    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private DataGrid<Car> carsDataGrid;

    public void increaseLoadCapacity(Car car, int value) {
        Car carLoad = dataManager.load(Car.class)
                .id(car.getId())
                .hint(DynAttrQueryHints.LOAD_DYN_ATTR, true)
                .one();
        Integer capacity = EntityValues.getValue(car, "+truckLoadCapacity");
        EntityValues.setValue(car, "+truckLoadCapacity", capacity + value);
        dataManager.save(car);
    }
    //end::get-value[]
    @Subscribe(id = "changeBtn", subject = "clickListener")
    public void onChangeBtnClick(final ClickEvent<JmixButton> event) {
        increaseLoadCapacity(Objects.requireNonNull(carsDataGrid.getSingleSelectedItem()), 10);
    }
}