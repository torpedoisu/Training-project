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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.UserException;

public class DBManager {
    public static Logger logger = LogManager.getLogger(DBManager.class);
    
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
            logger.debug("DBMangaer 초기화 성공");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new UserException("DBMangaer 초기화 중 예외 발생", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserException("DBMangaer 초기화 중 예외 발생", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new UserException("DBMangaer 초기화 중 예외 발생", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        this.driver = properties.getProperty("driver");
        this.url = properties.getProperty("url");
        this.id = properties.getProperty("id");
        this.pwd = properties.getProperty("pwd");
    }
    
    // 커밋 옵션 READ COMMITTED로 설정
    public void connect() {
            try {
                logger.debug("DB 연결 시작...");
                
                if (jdbcConnection == null || jdbcConnection.isClosed()) {
                    Class.forName(driver);
                    
                    jdbcConnection = DriverManager.getConnection(url, id, pwd);
                    jdbcConnection.setAutoCommit(false);
                    jdbcConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    
                    logger.debug("DB 연결 성공");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new UserException("jdbc 드라이버 존재하지 않음" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new UserException("db connection 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
    }
    
    // 커밋 실패시 롤백 수행
    public void commit(){
        try {
            logger.debug("DB에 커밋 시작...");
            
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.commit();
                
                logger.debug("db에 커밋 완료");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db에 커밋 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } 
    }
    
    public void rollback() {
     // �ѹ� ���� 
        try {
            logger.debug("트랜잭션 롤백 시작...");
            
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.rollback();
                
                logger.debug("트랜잭션 롤백 완료");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db 롤백 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } 
    }

    public void disconnect(PreparedStatement ps){
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                jdbcConnection.close();
                
                if (ps != null) {
                    ps.close();
                    
                    logger.debug("db와의 연결 해제 완료");
                }
            }   
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db와 연결 해제 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
                        logger.debug("db와의 연결 해제 완료");
                    }
                }
            }   
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("db와 연결 해제 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
            throw new UserException("db와의 연결 확인 중 예외 발생" , HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        return isClosed;
    }
    
}
