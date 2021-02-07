package security.ex1.security.finegrained;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "Manager", code = "manager-aggr")
public interface ManagerRole extends
        CustomerFullAccessRole,
        OrderFullAccessRole,
        SameRegionOnlyRole
{
}
