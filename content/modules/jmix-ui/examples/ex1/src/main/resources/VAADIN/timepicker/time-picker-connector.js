ui_ex1_components_javascript_TimePicker = function () {
    var connector = this;
    var element = connector.getElement();
    element.innerHTML = "<input type=\"text\" name=\"timepicker\" class=\"timepicker\"/>";

    var timepicker;

    this.onStateChange = function () { // <1>
        var data = this.getState().data;

        var options = {
            now: data.now,
            twentyFour: data.twentyFour,
            showSeconds: data.showSeconds,
            beforeShow: function () {
                connector.onBeforeShow();
            },
            show: function () {
                connector.onShow();
            }
        };

        timepicker = $('.timepicker').wickedpicker(options);
        // tag::function[]
        connector.showValue = function () {
            alert(timepicker.wickedpicker('time'));
        };
        // end::function[]
    };
};