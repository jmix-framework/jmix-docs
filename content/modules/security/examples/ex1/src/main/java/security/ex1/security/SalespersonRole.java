package security.ex1.security;

import security.ex1.security.permissions.CustomerNonConfidentialAccessRole;
import security.ex1.security.permissions.OrderRestrictedAccessRole;

//@ResourceRole(name = "Salesperson Permissions", code = "salesperson")
public interface SalespersonRole extends
        CustomerNonConfidentialAccessRole,
        OrderRestrictedAccessRole
{
}
