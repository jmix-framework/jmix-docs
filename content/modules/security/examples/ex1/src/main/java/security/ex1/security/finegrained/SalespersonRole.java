package security.ex1.security.finegrained;

import io.jmix.security.role.annotation.ResourceRole;

// TODO @PredicateRowLevelPolicy doesn't work in aggregated role, see https://github.com/Haulmont/jmix-security/issues/41

@ResourceRole(name = "Salesperson", code = "salesperson-aggr")
public interface SalespersonRole extends
        CustomerNonConfidentialAccessRole,
        OrderRestrictedAccessRole,
        OrdersCreatedByMeOnlyRole,
        SameRegionOnlyRole
{
}
