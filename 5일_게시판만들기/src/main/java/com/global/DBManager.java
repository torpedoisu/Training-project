package com.global;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.exception.UserException;

public class DBManager {
    public static Logger logger = LogManager.getLogger("DBManager.class");
    
    private String driver;
    private String url;
    private String id;
    private String pwd;
    private Connection jdbcConnection;
    

    
    // 멤버 변수 초기화
    public DBManager(){
        Properties properties = new Properties();
        String propertiesName = "/resources/db.properties";
        
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(propertiesName));
            logger.info("DB Manager 초기화 완료");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new UserException("DBMangaer 초기화 중 예외 발생", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserException("DBMangaer 초기화 중 예외 발생", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new UserException("DBMangaer 초기화 중 예외 발생", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
        
        this.driver = properties.getProperty("driver");
        this.url = properties.getProperty("url");
        this.id = properties.getProperty("id");
        this.pwd = properties.getProperty("pwd");
    }
    
    // 커밋 옵션 READ COMMITTED로 설정
    public void connect() {
            try {
                if (jdbcConnection == null || jdbcConnection.isClosed()) {
                    Class.forName(driver);
                    
                    jdbcConnection = DriverManager.getConnection(url, id, pwd);
                    jdbcConnection.setAutoCommit(false);
                    jdbcConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    
                    logger.info("Complete connect to db");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new UserException("jdbc 드라이버 존재하지 않음" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new UserException("db connection 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
            }
    }
    
    // 커밋 실패시 롤백 수행
    public void commit(){
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.commit();
                
                logger.info("Success commit to db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db에 커밋 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        } 
    }
    
    public void rollback() {
     // 롤백 시작 
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.rollback();
                
                logger.info("Success transaction rollback");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db 롤백 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        } 
    }

    public void disconnect(PreparedStatement ps){
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                jdbcConnection.close();
                
                if (ps != null) {
                    ps.close();
                    
                    logger.info("Success closing connection with db");
                }
            }   
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db와 연결 해제 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
        
    }
    
    public void disconnect(PreparedStatement ps, ResultSet rs){
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                jdbcConnection.close();
                
                if (ps != null) {
                    ps.close();
                    
                    if (rs != null) {
                        rs.close();
                        logger.info("Success closing connection with db");
                    }
                }
            }   
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db와 연결 해제 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
        
    }
    
    
    public Connection getJdbcConnection() {
        return this.jdbcConnection;
    }
    
    public boolean checkJdbcConnectionIsClosed() {
        boolean isClosed = false;
        
        try {
            isClosed = this.jdbcConnection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db와의 연결 확인 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
        
        return isClosed;
    }
    
}
