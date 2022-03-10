ui_ex1_components_javascript_RichTextEditor = function () {
    var connector = this;
    var element = connector.getElement();
    element.innerHTML = "<div id=\"editor\">" +
        "<p>Hello World!</p>" +
        "<p>Some initial <strong>bold</strong> text</p>" +
        "<p><br></p>" +
        "</div>";

    connector.onStateChange = function () { // <1>
        var state = connector.getState();
        var data = state.data;

        var quill = new Quill('#editor', data.options);

        quill.on('text-change', function (delta, oldDelta, source) { // <2>
            if (source === 'user') {
                connector.valueChanged(quill.getText(), quill.getContents());
            }
        });
    }
};