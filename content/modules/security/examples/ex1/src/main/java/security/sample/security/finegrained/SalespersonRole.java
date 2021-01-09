package security.sample.security.finegrained;

import io.jmix.security.role.annotation.Role;

// TODO @PredicateRowLevelPolicy doesn't work in aggregated role, see https://github.com/Haulmont/jmix-security/issues/41

@Role(name = "Salesperson", code = "salesperson-aggr")
public interface SalespersonRole extends
        CustomerNonConfidentialAccessRole,
        OrderRestrictedAccessRole,
        OrdersCreatedByMeOnlyRole,
        SameRegionOnlyRole
{
}
