package db;

import java.sql.SQLException;

public class RollbackTest {
	public static void main (String[] args) {
		ManageDB db = new ManageDB();
		try {
			
			db.selectAllEmployee();
			
			db.insertEmployee("rollbacktest", "rollback", "rollback");
			
			System.out.println("Rollback...");
			db.jdbcConnection.rollback();
			
			db.commit();
			
			System.out.println("=== [Success] Testing transaction rollback ===");
		
			db.selectAllEmployee();
		} catch (SQLException e) {
			System.out.println("=== [Error] While testing DB ===");
			e.printStackTrace();
		} finally {
			try {
				db.disconnect();
			} catch (SQLException e) {
				System.out.println("=== [Error] While closing connection with DB ===");
				e.printStackTrace();
			}
		}
	
		
		
		
		
	}
}
