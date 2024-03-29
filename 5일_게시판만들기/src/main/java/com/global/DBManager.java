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

import com.exception.CustomException;

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
            logger.error("DBMangaer 초기화 성공");
        } catch (FileNotFoundException e) {
            logger.error("DBMangaer 초기화 중 예외 발생");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("DBMangaer 초기화 중 예외 발생");
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error("DBMangaer 초기화 중 예외 발생");
            e.printStackTrace();
            
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
                
                if (this.jdbcConnection == null || this.jdbcConnection.isClosed()) {
                    Class.forName(driver);
                    
                    this.jdbcConnection = DriverManager.getConnection(url, id, pwd);
                    this.jdbcConnection.setAutoCommit(false);
                    this.jdbcConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    
                    logger.debug("DB 연결 성공");
                }
            } catch (ClassNotFoundException e) {
                logger.error("jdbc 드라이버 존재하지 않음");
                e.printStackTrace();
            } catch (SQLException e) {
                logger.error("db connection 중 예외 발생");
                e.printStackTrace();
            }
    }
    
    // 커밋 실패시 롤백 수행
    public void commit(){
        try {
            logger.debug("DB에 커밋 시작...");
            
            if (this.jdbcConnection != null && !this.jdbcConnection.isClosed()) {
                this.jdbcConnection.commit();
                
                logger.debug("db에 커밋 완료");
            }
        } catch (SQLException e) {
            logger.error("db에 커밋 중 예외 발생");
            e.printStackTrace();
        } 
    }
    
    public void rollback() {
        try {
            logger.debug("트랜잭션 롤백 시작...");
            
            if (this.jdbcConnection != null && !this.jdbcConnection.isClosed()) {
                this.jdbcConnection.rollback();
                
                logger.debug("트랜잭션 롤백 완료");
            }
        } catch (SQLException e) {
            logger.error("db 롤백 중 예외 발생");
            e.printStackTrace();
        } 
    }

    public void disconnect(){
        try {
            if (this.jdbcConnection != null && !this.jdbcConnection.isClosed()) {
                this.jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                this.jdbcConnection.close();
                
            }   
            
        } catch (SQLException e) {
            logger.error("db와 연결 해제 중 예외 발생");
            e.printStackTrace();
        }
        
    }
    
    public void disconnect(PreparedStatement ps){
        try {
            if (this.jdbcConnection != null && !this.jdbcConnection.isClosed()) {
                this.jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                this.jdbcConnection.close();
                
                if (ps != null) {
                    ps.close();
                    
                    logger.debug("db와의 연결 해제 완료");
                }
            }   
            
        } catch (SQLException e) {
            logger.error("db와 연결 해제 중 예외 발생");
            e.printStackTrace();
        }
        
    }
    
    public void disconnect(PreparedStatement ps, ResultSet rs){
        try {
            if (this.jdbcConnection != null && !this.jdbcConnection.isClosed()) {
                this.jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                this.jdbcConnection.close();
                
                if (ps != null) {
                    ps.close();
                    
                    if (rs != null) {
                        rs.close();
                        logger.debug("db와의 연결 해제 완료");
                    }
                }
            }   
            
        } catch (SQLException e) {
            logger.error("db와 연결 해제 중 예외 발생");
            e.printStackTrace();
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
            logger.error("db와의 연결 확인 중 예외 발생");
            e.printStackTrace();
        }
        
        return isClosed;
    }
    
}
