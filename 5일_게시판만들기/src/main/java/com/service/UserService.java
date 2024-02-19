package com.service;

import java.util.HashMap;

import com.dao.UserDAO;
import com.global.Status;
import com.vo.UserVO;

public class UserService {
    private static UserService userService = new UserService();
    private UserDAO dao = UserDAO.getInstance();
    
    private UserService() {}
    
    public static UserService getInstance() {
        return userService;
    }
    
    public HashMap<Status, Object> userInsert(UserVO user) {
        return dao.userInsert(user);
    }
}
