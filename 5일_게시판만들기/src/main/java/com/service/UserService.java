package com.service;

import java.util.HashMap;

import com.dao.UserDAO;
import com.exception.UserException;
import com.global.Status;
import com.vo.UserVO;

public class UserService {
    
    private static UserService userService = null;
    private UserDAO dao = UserDAO.getInstance();
    
    private UserService() {}
    
    public static UserService getInstance() {
        if (userService == null) {
            synchronized (UserService.class) {
                userService = new UserService();    
            }
        }
        return userService;
    }
    
    public void userInsert(UserVO user){
        dao.userInsert(user);
    }
    
}
