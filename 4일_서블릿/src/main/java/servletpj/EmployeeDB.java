package servletpj;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeDB {
    private DBManager dbManager;
    
    public EmployeeDB() {
        this.dbManager = new DBManager();
    }
    
    public void syncToEmployeeTable(List<Employee> userTableEmployees) {
        System.out.println("Start sync to table -> db...");
        
        // 1. selectAll로 존재하는 employee 전부 가져오기
        List<Employee> dbEmployees = this.selectAllEmployee();
        
        // 2.이름 기준 정렬 후 비교
        Collections.sort(userTableEmployees, Comparator.comparing(Employee::getName));
        Collections.sort(dbEmployees, Comparator.comparing(Employee::getName));
        
        // 3. 사용자가 만든 표와 비교 
        int userIndex = 0;
        int dbIndex = 0;

        while (userIndex < userTableEmployees.size() && dbIndex < dbEmployees.size()) {
            Employee userEmployee = userTableEmployees.get(userIndex);
            Employee dbEmployee = dbEmployees.get(dbIndex);

            int departComp = userEmployee.getDepartment().compareTo(dbEmployee.getDepartment());
            int nameComp = userEmployee.getName().compareTo(dbEmployee.getName());
            int positionComp = userEmployee.getPosition().compareTo(dbEmployee.getPosition());
            int engNameComp = userEmployee.getEnglishName().compareTo(dbEmployee.getEnglishName());
            int phoneNumComp = userEmployee.getPhoneNumber().compareTo(dbEmployee.getPhoneNumber());
            int emailComp = userEmployee.getEmail().compareTo(dbEmployee.getEmail());
            int sumComp = departComp + nameComp + positionComp + engNameComp + phoneNumComp + emailComp;
            
            if (sumComp == 0) {
                userIndex++;
                dbIndex++;
            } else if (sumComp < 0) {
             // 3-1. db에 존재하지 않는 employee 라면 insert
                this.insertEmployee(userEmployee);
                userIndex++;
            } else {
             // 3-2. db에 존재하는 employee인데 표에 존재하지 않는다면 delete
                this.deleteEmployee(dbEmployee);
                dbIndex++;
            }
        }
        

        // 3-3 표에 남아 있는 employee insert
        while (userIndex < userTableEmployees.size()) {
            this.insertEmployee(userTableEmployees.get(userIndex));
            userIndex++;
        }

        // 3-4 db에 남아있는 employee delete
        while (dbIndex < dbEmployees.size()) {
            this.deleteEmployee(dbEmployees.get(dbIndex));
            dbIndex++;
        }
        
        System.out.println("Complete sync");
    }
    
    private List<Employee> selectAllEmployee() {
        dbManager.connect();
        
        System.out.println("Selecting all employee from db...");
        
        String sql = "SELECT * FROM employees_tb";
        
        List<Employee> employees = new ArrayList<Employee>();
        
        try {
            PreparedStatement statement = dbManager.getJdbcConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Employee em_tmp = new Employee();
                
                em_tmp.setPk(String.valueOf(resultSet.getInt("id")));
                em_tmp.setDepartment(resultSet.getString("department"));
                em_tmp.setName(resultSet.getString("name"));
                em_tmp.setPosition(resultSet.getString("position"));
                em_tmp.setEnglishName(resultSet.getString("english_name"));
                em_tmp.setPhoneNumber(resultSet.getString("phone_number"));
                em_tmp.setEmail(resultSet.getString("email"));
                
                employees.add(em_tmp);
            }
            
            resultSet.close();
            statement.close();
            
            System.out.println("=== [SUCCESS] Selecing all complete ===");
        } catch (SQLException e) {
            System.out.println("=== [ERROR] while selecting all employee in db ===");
            e.printStackTrace();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
       
        return employees;
    }
    
    private void insertEmployee(Employee employee) {
        dbManager.connect();
        
        System.out.println("Inserting to DB...");
        
        String sql = "INSERT INTO employees_tb (department, name, position, english_name, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, employee.getDepartment());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getPosition());
            statement.setString(4, employee.getEnglishName());
            statement.setString(5, employee.getPhoneNumber());
            statement.setString(6, employee.getEmail());
            
            statement.executeUpdate();
            statement.close();
            
            dbManager.commit(); 
            
            System.out.println("=== [SUCCESS] Employee insert complete ===");
        } catch (SQLException e) {
            System.out.println("=== [ERROR] while inserting employee ===");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }

    }
    
    private void deleteEmployee(Employee employee) {
        dbManager.connect();
        
        System.out.println("Deleting from DB...");
        
        String sql = "DELETE FROM employees_tb WHERE id = ?";
        
        try {
            PreparedStatement statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, employee.getPk());

            statement.executeUpdate();
            statement.close();
            
            dbManager.commit(); 
            
            System.out.println("=== [SUCCESS] employee delete complete ===");
        } catch (SQLException e) {
            System.out.println("=== [ERROR] while deleting employee ===");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
            
        }

    }
}
