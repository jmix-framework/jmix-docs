package com.company.demo.security.permissions;

import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerDetail;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

// tag::role[]
@ResourceRole(
        name = "Customer full access",
        code = "customer-full-access")
public interface CustomerFullAccessRole {

    @EntityPolicy(
            entityClass = Customer.class,
            actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(
            entityClass = Customer.class,
            attributes = "*",
            action = EntityAttributePolicyAction.MODIFY)
    @ViewPolicy(
            viewIds = {"Customer.list", "Customer.detail"})
    @MenuPolicy(
            menuIds = {"Customer.list"})
    // tag::specific-permission[]
    @SpecificPolicy(
            resources = {"customer.notify"})
    void customer();
    // end::specific-permission[]

    @EntityPolicy(
            entityClass = CustomerDetail.class,
            actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(
            entityClass = CustomerDetail.class,
            attributes = "*",
            action = EntityAttributePolicyAction.MODIFY)
    @ViewPolicy(
            viewIds = {"CustomerDetail.detail"})
    void customerDetail();
}
// end::role[]
