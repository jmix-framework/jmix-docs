package com.company.onboarding.security;

import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "HrManagerRowLevelRole", code = "hr-manager-row-level-role")
public interface HrManagerRowLevelRole {
}