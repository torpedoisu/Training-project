package servletpj;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXMLFile {
  public static void main(String argv[]) {
    try {
    	//  TODO: 생성자 추가
      File fXmlFile = new File("");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);
      doc.getDocumentElement().normalize();
      
      NodeList nList = doc.getElementsByTagName("employee");
      
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String department = eElement.getElementsByTagName("department").item(0).getTextContent();
          String name = eElement.getElementsByTagName("name").item(0).getTextContent();
          String position = eElement.getElementsByTagName("position").item(0).getTextContent();
          String englishName = eElement.getElementsByTagName("englishName").item(0).getTextContent();
          String phone = eElement.getElementsByTagName("phone").item(0).getTextContent();
          String email = eElement.getElementsByTagName("email").item(0).getTextContent();
          
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


