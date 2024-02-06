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
    
    public StringBuilder makeDBQuery(StringBuilder sb) {
        return new StringBuilder();
    }
}