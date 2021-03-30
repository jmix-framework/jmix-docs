package bpm.ex1.service;

import bpm.ex1.entity.User;
import io.jmix.bpm.provider.UserListProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("smpl_MyUserListProvider")
public class MyUserListProvider implements UserListProvider {

    @Override
    public List<User> getValue(String executionId){
        return null;
    }
}