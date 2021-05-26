package ui.ex1.screen.screens.open;

import io.jmix.ui.screen.StandardCloseAction;

// tag::my-close-action[]
public class MyCloseAction extends StandardCloseAction {

    private String result;

    public MyCloseAction(String result) {
        super("myCloseAction");
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
// end::my-close-action[]
