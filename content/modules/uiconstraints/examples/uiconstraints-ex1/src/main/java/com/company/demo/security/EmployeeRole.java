package com.company.demo.security;

import com.company.demo.view.customer.CustomerDetailView;
import com.company.demo.view.customer.CustomerListView;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.uiconstraints.annotation.UiComponentPolicy;
import io.jmix.uiconstraints.annotation.UiComponentPolicyAction;
import io.jmix.uiconstraints.annotation.UiComponentPolicyEffect;

@ResourceRole(name = "EmployeeRole", code = EmployeeRole.CODE, scope = "UI")
public interface EmployeeRole {
    String CODE = "employee-role";

    @UiComponentPolicy(
            viewClass = CustomerListView.class,
            componentIds = {"customersDataGrid.importCustomersAction"},
            action = UiComponentPolicyAction.ENABLED,
            effect = UiComponentPolicyEffect.DENY
    )
    @UiComponentPolicy(
            viewClass = CustomerDetailView.class,
            componentIds = {"commentsPane"},
            action = UiComponentPolicyAction.VISIBLE,
            effect = UiComponentPolicyEffect.DENY
    )
    void customers();
}