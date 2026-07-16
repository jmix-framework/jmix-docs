package com.company.sample.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "Employee", code = "employee")
public interface EmployeeRole {
}
