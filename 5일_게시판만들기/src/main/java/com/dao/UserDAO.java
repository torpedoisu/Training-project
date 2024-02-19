package com.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.exception.UserException;
import com.global.DBManager;
import com.global.ResponseData;
import com.global.Status;
import com.vo.UserVO;

public class UserDAO {
    private static UserDAO userDao = null;
    public static Logger logger = LogManager.getLogger("UserDAO.class");        
    
    private UserDAO() {}
    
    public static UserDAO getInstance() {
        if (userDao == null) {
            synchronized(UserDAO.class) {
                userDao = new UserDAO();
            }
        }
        
        return userDao;
    }
    
    public void userInsert(UserVO user){
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        PreparedStatement statement = null;
        System.out.println("Inserting to DB...");
        
        String sql = "INSERT INTO user_tb (id, pwd) VALUES (?, ?)";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, user.getId());
            statement.setString(2, user.getPwd());
            statement.executeUpdate();
            
            dbManager.commit(); 
            
            logger.info("=== [SUCCESS] User: "+ user.getId() +" insert complete ===");
        } catch (SQLException e) {
            dbManager.rollback();
            e.printStackTrace();
            throw new UserException("DB에 insert 도중 에러 (행의 모든 값을 채워주세요)", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement);    
            }
        }
        
    }
    
    
}
