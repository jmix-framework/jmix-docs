package com.company.onboarding.exception;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.RouteNotFoundError;
import jakarta.servlet.http.HttpServletResponse;

public class CustomRouteNotFoundError extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {
        Div message = new Div("404");
        message.getElement().getStyle().setFontSize("10em");

        Anchor link = new Anchor(".", "Main Page");
        link.getElement().getStyle().setFontSize("2em");

        getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
        getElement().appendChild(
                message.getElement(),
                link.getElement()
        );

        return HttpServletResponse.SC_NOT_FOUND;
    }
}
