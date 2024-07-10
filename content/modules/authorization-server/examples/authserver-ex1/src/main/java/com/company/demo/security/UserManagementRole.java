package com.company.demo.security;

import com.company.demo.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;

//tag::user-management-role[]
@ResourceRole(name = "User management", code = UserManagementRole.CODE, scope = "API") // <1>
public interface UserManagementRole {

    String CODE = "user-management";

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.ALL)
    void user();
}
//end::user-management-role[]