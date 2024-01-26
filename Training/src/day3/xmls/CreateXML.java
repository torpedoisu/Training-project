package day3.xmls;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXML {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter destination file path: ");
		String dstPath = sc.nextLine();
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 새로운 DOM 트리 생성
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlStandalone(true);
			
			//Document에 product 태그 추가
			Element products = document.createElement("products");
			document.appendChild(products);
			
			List<String> product1 = Arrays.asList("100", "새우깡", "1500");
			List<String> product2 = Arrays.asList("101", "양파링", "2000");
			List<String> product3 = Arrays.asList("102", "홈런볼", "3000");
			
			List<List<String>> productList = Arrays.asList(product1, product2, product3);
			
			for (List<String> line : productList) {
				Element product = document.createElement("product");
				
				Element number = document.createElement("number");
				number.setTextContent(line.get(0)); //line의 1번째를 number 태그 안에 설명
				
				Element name = document.createElement("name");
				name.setTextContent(line.get(1)); // line의 2번째를 name 태그 안에 설정
				
				Element price = document.createElement("price");
				price.setTextContent(line.get(2)); // line의 3번째를 price 태그 안에 설정
				
				product.appendChild(number);
				product.appendChild(name);
				product.appendChild(price);
				
				products.appendChild(product); // product 태그 안에 만든 number name price 태그 넣어주기
				
			}
			
			// 다른 DOM 트리를 다른 형식으로 변환할 때 사용
			// DOM 트리를 문자열로 변환해 파일로 저장하거나, 네트워크를 통해 전송
			TransformerFactory transformerFactory = TransformerFactory.newInstance(); 
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("encoding", "UTF-8");
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("doctype-public", "yes");
			
			Source source = new DOMSource(document);
			File file = new File(dstPath);
			StreamResult result = new StreamResult(file);
			
			transformer.transform(source, result);
			
			System.out.println("=== [Success] Create Document ===");
			
		} catch (ParserConfigurationException e) {
			// DocumentBuilder 생성할 때 문제 발생
			System.out.println("=== [Error] Error while making DOM ===");
			e.printStackTrace();
		} catch (TransformerException e) {
			// XML문서를 다른 형태로 변환하는 과정에서 문제 발생
			System.out.println("=== [Error] Error while transforming XML ===");
			e.printStackTrace();
		}

	}
}
