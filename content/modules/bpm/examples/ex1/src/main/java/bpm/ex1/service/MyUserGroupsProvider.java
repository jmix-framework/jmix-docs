package bpm.ex1.service;

import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.provider.UserGroupListProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@UserGroupListProvider("smpl_MyUserGroupsProvider")
public class MyUserGroupsProvider {

    public List<String> getValue(String executionId) {
        return null;
    }
}