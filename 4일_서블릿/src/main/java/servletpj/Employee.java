package servletpj;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Employee {
    private String pk;
    private String department;
    private String name;
    private String position;
    private String englishName;
    private String phoneNumber;
    private String email;

    public String getPk() {
        return pk;
    }
    
    public void setPk(String val) {
        this.pk = val;
    }
    
    public String getDepartment() {
    	return department;
    }
    
    public void setDepartment(String val) {
    	this.department = val;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String val) {
    	this.name = val;
    }
    
    public String getPosition() {
    	return position;
    }
    
    public void setPosition(String val) {
    	this.position = val;
    }
    
    public String getEnglishName() {
    	return englishName;
    }
    
    public void setEnglishName(String val) {
    	this.englishName = val;
    }
    
    public String getPhoneNumber() {
    	return phoneNumber;
    }
    
    public void setPhoneNumber(String val) {
    	this.phoneNumber = val;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String val) {
    	this.email = val;
    }

}
