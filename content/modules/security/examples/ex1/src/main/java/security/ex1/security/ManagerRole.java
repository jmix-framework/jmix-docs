package security.ex1.security;

import security.ex1.security.permissions.CustomerFullAccessRole;
import security.ex1.security.permissions.OrderFullAccessRole;

//@ResourceRole(name = "Manager Permissions", code = "manager")
public interface ManagerRole extends
        CustomerFullAccessRole,
        OrderFullAccessRole
{
}
