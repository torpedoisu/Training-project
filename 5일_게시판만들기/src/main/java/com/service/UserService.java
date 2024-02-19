package com.service;

import java.util.HashMap;

import com.dao.UserDAO;
import com.exception.LoginException;
import com.global.Status;
import com.global.TestException;
import com.vo.UserVO;

public class UserService {
    private static UserService userService = new UserService();
    private UserDAO dao = UserDAO.getInstance();
    
    private UserService() {}
    
    public static UserService getInstance() {
        return userService;
    }
    
    public void userInsert(UserVO user){
        dao.userInsert(user);
    }
    
}
