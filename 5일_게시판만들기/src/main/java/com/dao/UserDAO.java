package com.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.global.DBManager;
import com.global.ResponseData;
import com.global.Status;
import com.vo.UserVO;

public class UserDAO {
    private static UserDAO dao = new UserDAO();
    private DBManager dbManager = new DBManager();
    private HashMap<Status, Object> returnMap = new HashMap<>();
    
    private UserDAO() {
        returnMap.put(Status.FAIL, null);
        returnMap.put(Status.DATA, null);
    }
    
    public static UserDAO getInstance() {
        return dao;
    }
    
    public HashMap<Status, Object> userInsert(UserVO user) {
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
            
            System.out.println("=== [SUCCESS] User insert complete ===");
        } catch (SQLException e) {
            System.out.println("=== [ERROR] while inserting user " + e + " ===");
            dbManager.rollback();
            returnMap.put(Status.FAIL, (Object) new ResponseData(Status.FAIL, "DB에 insert 도중 에러 (행의 모든 값을 채워주세요)"));
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement);    
            }
        }
        
            return returnMap;
    }
    
    
}
