package com.global;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.exception.LoginException;

public class DBManager {
    private String driver;
    private String url;
    private String id;
    private String pwd;
    private Connection jdbcConnection;
    
    // 클래스 변수 초기화
    public DBManager(){
        Properties properties = new Properties();
        String propertiesName = "/resources/db.properties";
        
        try {
            System.out.println("Getting Db info...");
            
            properties.load(this.getClass().getClassLoader().getResourceAsStream(propertiesName));
            
            System.out.println("Complete getting db info");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        
        this.driver = properties.getProperty("driver");
        this.url = properties.getProperty("url");
        this.id = properties.getProperty("id");
        this.pwd = properties.getProperty("pwd");
        
        throw new LoginException("이건 예외 테스트 메시지입니다");
    }
    
    // 커밋 옵션 READ COMMITTED로 설정
    public void connect() {
            try {
                if (jdbcConnection == null || jdbcConnection.isClosed()) {
                    System.out.println("Start connectiong to db...");
                    
                    Class.forName(driver);
                    
                    jdbcConnection = DriverManager.getConnection(url, id, pwd);
                    jdbcConnection.setAutoCommit(false);
                    jdbcConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    
                    System.out.println("Complete connect to db");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("=== [ERROR] jdbc driver does not exist ===");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("=== [ERROR] while connection to db ===");
                e.printStackTrace();
            }
    }
    
    // 커밋 실패시 롤백 수행
    public void commit(){
        System.out.println("Commiting transaction to db...");
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.commit();
                
                System.out.println("Success commit to db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("=== [ERROR] while commiting to db ===");
        } 
    }
    
    public void rollback() {
     // 롤백 시작 
        System.out.println("Rollbacking transaction...");
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.rollback();
                
                System.out.println("Success transaction rollback");
            }
        } catch (SQLException r) {
            r.printStackTrace();
            System.out.println("=== [ERROR] while rollback ===");
        } 
    }

    public void disconnect(PreparedStatement ps){
        System.out.println("Closing connection with db...");
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                jdbcConnection.close();
                
                if (ps != null) {
                    ps.close();
                    
                    System.out.println("Success closing connection with db");
                }
            }   
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("=== [ERROR] while disconnection with db ===");
        }
        
    }
    
    public void disconnect(PreparedStatement ps, ResultSet rs){
        System.out.println("Closing connection with db...");
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.setAutoCommit(true); // 커밋 옵션 다시 원래대로 되돌리기
                jdbcConnection.close();
                
                if (ps != null) {
                    ps.close();
                    
                    if (rs != null) {
                        rs.close();
                        System.out.println("Success closing connection with db");
                    }
                }
            }   
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("=== [ERROR] while disconnection with db ===");
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
            System.out.println("=== [ERROR] db access refused while checking isClosed === ");
        }
        
        return isClosed;
    }
    
}
