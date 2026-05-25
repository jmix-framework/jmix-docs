package com.company.demo.security;

import com.company.demo.entity.Customer;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;

// tag::mt-1[]
@ResourceRole(name = "UsersRole", code = UsersRole.CODE, scope = "UI")
public interface UsersRole {
    String CODE = "users-role";

    @EntityAttributePolicy(entityClass = Customer.class,
            attributes = {"id", "name", "region", "version"},
            action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();
}
// end::mt-1[]