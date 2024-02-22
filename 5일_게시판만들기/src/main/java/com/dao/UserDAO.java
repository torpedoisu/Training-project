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
        logger.debug("User: "+ user.getId() +" 등록 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        String sql = "INSERT INTO user_tb (id, pwd) VALUES (?, ?)";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, user.getId());
            statement.setString(2, user.getPwd());
            statement.executeUpdate();
            
            dbManager.commit(); 
            
            logger.debug("User: "+ user.getId() +" 등록 완료");
            
            if (generatedKeys.next()) {
                String userPk = generatedKeys.getString(1);// 새로 생성된 게시글의 PK
                user.setPk(userPk); // ArticleVO에 설정
            }
            
            
        } catch (SQLException e) {
            logger.error("DB에 insert 도중 에러");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement, generatedKeys);    
            }
        }
        
        return user;
    }

    public UserVO getUser(String userPk) {
        logger.debug("UserPk: "+ userPk +" 조회 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        String sql = "SELECT * FROM USER_TB WHERE PK = ?;";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, userPk);
            statement.executeUpdate();
            
            dbManager.commit(); 
            
            logger.debug("UserPk: "+ userPk +" 조회 완료");
            
        } catch (SQLException e) {
            logger.error("DB에서 select 도중 에러");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement, generatedKeys);    
            }
        }
        
    }
    
    
}
