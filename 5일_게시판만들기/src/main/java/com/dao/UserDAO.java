package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.CustomException;
import com.global.DBManager;
import com.vo.UserVO;

public class UserDAO {
    private DBManager dbManager = null;

    public static Logger logger = LogManager.getLogger(UserDAO.class);        

    public UserDAO() {
        dbManager = new DBManager();
    }
    
    /**
     * USER_TB 테이블에 유저를 등록하는 메서드
     * 
     * @param user - db에 등록할 유저 정보, pk는 db에서 auto increment
     * @return userVO - pk가 등록된 UserVO 
     */
    public UserVO insert(UserVO user){
        logger.debug("[insert] User: "+ user.getId() +" 등록 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;

        String sql = "INSERT INTO USER_TB (ID, PWD) VALUES (?, ?)";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, user.getId());
            statement.setString(2, user.getPwd());
            statement.executeUpdate();
            
            dbManager.commit(); 
            
            logger.debug("User: "+ user.getId() +" 등록 완료");
            
            
        } catch (SQLException e) {
            logger.error("DB에 insert 도중 에러");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement);    
            }
        }
        
        return user;
    }

    /**
     * USER_TB 테이블에서 아이디와 비밀번호로 일치되는 유저를 반환하는 메서드
     * 
     * @param userId
     * @param userPwd - sha256으로 인코딩된 비밀번호
     * @return UserVO
     */
    public UserVO getUserWithIdEncPwd(String userId, String userEncPwd) {
        logger.debug("[getUserWithIdEncPwd] User: "+ userId +" 조회 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserVO user = new UserVO();
        
        String sql = "SELECT * FROM USER_TB WHERE ID = ? AND PWD = ?";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, userEncPwd);
            rs = statement.executeQuery();
            
            if (rs.next()) {
                user.setPk(rs.getString("PK"));
                user.setId(rs.getString("ID"));
                user.setPwd(rs.getString("PWD"));
            }
            
            logger.debug("User: "+ userId +" 조회 완료");
            
        } catch (SQLException e) {
            logger.error("DB에서 select 도중 에러");
            e.printStackTrace();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement, rs);    
            }
        }
        return user;

    }

    /**
     * USER_TB 테이블에서 아이디로 유저를 조회하는 메서드
     * 
     * @param userId
     * @return UserVO
     */
    public UserVO getUserWithId(String userId) {
        logger.debug("[getUserWithId] User: "+ userId +" 조회 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserVO user = new UserVO();
        
        String sql = "SELECT * FROM USER_TB WHERE ID = ?";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, userId);
            rs = statement.executeQuery();
            
            if (rs.next()) {
                user.setPk(rs.getString("PK"));
                user.setId(rs.getString("ID"));
                user.setPwd(rs.getString("PWD"));
            }
            logger.debug(user.getPwd());
            
            logger.debug("User: "+ user.getId() +" 조회 완료");
            
        } catch (SQLException e) {
            logger.error("DB에서 select 도중 에러");
            e.printStackTrace();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement, rs);    
            }
        }
        return user;
    }
    
    
}
