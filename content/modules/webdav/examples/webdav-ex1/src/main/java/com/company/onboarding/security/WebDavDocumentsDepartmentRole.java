package com.company.onboarding.security;

import com.company.onboarding.entity.Department;
import com.company.onboarding.entity.User;
import com.company.onboarding.entity.WebdavDocumentWrapper;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;

// tag::role[]
@RowLevelRole(name = "WebDav documents department", code = WebDavDocumentsDepartmentRole.CODE)
public interface WebDavDocumentsDepartmentRole {
    String CODE = "web-dav-documents-department";

    @PredicateRowLevelPolicy(entityClass = WebdavDocumentWrapper.class, actions = RowLevelPolicyAction.READ)
    default RowLevelBiPredicate<WebdavDocumentWrapper, ApplicationContext> webdavDocumentWrapperPredicate() {
        return (webdavDocumentWrapper, applicationContext) -> {
            CurrentUserSubstitution currentUserSubstitution = applicationContext.getBean(CurrentUserSubstitution.class);
            UserDetails effectiveUser = currentUserSubstitution.getEffectiveUser();

            boolean isAdmin = effectiveUser.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> {
                        String authority = grantedAuthority.getAuthority();
                        return authority.contains("hr-manager") || authority.contains("system-full-access");
                    });
            if (isAdmin) {
                return true;
            }

            boolean isEmployee = effectiveUser.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> {
                        String authority = grantedAuthority.getAuthority();
                        return authority.contains("employee");
                    });
            if (isEmployee) {
                Department userDepartment = ((User) effectiveUser).getDepartment();
                Department webdavDocDepartment = webdavDocumentWrapper.getDepartment();
                return userDepartment.equals(webdavDocDepartment);
            }

            return false;
        };
    }
}
// end::role[]