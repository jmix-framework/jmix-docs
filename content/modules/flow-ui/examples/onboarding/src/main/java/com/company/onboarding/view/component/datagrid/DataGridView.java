package com.company.onboarding.view.component.datagrid;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.DataGridHelper;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.editor.DataGridEditor;
import io.jmix.flowui.data.grid.ContainerDataGridItems;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;

@Route(value = "DataGridView", layout = MainView.class)
@ViewController("DataGridView")
@ViewDescriptor("data-grid-view.xml")
public class DataGridView extends StandardView {

    // tag::dataGrid[]
    @ViewComponent
    private DataGrid<User> dataGrid;

    // end::dataGrid[]
    // tag::usersDc[]
    @ViewComponent
    private CollectionContainer<User> usersDc;

    // end::usersDc[]
    // tag::usersDtGr[]
    @ViewComponent
    private DataGrid<User> usersDtGr;

    // end::usersDtGr[]
    // tag::uiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::uiComponents[]
    // tag::fileStorage[]
    @Autowired
    private FileStorage fileStorage;

    // end::fileStorage[]
    // tag::editableUserTable[]
    @ViewComponent
    private DataGrid<User> editableUserTable;

    // end::editableUserTable[]
    // tag::dblClickTable[]
    @ViewComponent
    private DataGrid<User> dblClickTable;

    // end::dblClickTable[]
    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        dataGrid.setItems(new ContainerDataGridItems<>(usersDc));
        // end::setItems[]
        // tag::addJoiningDate[]
        usersDtGr.addColumn(new LocalDateRenderer<>
                        (User::getJoiningDate,"dd/MM"))
                .setHeader("Joining date");
        // end::addJoiningDate[]
        // tag::addComponentColumn[]
        Grid.Column<User> pictureColumn = usersDtGr.addComponentColumn(user -> { // <1>
            FileRef fileRef = user.getPicture();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class); // <2>
                image.setWidth("30px");
                image.setHeight("30px");
                image.setClassName("user-picture");

                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource); // <3>

                return image; // <4>
            } else {
                return new Span();
            }
        })
                .setHeader("Picture")
                .setAutoWidth(true)
                .setFlexGrow(0);
        usersDtGr.setColumnPosition(pictureColumn,0);
        // end::addComponentColumn[]
        // tag::DataGridEditor[]
        DataGridEditor<User> editor = editableUserTable.getEditor(); // <1>

        editor.setColumnEditorComponent("timeZoneId", generationContext -> {
            //noinspection unchecked
            JmixComboBox<String> timeZoneField = uiComponents.create(JmixComboBox.class); // <2>
            timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));
            timeZoneField.setValueSource(generationContext.getValueSourceProvider().getValueSource("timeZoneId"));
            timeZoneField.setWidthFull();
            timeZoneField.setClearButtonVisible(true);
            timeZoneField.setRequired(true);
            //noinspection unchecked,rawtypes
            timeZoneField.setStatusChangeHandler(((Consumer) generationContext.getStatusHandler())); // <3>

            return timeZoneField; // <4>
        });
        // end::DataGridEditor[]
        // tag::addItemDoubleClickListener[]
        DataGridEditor<User> tableEditor = dblClickTable.getEditor();
        dblClickTable.addItemDoubleClickListener(e -> {
            tableEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });
        // end::addItemDoubleClickListener[]
        // tag::onInit[]
    }
    // end::onInit[]
}