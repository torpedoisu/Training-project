package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.global.DBManager;
import com.vo.UserVO;

public class UserDAO {

    public static Logger logger = LogManager.getLogger(UserDAO.class);        

    /**
     * USER_TB 테이블에 유저를 등록하는 메서드
     * 
     * @param user - db에 등록할 유저 정보, pk는 db에서 auto increment
     * @return userVO - pk가 등록된 UserVO 
     * @throws SQLException 
     */
    public UserVO insert(DBManager dbManager, UserVO user) throws SQLException{
        logger.debug("[insert] User: "+ user.getId() +" 등록 시작");
        
        PreparedStatement statement = null;

        String sql = "INSERT INTO USER_TB (ID, PWD) VALUES (?, ?)";

        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, user.getId());
        statement.setString(2, user.getPwd());
        statement.executeUpdate();
        statement.close();
        
        logger.debug("[insert] User: "+ user.getId() +" 등록 완료");
        
        return user;
    }

    /**
     * USER_TB 테이블에서 아이디와 비밀번호로 일치되는 유저를 반환하는 메서드
     * 
     * @param userId
     * @param userPwd - sha256으로 인코딩된 비밀번호
     * @return UserVO
     * @throws SQLException 
     */
    public UserVO getUserWithIdEncPwd(DBManager dbManager, String userId, String userEncPwd) throws SQLException {
        logger.debug("[getUserWithIdEncPwd] User: "+ userId +" 조회 시작");
        
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserVO user = new UserVO();
        
        String sql = "SELECT * FROM USER_TB WHERE ID = ? AND PWD = ?";
        
        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, userId);
        statement.setString(2, userEncPwd);
        rs = statement.executeQuery();
        
        if (rs.next()) {
            user.setPk(rs.getString("PK"));
            user.setId(rs.getString("ID"));
            user.setPwd(rs.getString("PWD"));
        }
        
        rs.close();
        statement.close();
            
        logger.debug("[getUserWithIdEncPwd] User: "+ userId +" 조회 완료");
        
        return user;

    }

    /**
     * USER_TB 테이블에서 아이디로 유저를 조회하는 메서드
     * 
     * @param userId
     * @return UserVO
     * @throws SQLException 
     */
    public UserVO getUserWithId(DBManager dbManager, String userId) throws SQLException {
        logger.debug("[getUserWithId] User: "+ userId +" 조회 시작");
        
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserVO user = new UserVO();
        
        String sql = "SELECT * FROM USER_TB WHERE ID = ?";
        
        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, userId);
        rs = statement.executeQuery();
        
        if (rs.next()) {
            user.setPk(rs.getString("PK"));
            user.setId(rs.getString("ID"));
            user.setPwd(rs.getString("PWD"));
        }
        
        rs.close();
        statement.close();
        
        logger.debug("[getUserWithId] User: "+ userId +" 조회 완료");
        
        return user;
    }

    /**
     * USER_TB 테이블에서 pk로 유저를 조회하는 메서드
     * @param userPk
     * @return UserVO
     * @throws SQLException 
     */
    public UserVO selectUserWithPk(DBManager dbManager, String userPk) throws SQLException {
        logger.debug("[selectUserWithPk] UserPk: "+ userPk +" 조회 시작");
        
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserVO user = new UserVO();
        
        String sql = "SELECT * FROM USER_TB WHERE PK = ?";
        
        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, userPk);
        rs = statement.executeQuery();
        
        if (rs.next()) {
            user.setPk(rs.getString("PK"));
            user.setId(rs.getString("ID"));
            user.setPwd(rs.getString("PWD"));
        }
        
        rs.close();
        statement.close();
        
        logger.debug("[selectUserWithPk] UserPk: "+ userPk +" 조회 완료");
        
        return user;
    }
    
    
}
