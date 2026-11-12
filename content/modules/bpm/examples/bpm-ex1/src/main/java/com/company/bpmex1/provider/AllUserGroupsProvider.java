package com.company.bpmex1.provider;

import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.provider.UserGroupListProvider;
import io.jmix.bpm.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// tag::user-group-list-provider[]
@UserGroupListProvider(value = "AllUserGroupsProvider", description = "Returns all user groups")
public class AllUserGroupsProvider {

    @Autowired
    private UserGroupService userGroupService;

    public List<String> getAllUserGroups() {
        return userGroupService.getAllUserGroups().stream()
                .map(UserGroup::getCode)
                .toList();
    }
}
// end::user-group-list-provider[]
