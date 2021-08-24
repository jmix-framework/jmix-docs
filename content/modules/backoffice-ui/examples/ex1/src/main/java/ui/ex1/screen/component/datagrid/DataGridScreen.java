package ui.ex1.screen.component.datagrid;

import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.component.data.ValueSource;
import io.jmix.ui.component.data.datagrid.ContainerDataGridItems;
import io.jmix.ui.icon.Icons;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import io.jmix.ui.theme.ThemeClassNames;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.*;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@UiController("dataGrid-screen")
@UiDescriptor("data-grid-screen.xml")
public class DataGridScreen extends Screen {
    // tag::inject-customersDataGrid[]
    @Autowired
    private DataGrid<Customer> customersDataGrid;

    // end::inject-customersDataGrid[]
    // tag::inject-customersDc[]
    @Autowired
    private CollectionContainer<Customer> customersDc;

    // end::inject-customersDc[]

    // tag::init-footer-header-var[]
    @Autowired
    private DataGrid<Customer> dataGrid;
    @Autowired
    private CollectionLoader<Customer> customersDl;

    private int silverCount = 0;
    private int goldCount = 0;
    private int platinumCount = 0;
    private int diamondCount = 0;

    // end::init-footer-header-var[]
    // tag::inject-notifications[]
    @Autowired
    private Notifications notifications;

    // end::inject-notifications[]
    @Autowired
    private UiComponents uiComponents;
    // tag::inject-gridClick[]
    @Autowired
    private DataGrid<Customer> gridClick;

    // end::inject-gridClick[]
    // tag::inject-gridButtonRenderer[]
    @Autowired
    private DataGrid<Customer> gridButtonRenderer;

    // end::inject-gridButtonRenderer[]
    // tag::inject-imageGrid[]
    @Autowired
    private DataGrid<Country> imageGrid;

    // end::inject-imageGrid[]
    // tag::inject-detailsGrid[]
    @Autowired
    private DataGrid<Customer> detailsGrid;

    // end::inject-detailsGrid[]
    // tag::inject-citiesDc[]
    @Autowired
    private CollectionContainer<City> citiesDc;

    // end::inject-citiesDc[]
    @Autowired
    private DataGrid<BudgetItem> progressGrid;

    // tag::init-start[]
    @Autowired
    private DataGrid<Event> clickGrid;
    @Subscribe
    public void onInit(InitEvent event) {
        // end::init-start[]
        // tag::programmatic-binding[]
        customersDataGrid.setItems(new ContainerDataGridItems<>(customersDc));
        // end::programmatic-binding[]
        // tag::init-footer-header[]
        customersDl.load();
        initFooter();
        initHeader();
        // end::init-footer-header[]
        // tag::button-renderer[]
        DataGrid.ButtonRenderer<Customer> gridButtonRendererFirstNameRenderer =
                getApplicationContext().getBean(DataGrid.ButtonRenderer.class);
        gridButtonRendererFirstNameRenderer.setRendererClickListener(
                clickableRendererClickEvent ->
                notifications.create()
                        .withCaption("ButtonRenderer")
                        .withDescription("Column id: " +
                                clickableRendererClickEvent.getColumnId())
                        .show());
        gridButtonRenderer.getColumn("firstName")
                .setRenderer(gridButtonRendererFirstNameRenderer);
        // end::button-renderer[]
        // tag::clickable-text-renderer[]
        DataGrid.ClickableTextRenderer<Customer> gridClickFirstNameRenderer =
                getApplicationContext().getBean(DataGrid.ClickableTextRenderer.class);
        gridClickFirstNameRenderer.setRendererClickListener(clickEvent ->
                notifications.create()
                        .withDescription("The full name: " +
                                clickEvent.getItem().getFirstName() +
                                " " + clickEvent.getItem().getLastName())
                        .show());
        gridClick.getColumn("firstName").setRenderer(gridClickFirstNameRenderer);
        // end::clickable-text-renderer[]

        // tag::image-renderer[]
        DataGrid.ImageRenderer<Person> imageGridFlagRenderer =
                getApplicationContext().getBean(DataGrid.ImageRenderer.class);
        imageGridFlagRenderer.setRendererClickListener(clickableTextRendererClickEvent -> {
        });
        imageGrid.getColumn("flag").setRenderer(imageGridFlagRenderer);
        // end::image-renderer[]
        // tag::set-style-name[]
        progressGrid.setStyleName(ThemeClassNames.DATAGRID_NO_STRIPES);
        // end::set-style-name[]
        // tag::init-end[]
    }
    // end::init-end[]
    // tag::init-footer[]

