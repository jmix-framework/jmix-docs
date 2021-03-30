package bpm.ex1.service;

import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.provider.UserGroupListProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("smpl_MyUserGroupsProvider")
public class MyUserGroupsProvider implements UserGroupListProvider {

    @Override
    public List<UserGroup> getValue(String executionId) {
        return null;
    }
}