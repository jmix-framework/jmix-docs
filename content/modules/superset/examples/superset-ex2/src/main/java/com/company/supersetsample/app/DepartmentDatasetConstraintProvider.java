package com.company.supersetsample.app;

import com.company.supersetsample.entity.Department;
import com.company.supersetsample.entity.User;
import com.company.supersetsample.security.DepartmentConstraintRole;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.SecurityProperties;
import io.jmix.supersetflowui.component.dataconstraint.DatasetConstraint;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentDatasetConstraintProvider {

    private final CurrentAuthentication currentAuthentication;
    private final SecurityProperties securityProperties;

    public DepartmentDatasetConstraintProvider(CurrentAuthentication currentAuthentication,
                                               SecurityProperties securityProperties) {
        this.currentAuthentication = currentAuthentication;
        this.securityProperties = securityProperties;
    }

    public List<DatasetConstraint> getConstraints() {
        Department department = getDepartment();
        if (hasDepartmentConstraintRole() && department != null) {
            return List.of(new DatasetConstraint(24, "department_name = '" + department.getName() + "'"));
        }
        return List.of();
    }

    private boolean hasDepartmentConstraintRole() {
        Authentication authentication = currentAuthentication.getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals(
                                securityProperties.getDefaultRowLevelRolePrefix() + DepartmentConstraintRole.CODE));
    }

    private Department getDepartment() {
        User user = (User) currentAuthentication.getUser();
        return user.getDepartment();
    }
}