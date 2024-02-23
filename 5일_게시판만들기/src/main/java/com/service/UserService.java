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
    
    /**
     * 회원 가입 메서드
     * 
     * 예외 처리
     * - id가 중복되었는지 체크
     * - 저장이 잘 되었는지 체크 
     * 
     * @param id - 유저가 입력한 아이디
     * @param pwd - 유저가 입력한 비밀번호 (인코딩 전)
     * @return UserVO
     */
    public UserVO register(String id, String pwd){
        String encPwd = Encrypt.encode(pwd);
        
        UserVO user = new UserVO();
        user.setId(id);
        user.setPwd(encPwd);
        
        UserDAO userDao = new UserDAO();
        
        if (this.isIdIsAlreadyInDB(id)) {
            throw new CustomException("이미 존재하는 id입니다 다른 id를 입력해주세요", HttpServletResponse.SC_CONFLICT, "userRegister.jsp");
        }
        userDao.insert(user);
        
        // user의 pk 설정을 위한 로직
        UserVO userInfoInDB = userDao.getUserWithIdEncPwd(user.getId(), user.getPwd());
        
        if (!userInfoInDB.isExist()) {
            throw new CustomException("Service - 회원 db에 저장 후 pk를 불러오다 오류", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "userRegister.jsp");
        }
        
        user.setPk(userInfoInDB.getPk());
        
        return user;
    }

    /**
     * 유저의 아이디와 비밀번호로 User객체를 받아오는 메서드
     * 
     * @param userId - 유저가 입력한 아이디
     * @param userPwd- 유저가 입력한 비밀번호 (인코딩 전)
     * @return UserVO
     */
    public UserVO getUserInDB(String userId, String userPwd) {
        UserDAO userDao = new UserDAO();
        
        String userEncPwd = Encrypt.encode(userPwd);

        UserVO userInDB = userDao.getUserWithIdEncPwd(userId, userEncPwd);
        
        return userInDB;
    }
    
    /**
     * 유저의 아이디가 중복된 아이디인지 체크
     * 
     * @param userId
     * @return boolean
     */
    private boolean isIdIsAlreadyInDB(String userId) {
        UserDAO userDao = new UserDAO();

        UserVO userInDB = userDao.getUserWithId(userId);
        
        if (userInDB.isExist()) {
            return true;
        }
        return false;
    }
    
    
}
