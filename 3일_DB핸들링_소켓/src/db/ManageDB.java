package db;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageDB {

	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String JDBC_URL = "jdbc:oracle:thin:@rain.torpedo.co.kr:1521:orcl";
	private final String JDBC_USERNAME = "isu";
	private final String JDBC_PASSWORD = "xhvleh";
	protected Connection jdbcConnection;
	
	public void connect() throws SQLException {
		// 커넥션이 안되어 있는 상태라면 연결
		System.out.println("Connecting with DB...");
		
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName(DRIVER_NAME);
				jdbcConnection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
				jdbcConnection.setAutoCommit(false); // 트랜잭션 처리를 위한 AutoCommit 중지
			} catch (ClassNotFoundException e) {
				System.out.println("=== [Error] Db access error ===");
				throw new SQLException(e);
			} 
		}
	}
	
	public void commit() throws SQLException {
		// 커넥션이 안되어 있는 상태라면 연결
		System.out.println("Commiting transaction to DB...");
		
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.commit();
		}
	}
	
	public void disconnect() throws SQLException {
		// 커넥션이 되어 있는 상태라면 연결 끊기
		System.out.println("Closing connection with DB...");
		
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.setAutoCommit(true); // 트랜잭션 처리를 기본 상태로 되돌림
			jdbcConnection.close();
		}
	}
	
	public void checkId(int id) throws SQLException {
		connect();
		
		System.out.println("Checking id exists from DB...");
		String sql = "SELECT id FROM employees_tb WHERE id = ?";
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	    statement.setInt(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (!resultSet.next()) {
	        // id가 일치하지 않으면 예외를 던짐
	        throw new SQLException("No employee with the given id exists");
	    }

		statement.close();
	}

	public void insertEmployee(String name, String email, String phoneNumber) throws SQLException {
		connect();
		
		System.out.println("Inserting to DB...");
		
		String sql = "INSERT INTO employees_tb (name, email, phone_number) VALUES (?, ?, ?)";
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, name);
		statement.setString(2, email);
		statement.setString(3, phoneNumber);
		
		statement.executeUpdate();
		
		statement.close();
		
	}
	
	public void selectAllEmployee() throws SQLException {
		connect();
		
		System.out.println("Selecting from DB...");
		String sql = "SELECT * FROM employees_tb";
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String phoneNumber = resultSet.getString("phone_number");
			
			System.out.printf("id: %d, name: %s, email: %s, phone_number: %s \n", id, name, email, phoneNumber);
		}
		
		resultSet.close();
		statement.close();
		
	}
	
	public void updateEmployee(int id, String key, String value) throws SQLException {		
		connect();
		
		System.out.println("Updating to DB...");
		
		String sqlForfix = "UPDATE employees_tb SET ";
		String sqlPrefix = " WHERE id = ?";
        
		String fullSql = null;
		switch (key) {
			case "name":
				fullSql = sqlForfix + "name = ?" + sqlPrefix;
				break;
			case "email":
				fullSql = sqlForfix + "email = ?" + sqlPrefix;
				break;
			case "phone_number":
				fullSql = sqlForfix + "phoneNumber = ?" + sqlPrefix;
				break;
		}
		
		if (fullSql == null) {
			throw new SQLException();
		}
		
		PreparedStatement statement = jdbcConnection.prepareStatement(fullSql);
		statement.setString(1, value);
		statement.setInt(2, id);

        statement.executeUpdate();
        
        statement.close();
        
	}
	
	public void updateEmployee(int id, String name, String email, String phoneNumber) throws SQLException {
		connect();
		
		System.out.println("Updating to DB...");
		
		String sql = "UPDATE employees_tb SET name = ?, email = ?, phone_number = ? WHERE id = ?";
		
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, email);
        statement.setString(3, phoneNumber);
        statement.setInt(4, id);
        
        statement.executeUpdate();
        
        statement.close();
        
	}
	
	public void deleteEmployee(int id) throws SQLException {
		connect();
		
		System.out.println("Deleting from DB...");
		
		String sql = "DELETE FROM employees_tb WHERE id = ?";
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        
        statement.executeUpdate();
        
        statement.close();
	}
	
	public static void main(String[] args) {
		ManageDB db = new ManageDB();
		Scanner sc = new Scanner(System.in);
		
		String name;
		String email;
		String phone;
		int id;
		
		while (true) {
			System.out.print("Enter Command (insert, update, delete, selectAll): ");
			String cmd = sc.nextLine();
			
			try {
				
				switch (cmd) {
					
					case "insert":
						System.out.println("Enter name, email, phone with blank Ex. heesom heesom.hs@gmail.com 010-7256-1378");
						System.out.print(">");
						String[] userInsertInfo = sc.nextLine().split(" ");
						
						name = userInsertInfo[0];
						email = userInsertInfo[1];
						phone = userInsertInfo[2];
						
						db.insertEmployee(name, email, phone);	
						System.out.printf("=== [Success] name: %s, email: %s, phone_number: %s inserted ===\n", name, email, phone );
						break;
					
					case "update":
						System.out.print("Enter id to update: ");
						id = Integer.parseInt(sc.nextLine());
						
						db.checkId(id); // Db에 id 존재하는지 체크
						db.commit();
						db.disconnect();
						System.out.println("id exist in DB...");
						
						System.out.print("Enter 1 to update all column, 2 to update certain column: ");
						int select = Integer.parseInt(sc.nextLine());
						
						if (select == 1) {
							System.out.println("Enter name, email, phone with blank Ex. heesom heesom.hs@gmail.com 010-7256-1378");
							System.out.print(">"); 
							String[] userUpdateInfo = sc.nextLine().split(" ");
							
							name = userUpdateInfo[0];
							email = userUpdateInfo[1];
							phone = userUpdateInfo[2];
							
							db.updateEmployee(id, name, email, phone);	
							System.out.printf("=== [Success] id: %s = name: %s, email: %s, phone_number: %s updated ===\n", id, name, email, phone );
						} else if (select == 2) {
							System.out.println("Enter column name (name, email, phone_number) and value Ex. name heesom");
							System.out.print(">"); 
							String[] userUpdateKeyValue = sc.nextLine().split(" ");
							
							String key = userUpdateKeyValue[0];
							String value = userUpdateKeyValue[1];
							
							db.updateEmployee(id, key, value);	
							System.out.printf("=== [Success] id: %s = key: %s, value: %s updated ===\n", id, key, value );
						} else {
							System.out.println("Entered wrong number. Please enter 1 or 2");
						}
						
						break;
						
					case "delete":
						System.out.print("Enter id to delete: ");
						id = Integer.parseInt(sc.nextLine());
						
						db.checkId(id); // Db에 id 존재하는지 체크
						db.commit();
						db.disconnect();
						System.out.println("id exist in DB...");
						
						db.deleteEmployee(id);	
						System.out.printf("=== [Success] id: %s deleted ===\n", id);
						
						break;
						
					case "selectAll":
						db.selectAllEmployee();
						break;
						
				}

		        db.commit(); // 모든 작업이 완료된 후 커밋
		        System.out.println("Commit succeed...");
			
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("=== [Error] sql error ===");
				try {
					db.jdbcConnection.rollback(); // sql 예외 발생시 롤백
					System.out.println("Rollback succeed...");
				} catch (SQLException e1) {
					System.out.println("=== [Error] Error while rollback DB ===");
					e1.printStackTrace();
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("=== [Error] Error while typing ===");
			} finally {
		        try {
		            db.disconnect(); // 데이터베이스 연결 종료
		        } catch (SQLException e) {
		            e.printStackTrace();
		            System.out.println("=== [Error] Error while closing connection with DB ===");
			    }
			}
		} // end of while
		
	} // end of main

}