    private void initFooter() {
        DataGrid.FooterRow footerRow = dataGrid.prependFooterRow();
        DataGrid.FooterCell footerCell = footerRow.join("firstName","lastName");
        footerCell.setHtml("<strong>Total customers count: " +
                customersDc.getItems().size() + "</strong>");
        calculateMemberCount();
        footerRow.getCell("age").setHtml("<strong>Average age: " + getAverage("age") +
                "</strong>");
        footerRow.getCell("level").setHtml("<strong>Silver Members: " + silverCount +
                "<br>" + "Gold Members: " + goldCount + "<br>" +
                "Platinum Members: " + platinumCount + "<br>" +
                "Diamond Members: " + diamondCount + "<br>" +
                "</strong>");
        footerRow.getCell("rewardPoints").setHtml("<strong>Average reward points: " +
                getAverage("rewardPoints") + "</strong>");
    }
    // end::init-footer[]

    // tag::init-header[]

    private void initHeader() {
        DataGrid.HeaderRow headerRow = dataGrid.prependHeaderRow();
        DataGrid.HeaderCell headerCell = headerRow.join("firstName", "lastName");
        headerCell.setText("Full name");
        headerCell.setStyleName("center-bold");
        headerCell = headerRow.join("level", "rewardPoints");
        headerCell.setText("Account information");
        headerCell.setStyleName("center-bold");
    }
    // end::init-header[]

    // tag::get-average[]

    private int getAverage(String propertyId) {
        double average = 0.0;
        Collection<Customer> items = customersDc.getItems();
        for (Customer customer : items) {
            Double value = propertyId.equals("rewardPoints") ?
                    customer.getRewardPoints().doubleValue() :
                    customer.getAge().doubleValue();
            average += value != null ? value : 0.0;
        }
        return (int) (average / items.size());
    }
    // end::get-average[]

    // tag::calculate-member-count[]

    private void calculateMemberCount() {
        Collection<Customer> items = customersDc.getItems();
        for (Customer customer : items) {
            switch (customer.getLevel()) {
                case SILVER:
                    silverCount++;
                    break;
                case GOLD:
                    goldCount++;
                    break;
                case PLATINUM:
                    platinumCount++;
                    break;
                case DIAMOND:
                    diamondCount++;
                    break;
            }
        }
    }
    // end::calculate-member-count[]
    @Install(to = "gridCheckBoxRenderer.isEmail", subject = "columnGenerator")
    private Boolean gridCheckBoxRendererIsEmailColumnGenerator(DataGrid.ColumnGeneratorEvent<Customer> columnGeneratorEvent) {
        return columnGeneratorEvent.getItem().getEmail() != null;
    }

    // tag::component-renderer[]
    @Install(to = "gridComponent.age", subject = "columnGenerator")
    private Component gridComponentAgeColumnGenerator(DataGrid.ColumnGeneratorEvent<Customer> columnGeneratorEvent) {
        Slider<Integer> slider = uiComponents.create(Slider.NAME);
        slider.setValue(columnGeneratorEvent.getItem().getAge());
        slider.setEditable(false);
        slider.setWidth("150px");
        return slider;
    }
    // end::component-renderer[]

