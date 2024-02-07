package servletpj;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class XMLParser {
    public List<Employee> parseXML(InputStream inputStream) {
        List<Employee> employees = new ArrayList<>();
        
        try {
            System.out.println("Parsing XML...");
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream); // InputStream에서 XML 읽기
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("employee");

            if (nList == null) {
                // TODO:: XML 형식 맞지 않을시 예외 처리
            }
            
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Employee employee = new Employee();
                    employee.setDepartment(eElement.getElementsByTagName("department").item(0).getTextContent());
                    employee.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    employee.setPosition(eElement.getElementsByTagName("position").item(0).getTextContent());
                    employee.setEnglishName(eElement.getElementsByTagName("englishName").item(0).getTextContent());
                    employee.setPhoneNumber(eElement.getElementsByTagName("phone").item(0).getTextContent());
                    employee.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
                    employees.add(employee);
                }
            }
            
            System.out.println("Complete parsing XML");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    /**
     * JSON 데이터 -> xml 형식으로 문서를 만드는 메서드
     * @param sb
     * @return
     */
    public StringBuilder makeXML(StringBuilder sb) {
        // XML 형태 잡기
        System.out.println("Making XML...");
        
        // JSON 확인용 로그 
        // System.out.println(sb.toString()); 
           
        String[] employees = sb.toString().split("},");
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("\n<employees>\n");
           
        for (String employee : employees) {
            xml.append("  <employee>\n");
            String[] attributes = employee.replaceAll("[\\[\\]{}\"]", "").split(",");
            for (String attribute : attributes) {
                String[] keyValue = attribute.split(":");
                String key = keyValue[0].trim();
                String value = keyValue.length > 1 ? keyValue[1].trim() : ""; // 값이 없는 경우 처리
                xml.append("    <" + key + ">" + value + "</" + key + ">\n");
            }
            xml.append("  </employee>\n");
        }
        xml.append("</employees>");
    
        System.out.println("=== Complete making XML ===");
        System.out.println(xml.toString());
        System.out.println("===========================");
       
        return xml;
    }
    
    /**
     * JSON 데이터 -> Employee 객체로 변환해주는 메서드 
     * @param sb
     * @return
     */
    public List<Employee> makeEmployee(StringBuilder sb) {
        System.out.println("Making data to Employee object...");
        
        List<Employee> employeeList = new ArrayList<Employee>();
        String[] employees = sb.toString().split("},");
        
        for (String employee : employees) {
            String[] attributes = employee.replaceAll("[\\[\\]{}\"]", "").split(",");
            Employee em_tmp = new Employee();
            
            for (String attribute : attributes) {
                String[] keyValue = attribute.split(":");
                String key = keyValue[0].trim();
                String value = keyValue.length > 1 ? keyValue[1].trim() : ""; // 값이 없는 경우 처리
                
                switch(key) {
                    case "department":
                        em_tmp.setDepartment(value);
                        break;
                        
                    case "name":
                        em_tmp.setName(value);
                        break;
                        
                    case "position":
                        em_tmp.setPosition(value);
                        break;
                        
                    case "englishName":
                        em_tmp.setEnglishName(value);
                        break;
                        
                    case "phoneNumber":
                        em_tmp.setPhoneNumber(value);
                        break;
                        
                    case "email":
                        em_tmp.setEmail(value);
                        break;
                    
                    default:
                        System.out.println("===[ERROR] cannot recognize properties of employee json ===");
                }
            }
            
            employeeList.add(em_tmp);
        }
        
        return employeeList;
    }
    
}