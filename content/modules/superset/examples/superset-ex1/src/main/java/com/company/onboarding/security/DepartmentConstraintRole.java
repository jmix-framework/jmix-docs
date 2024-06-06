package com.company.onboarding.security;

import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "department-constraint", code = DepartmentConstraintRole.CODE)
public interface DepartmentConstraintRole {
    String CODE = "department-constraint";
}