    // tag::icon-renderer[]
    @Install(to = "iconGrid.hasEmail", subject = "columnGenerator")
    private Icons.Icon iconGridHasEmailColumnGenerator(DataGrid.ColumnGeneratorEvent<Customer> columnGeneratorEvent) {
        return columnGeneratorEvent.getItem().getEmail() != null ?
                JmixIcon.OK : JmixIcon.EXCLAMATION_TRIANGLE;
    }
    // end::icon-renderer[]
    // tag::column-generator[]
    @Install(to = "grid.fullName", subject = "columnGenerator")
    private String gridFullNameColumnGenerator(DataGrid.ColumnGeneratorEvent<Customer> columnGeneratorEvent) {
        return columnGeneratorEvent.getItem().getFirstName() + " " + columnGeneratorEvent.getItem().getLastName();
    }
    // end::column-generator[]
   /* @Install(to = "grid.fullName", subject = "columnGenerator")
    private Component gridFullNameColumnGenerator(DataGrid.ColumnGeneratorEvent<Customer> columnGeneratorEvent) {
        Label<String> label = uiComponents.create(Label.TYPE_STRING);
        label.setValue(columnGeneratorEvent.getItem().getFirstName() + " " + columnGeneratorEvent.getItem().getLastName());
        label.setAlignment(Component.Alignment.MIDDLE_LEFT);
        return label;
    }*/

    // tag::html-renderer[]
    @Install(to = "htmlGrid.endDate", subject = "columnGenerator")
    private String htmlGridEndDateColumnGenerator(DataGrid.ColumnGeneratorEvent<Event> columnGeneratorEvent) {
        return columnGeneratorEvent.getItem().getEndDate().before(new Date())
                ? "<font color='red'>" +
                columnGeneratorEvent.getItem().getEndDate() + "</font>"
                : "<font color='green'>" +
                columnGeneratorEvent.getItem().getEndDate() + "</font>";
    }
    // end::html-renderer[]

    @Install(to = "progressGrid.percent", subject = "columnGenerator")
    private Double progressGridPercentColumnGenerator(DataGrid.ColumnGeneratorEvent<BudgetItem> columnGeneratorEvent) {
        return (double)columnGeneratorEvent.getItem().getPercent()/100;
    }

    // tag::details-generator[]
    @Install(to = "detailsGrid", subject = "detailsGenerator")
    private Component detailsGridDetailsGenerator(Customer customer) {
        VBoxLayout mainLayout = uiComponents.create(VBoxLayout.class);
        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);

        HBoxLayout headerBox = uiComponents.create(HBoxLayout.class);
        headerBox.setWidth("100%");

        Label<String> infoLabel = uiComponents.create(Label.TYPE_STRING);
        infoLabel.setHtmlEnabled(true);
        infoLabel.setStyleName("h1");
        infoLabel.setValue("Customer info:");

        Component closeButton = createCloseButton(customer);
        headerBox.add(infoLabel);
        headerBox.add(closeButton);
        headerBox.expand(infoLabel);

        Component content = getContent(customer);

        mainLayout.add(headerBox);
        mainLayout.add(content);
        mainLayout.expand(content);

