package servletpj;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class XMLParser {
    public List<Employee> parseXML(String filePath) {
        List<Employee> employees = new ArrayList<>();
        
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
}