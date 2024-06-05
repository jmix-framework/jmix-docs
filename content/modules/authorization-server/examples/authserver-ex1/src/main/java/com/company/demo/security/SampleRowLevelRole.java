package com.company.demo.security;

import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "SampleRowLevelRole", code = "sample-row-level-role")
public interface SampleRowLevelRole {
}