        return mainLayout;
    }

    // end::details-generator[]
    // tag::create-close-button[]
    protected Component createCloseButton(Customer entity) {
        Button closeButton = uiComponents.create(Button.class);
        closeButton.setIcon("font-icon:TIMES");
        BaseAction closeAction = new BaseAction("closeAction")
                .withHandler(actionPerformedEvent ->
                        detailsGrid.setDetailsVisible(entity, false))
                .withCaption("");
        closeButton.setAction(closeAction);
        return closeButton;
    }

    // end::create-close-button[]

    // tag::get-content[]
    protected Component getContent(Customer entity) {
        Label<String> content = uiComponents.create(Label.TYPE_STRING);
        content.setHtmlEnabled(true);
        content.setId("contentLabel");

        StringBuilder sb = new StringBuilder();
        sb.append("<b>Full name</b><br>")
                .append(entity.getFirstName() + " " + entity.getLastName() + "<br><br>")
                .append("<b>Country</b><br>")
                .append(entity.getCity().getCountry().getName()+ "<br><br>")
                .append("<b>City</b><br>")
                .append(entity.getCity().getName());

        content.setValue(sb.toString());

        return content;
    }

    // end::get-content[]

    // tag::item-click-event[]
    @Subscribe("detailsGrid")
    public void onDetailsGridItemClick(DataGrid.ItemClickEvent<Customer> event) {
        detailsGrid.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent ->
                        detailsGrid.setDetailsVisible(detailsGrid.getSingleSelected(), true)));
    }

    // end::item-click-event[]

    // tag::edit-field-generator[]
    @Install(to = "editingGrid.city", subject = "editFieldGenerator")
    private Field<City> editingGridCityEditFieldGenerator(
            DataGrid.EditorFieldGenerationContext<Customer> editorFieldGenerationContext) {
        ComboBox<City> comboBox = uiComponents.create(ComboBox.NAME);
        comboBox.setValueSource((ValueSource<City>) editorFieldGenerationContext
                .getValueSourceProvider().getValueSource("city"));
        comboBox.setOptionsList(citiesDc.getItems());
        return comboBox;
    }
    // end::edit-field-generator[]
    // tag::editor-open-event[]
    @Subscribe("editEventsGrid")
    public void onEditEventsGridEditorOpen(DataGrid.EditorOpenEvent<Event> event) {
        Map<String, Field> fields = event.getFields();
        Field field1 = fields.get("startDate");
        Field field2 = fields.get("endDate");
        field1.addValueChangeListener(valueChangeEvent ->
                field2.setValue(DateUtils.addDays((Date) field1.getValue(), 1)));
        field2.addValueChangeListener(valueChangeEvent ->
                field1.setValue(DateUtils.addDays((Date) field2.getValue(), -1)));
    }
    // end::editor-open-event[]

    // tag::column-collapsing-change-event[]
    @Subscribe("collapseGrid")
    public void onCollapseGridColumnCollapsingChange(DataGrid.ColumnCollapsingChangeEvent event) {
        notifications.create()
                .withCaption((event.isCollapsed() ? "Collapsed: " : "Expanded: ") +
                        event.getColumn().getCaption())
                .show();
    }
    // end::column-collapsing-change-event[]
    // tag::column-resize-event[]
    @Subscribe("resizedEventGrid")
    public void onResizedEventGridColumnResize(DataGrid.ColumnResizeEvent event) {
        notifications.create()
                .withCaption("The " + event.getColumn().getCaption() + " column was resized")
                .show();
    }
    // end::column-resize-event[]
    // tag::context-click-event[]
    @Subscribe("contextGrid")
    public void onContextGridContextClick(DataGrid.ContextClickEvent event) {
        notifications.create()
                .withCaption("Clicked " + event.getButton().name())
                .show();
    }
    // end::context-click-event[]
    // tag::item-click-event2[]
    @Subscribe("clickGrid")
    public void onClickGridItemClick(DataGrid.ItemClickEvent<Event> event) {
        clickGrid.edit(event.getItem());
    }
    // end::item-click-event2[]
    // tag::sort-event[]
    @Subscribe("sortGrid")
    public void onSortGridSort(DataGrid.SortEvent event) {
        notifications.create()
                .withCaption("The sort order was changed")
                .show();
    }
    // end::sort-event[]
    // tag::row-description-provider[]
    @Install(to = "rowDescGrid", subject = "rowDescriptionProvider")
    private String rowDescGridRowDescriptionProvider(Customer customer) {
        switch (customer.getLevel()) {
            case SILVER:
                return "The customer gets special offers from sellers and birthday congratulations";
            case GOLD:
                return "The customer gets 2 coupons with a rating of $ 1";
            case PLATINUM:
                return "The customer gets a coupons with a par value of $ 3";
            case DIAMOND:
                return "The customer gets a coupon with a par value of $ 5";
        }
        return null;
    }
    // end::row-description-provider[]
    // tag::row-style-provider[]
    @Install(to = "styledGrid", subject = "rowStyleProvider")
    private String styledGridRowStyleProvider(Customer customer) {
        switch (customer.getLevel()) {
            case SILVER:
                return "level-silver";
            case GOLD:
                return "level-gold";
            case PLATINUM:
                return "level-platinum";
            case DIAMOND:
                return "level-diamond";
        }
        return null;
    }
    // end::row-style-provider[]
}