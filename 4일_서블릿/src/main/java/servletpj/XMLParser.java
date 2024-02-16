package servletpj;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

public class XMLParser {
    
    /**
     * employee 형식의 xml -> employee 객체 리스트로 변환하는 메서드
     * @param inputStream
     * @return 형식이 맞지 않을 시 null 반환, 성공 시 employee 객체 리스트 반환
     */
    public List<Employee> parseXML(InputStream inputStream) {
        List<Employee> employees = new ArrayList<>();
        
        try {
            System.out.println("Parsing XML...");
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream); // InputStream에서 XML 읽기
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("employee");
            if (nList.getLength() == 0) {
                return null;
            }
            
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Employee employee = new Employee();
                    
                    String sDepartment = eElement.getElementsByTagName("department").item(0).getTextContent();
                    String sName = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String sPosition = eElement.getElementsByTagName("position").item(0).getTextContent();
                    String sEngName = eElement.getElementsByTagName("englishName").item(0).getTextContent();
                    String sPhoneNumber = eElement.getElementsByTagName("phone").item(0).getTextContent();
                    String sEmail = eElement.getElementsByTagName("email").item(0).getTextContent();
                    
                    employee.setDepartment(sDepartment);
                    employee.setName(sName);
                    employee.setPosition(sPosition);
                    employee.setEnglishName(sEngName);
                    employee.setPhoneNumber(sPhoneNumber);
                    employee.setEmail(sEmail);
                    
                    employees.add(employee);
                }
            }
            
            System.out.println("Complete parsing XML");        
        } catch (NullPointerException e) {
            System.out.println("=== [ERROR] not a adequate XML form ===");
            return null;
        } catch (ParserConfigurationException | SAXException | IOException e3) {
            System.out.println("=== [ERROR] while parsing XML ===");
            return null;
        }
  
        return employees;
    }
    
    /**
     * JSON 데이터 -> xml 형식으로 문서를 만드는 메서드
     * @param sb
     * @return xml 형식의 stringbuilder 반환
     */
    public String makeXML(StringBuilder sb) {
        // XML 형태 잡기
        System.out.println("Making XML...");
           
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
       
        return xml.toString();
    }
    
    
    /**
     * JSON 데이터 -> Employee 객체로 변환해주는 메서드
     * @param sb
     * @param tool - axios or jquery
     * @return 성공 시 employee 리스트 반환
     */
    public List<Employee> makeEmployee(StringBuilder sb, String tool) {
        System.out.println("Making data to Employee object...");

        List<Employee> employeeList = new ArrayList<Employee>();
        
        String[] employees;
        String jsonString = sb.toString();
        if(tool.equals("axios")) {// axios 사용 시
            
            int startIndex = jsonString.indexOf("[");
            int endIndex = jsonString.lastIndexOf("]");

            String employeesString = jsonString.substring(startIndex + 1, endIndex);
    
            // 쉼표를 기준으로 문자열을 분리하여 배열로 만듦
            employees = employeesString.split("\\},\\{");
        } else { //jquery 사용 시 
            employees = jsonString.split("},");
        }

        
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