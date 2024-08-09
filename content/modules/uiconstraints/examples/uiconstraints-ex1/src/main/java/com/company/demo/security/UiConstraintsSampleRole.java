package com.company.demo.security;

import com.company.demo.view.user.UserDetailView;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.uiconstraints.annotation.UiComponentPolicy;
import io.jmix.uiconstraints.annotation.UiComponentPolicyAction;
import io.jmix.uiconstraints.annotation.UiComponentPolicyEffect;

//tag::role[]
@ResourceRole(name = "UiConstraintsSample", code = UiConstraintsSampleRole.CODE)
public interface UiConstraintsSampleRole {
    String CODE = "ui-constraints-sample";

    //tag::userDetailView[]
    @UiComponentPolicy(
            viewClass = UserDetailView.class,
            componentIds = {
                    "emailField",
                    "addressFragment.cityField"
            },
            action = UiComponentPolicyAction.ENABLED,
            effect = UiComponentPolicyEffect.DENY
    )
    @UiComponentPolicy(
            viewId = "User.detail",
            componentIds = {
                    "equipmentDataGrid.edit",
            },
            action = UiComponentPolicyAction.VISIBLE,
            effect = UiComponentPolicyEffect.DENY
    )
    void userDetailView();
    //end::userDetailView[]
}
//end::role[]