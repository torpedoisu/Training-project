package com.service;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dao.UserDAO;
import com.exception.CustomException;
import com.global.DBManager;
import com.global.Encrypt;
import com.global.Status;
import com.vo.UserVO;

public class UserService {
    
    public static Logger logger = LogManager.getLogger(UserService.class);  
    
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
        logger.debug("Service - 회원가입 트랜잭션 시작");
        
        DBManager dbManager = new DBManager();
        
        UserVO user = UserVO.getNewInstanceWithUUID();
        
        dbManager.connect();
        try {
            String encPwd = Encrypt.encode(pwd);
            
            user.setId(id);
            user.setPwd(encPwd);
            
            UserDAO userDao = new UserDAO();
            
            if (this.isIdIsAlreadyInDB(dbManager, id)) {
                throw new CustomException("이미 존재하는 id입니다 다른 id를 입력해주세요", HttpServletResponse.SC_CONFLICT, "userRegister.jsp");
            }
            userDao.insert(dbManager, user);
            
            dbManager.commit();
            
        } catch (SQLException e) {
            logger.error("회원 가입 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
        logger.debug("Service - 회원가입 트랜잭션 완료");
        
        return user;
    }
    
    /**
     * 클라이언트가 입력한 정보가 db에 있는 user와 일치하는지 검증
     * 
     * @param userId - 클라이언트가 입력한 id
     * @param userPwd - 클라이언트가 입력한 password
     * @return UserVO
     */
    public UserVO login(String userId, String userPwd) {
        logger.debug("Service - 로그인 트랜잭션 시작");
        
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        
        UserVO userInDB = null;
        try {
            String userEncPwd = Encrypt.encode(userPwd);
            
            UserDAO userDao = new UserDAO();
            userInDB = userDao.getUserWithIdEncPwd(dbManager, userId, userEncPwd);
            
            // 유저 정보로 조회한 객체가 비어있다면 db에 존재하지 않는 것
            if(!userInDB.isExist()) {
                throw new CustomException("틀린 아이디이거나 비밀번호입니다", HttpServletResponse.SC_BAD_REQUEST, "login.jsp");
            }
            
            dbManager.commit();
            
        } catch (SQLException e) {
            logger.error("로그인 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
        logger.debug("Service - 로그인 트랜잭션 완료");
        return userInDB;
    }
    
    /**
     * 유저의 아이디가 중복된 아이디인지 체크
     * 
     * @param userId
     * @return boolean
     * @throws SQLException 
     */
    private boolean isIdIsAlreadyInDB(DBManager dbManager, String userId) throws SQLException {
        
        UserDAO userDao = new UserDAO();

        UserVO userInDB = userDao.getUserWithId(dbManager, userId);
        
        if (userInDB.isExist()) {
            return true;
        }
        return false;
    }
    
}
