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
    
    public UserVO userInsert(String id, String pwd){
        UserVO user = new UserVO();
        user.setId(id);
        user.setPwd(pwd);
        
        UserDAO userDao = new UserDAO();

        // user의 pk 설정
        user = userDao.insert(user);
        UserVO userInfoInDB = userDao.getUserWithIdPwd(id, pwd);
        user.setPk(userInfoInDB.getPk());
        
        return user;
    }

    public boolean checkUserIsValid(String userPk, String userId, String userPwd) {
        UserDAO userDao = new UserDAO();

        UserVO userInDB = userDao.getUser(userPk);
        
        if (userInDB.getId().equals(userId) && userInDB.getPwd().equals(userPwd)) {
            return true;
        }
        
        return false;
    }
    
}
