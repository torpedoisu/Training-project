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
	private Connection jdbcConnection;
	
	public void connect() throws SQLException {
		// 커넥션이 안되어 있는 상태라면 연결
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName(DRIVER_NAME);
				jdbcConnection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
			} catch (ClassNotFoundException e) {
				System.out.println("=== [Error] Db access error ===");
				throw new SQLException(e);
			}
		}
	}
	
	public void disconnect() throws SQLException {
		// 커넥션이 되어 있는 상태라면 연결 끊기
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	

	public void insertEmployee(String name, String email, String phoneNumber) throws SQLException {
		String sql = "INSERT INTO employees_tb (name, email, phone_number) VALUES (?, ?, ?)";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, name);
		statement.setString(2, email);
		statement.setString(3, phoneNumber);
		
		statement.executeUpdate();
		
		statement.close();
		
		disconnect();
	}
	
	public void selectAllEmployee() throws SQLException {
		String sql = "SELECT * FROM employees_tb";
		
		connect();
		
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
		disconnect();
		
	}
	
	public void updateEmployee(int id, String name, String email, String phoneNumber) throws SQLException {
		String sql = "UPDATE employees_tb SET name = ?, email = ?, phone_number = ? WHERE id = ?";
		
		connect();
		
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, email);
        statement.setString(3, phoneNumber);
        statement.setInt(4, id);
        
        statement.executeUpdate();
        
        statement.close();
        disconnect();
        
	}
	
	public void deleteEmployee(int id) throws SQLException {
		String sql = "DELETE FROM employees_tb WHERE id = ?";
        
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        
        statement.executeUpdate();
        
        statement.close();
        disconnect();
	}
	
	public static void main(String[] args) {
		ManageDB db = new ManageDB();
		Scanner sc = new Scanner(System.in);
		
		String name;
		String email;
		String phone;
		String id;
		
		
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
						id = sc.nextLine();
						
						System.out.println("Enter name, email, phone with blank Ex. heesom heesom.hs@gmail.com 010-7256-1378");
						System.out.print(">"); 
						String[] userUpdateInfo = sc.nextLine().split(" ");
						
						name = userUpdateInfo[0];
						email = userUpdateInfo[1];
						phone = userUpdateInfo[2];
						
						db.updateEmployee(Integer.parseInt(id), name, email, phone);	
						System.out.printf("=== [Success] id: %s = name: %s, email: %s, phone_number: %s updated ===\n", id, name, email, phone );
						
						break;
						
					case "delete":
						System.out.print("Enter id to delete: ");
						id = sc.nextLine();
						
						db.deleteEmployee(Integer.parseInt(id));	
						System.out.printf("=== [Success] id: %s deleted ===\n", id);
						
						break;
						
					case "selectAll":
						db.selectAllEmployee();
						break;
						
				}
				
			
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("=== [Error] sql error ===");
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("=== [Error] Error while typing ===");
			}
		} // end of while
		
	} // end of main

}
