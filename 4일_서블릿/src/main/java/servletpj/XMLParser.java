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
}