package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import response.ResponseData;
import response.Status;
import servletpj.EmployeeDAO;

public class EmployeeService {
    private DBManager dbManager;
    
    public EmployeeService() {
        this.dbManager = new DBManager();
    }
    
    /**
     * DB에 유저 테이블 정보를 저장하는 메서드
     * 
     * @param userTableEmployees
     * @return 
     */
    public ResponseData syncToEmployeeTable(List<EmployeeDAO> userTableEmployees) {
        System.out.println("Start sync to table -> db...");
        
        try {
            // 1. selectAll로 존재하는 employee 전부 가져오기
            List<EmployeeDAO> dbEmployees = this.selectAllEmployee();
            
            // selectAll은 employees 반환되지 않는 경우(null)인 경우 예외
            if (dbEmployees == null) {
                return new ResponseData(Status.FAIL, "DB에서 값 가져오던 중 에러");
            }
            // 2.이름 기준 정렬 후 비교
            Collections.sort(userTableEmployees, Comparator.comparing(EmployeeDAO::getName));
            Collections.sort(dbEmployees, Comparator.comparing(EmployeeDAO::getName));
            
            // 3. 사용자가 만든 표와 비교 
            int userIndex = 0;
            int dbIndex = 0;
    
            ResponseData response = null;
            
            while (userIndex < userTableEmployees.size() && dbIndex < dbEmployees.size()) {
                EmployeeDAO userEmployee = userTableEmployees.get(userIndex);
                EmployeeDAO dbEmployee = dbEmployees.get(dbIndex);
    
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
                    response = this.insertEmployee(userEmployee);
                    userIndex++;
                    
                } else {
                    // 3-2. db에 존재하는 employee인데 표에 존재하지 않는다면 delete
                    response = this.deleteEmployee(dbEmployee);
                    dbIndex++;
                }
                
                // insert와 delete는 예외 시 response 값 존재
                if (response != null) {
                    return response;
                }
            }
            
    
            // 3-3 표에 남아 있는 employee insert
            while (userIndex < userTableEmployees.size()) {
                response = this.insertEmployee(userTableEmployees.get(userIndex));
                userIndex++;
                
                // insert와 delete는 예외 시 response 값 존재
                if (response != null) {
                    return response;
                }
            }
    
            // 3-4 db에 남아있는 employee delete
            while (dbIndex < dbEmployees.size()) {
                response = this.deleteEmployee(dbEmployees.get(dbIndex));
                dbIndex++;
                
                // insert와 delete는 예외 시 response 값 존재
                if (response != null) {
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Status.FAIL, "DB 처리 중 예외 발생");
        }
        
        System.out.println("Complete sync");
        
        return new ResponseData(Status.SUCCESS, "DB에 저장 성공");
    }
    
    /**
     * 테이블에 있는 레코드 다 가져오는 메서드
     * @return 성공시 employee 리스트 반환, 예외시 null 반환
     */
    private List<EmployeeDAO> selectAllEmployee() {
        dbManager.connect();
        
        System.out.println("Selecting all employee from db...");
        
        String sql = "SELECT * FROM employees_tb";
        
        List<EmployeeDAO> employees = new ArrayList<EmployeeDAO>();
        
        try {
            PreparedStatement statement = dbManager.getJdbcConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                EmployeeDAO em_tmp = new EmployeeDAO();
                
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
            return null;
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
       
        return employees;
    }
    
    /**
     * DB에 레코드 삽입하는 메서드
     * @param employee
     * @return 예외 시 ResponseData 반환, 성공할 경우 null 반환 
     */
    private ResponseData insertEmployee(EmployeeDAO employee) {
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
            
            return new ResponseData(Status.FAIL, "DB에 insert 도중 에러 (행의 모든 값을 채워주세요)");
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
        return null;
    }
    
    /**
     * DB에서 레코드 삭제하는 메서드 
     * @param employee
     * @return 예외 시 ReponseData 반환, 성공 시 null 반환
     */
    private ResponseData deleteEmployee(EmployeeDAO employee) {
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
            return new ResponseData(Status.FAIL, "DB에서 delete 도중 에러");
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
            
        }
        
        return null;
    }
}
