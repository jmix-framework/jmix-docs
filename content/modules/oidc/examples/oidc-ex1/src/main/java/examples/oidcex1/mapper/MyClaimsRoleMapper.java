package examples.oidcex1.mapper;

//tag::code[]
import io.jmix.oidc.claimsmapper.BaseClaimsRolesMapper;
import io.jmix.security.role.ResourceRoleRepository;
import io.jmix.security.role.RoleGrantedAuthorityUtils;
import io.jmix.security.role.RowLevelRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

@Component
public class MyClaimsRoleMapper extends BaseClaimsRolesMapper {

    public MyClaimsRoleMapper(ResourceRoleRepository resourceRoleRepository,
                              RowLevelRoleRepository rowLevelRoleRepository,
                              RoleGrantedAuthorityUtils roleGrantedAuthorityUtils) {
        super(resourceRoleRepository, rowLevelRoleRepository, roleGrantedAuthorityUtils);
    }

    @Override
    protected Collection<String> getResourceRolesCodes(Map<String, Object> claims) {
        Collection<String> jmixRoleCodes = new HashSet<>();
        String position = (String) claims.get("position");
        if ("Manager".equals(position)) {
            jmixRoleCodes.add("edit-contracts");
            jmixRoleCodes.add("view-archive");
        } else {
            jmixRoleCodes.add("view-contracts");
        }
        return jmixRoleCodes;

    }
}
//end::code[]