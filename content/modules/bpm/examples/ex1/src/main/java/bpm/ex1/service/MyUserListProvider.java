package bpm.ex1.service;

import bpm.ex1.entity.User;
import io.jmix.bpm.provider.UserListProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@UserListProvider("smpl_MyUserListProvider")
public class MyUserListProvider {

    public List<String> getValue(String executionId){
        return null;
    }
}