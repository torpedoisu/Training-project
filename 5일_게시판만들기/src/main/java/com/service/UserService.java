package com.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.dao.UserDAO;
import com.exception.CustomException;
import com.global.Encrypt;
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
        user.setPwd(Encrypt.encode(pwd));
        
        UserDAO userDao = new UserDAO();
        UserVO insertedUser = userDao.insert(user);
        
        // user의 pk 설정을 위한 로직
        if (!insertedUser.isExist()) {
            throw new CustomException("Service - 회원 가입 중 오류", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "/userRegister.jsp");
        }
        
        UserVO userInfoInDB = userDao.getUserWithIdEncPwd(insertedUser.getId(), insertedUser.getPwd());
        
        if (!userInfoInDB.isExist()) {
            throw new CustomException("Service - 회원 db에 저장 후 pk를 불러오다 오류", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "/userRegister.jsp");
        }
        
        user.setPk(userInfoInDB.getPk());
        
        return user;
    }

    
    public boolean checkUserIsValidInSession(String userPk, String userId, String userPwd) {
        UserDAO userDao = new UserDAO();
        
        String userEncPwd = Encrypt.encode(userPwd);

        UserVO userInDB = userDao.getUserWithIdEncPwd(userId, userEncPwd);
        
        if (userInDB.getId().equals(userId) && userInDB.getPwd().equals(userEncPwd)) {
            return true;
        }
        
        return false;
    }

    public UserVO getUserInDB(String userId, String userPwd) {
        UserDAO userDao = new UserDAO();
        
        String userEncPwd = Encrypt.encode(userPwd);

        UserVO userInDB = userDao.getUserWithIdEncPwd(userId, userEncPwd);
        
        return userInDB;
    }
    
    
}
