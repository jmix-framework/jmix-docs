package security.ex1.security.finegrained;

import io.jmix.security.role.annotation.Role;

@Role(name = "Manager", code = "manager-aggr")
public interface ManagerRole extends
        CustomerFullAccessRole,
        OrderFullAccessRole,
        SameRegionOnlyRole
{
}
