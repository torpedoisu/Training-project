package com.service;

import java.util.HashMap;

import com.dao.UserDAO;
import com.exception.CustomException;
import com.global.Status;
import com.vo.UserVO;

public class UserService {
    
    private static UserService userService = null;
    
    private UserService() {}
    
    public static UserService getInstance() {
        if (userService == null) {
            synchronized (UserService.class) {
                userService = new UserService();    
            }
        }
        return userService;
    }
    
    public UserVO userInsert(UserVO user){
        UserDAO userDao = new UserDAO();

        return userDao.insert(user);
    }
    
}
