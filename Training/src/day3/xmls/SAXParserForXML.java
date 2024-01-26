package day3.xmls;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserForXML {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter source file path: ");
		String srcPath = sc.nextLine();
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
		
			DefaultHandler handler = new DefaultHandler() {
				private boolean isNumber =  false;
				private boolean isName = false;
				private boolean isPrice = false;
				
				// 엘리먼트 시작 태그를 만났을 때 호출
				@Override
	            public void startElement(String uri, String localName, String qName, Attributes attributes)
	                    throws SAXException {
	                if (qName.equalsIgnoreCase("number")) {
	                    isNumber = true;
	                } else if (qName.equalsIgnoreCase("name")) {
	                    isName = true;
	                } else if (qName.equalsIgnoreCase("price")) {
	                    isPrice = true;
	                }
	            }
				
				// 엘리먼트 종료 태그를 만났을 때 호출
	            @Override
	            public void endElement(String uri, String localName, String qName) throws SAXException {
	            	if (qName.equalsIgnoreCase("number")) {
	                    isNumber = false;
	                } else if (qName.equalsIgnoreCase("name")) {
	                    isName = false;
	                } else if (qName.equalsIgnoreCase("price")) {
	                    isPrice = false;
	                }
	            }
	
	            // 태그 내의 문자 데이터를 만났을 때 호출
	            @Override
	            public void characters(char ch[], int start, int length) throws SAXException {
	                if (isNumber) {
	                    System.out.println("번호: " + new String(ch, start, length));
	                    isNumber = false;
	                } else if (isName) {
	                    System.out.println("이름: " + new String(ch, start, length));
	                    isName = false;
	                } else if (isPrice) {
	                    System.out.println("가격: " + new String(ch, start, length));
	                    isPrice = false;
	                }
	            }
			};
			
			saxParser.parse(new File(srcPath), handler);
			
			System.out.println("=== [Success] Parsing XML ===");
		
		} catch (ParserConfigurationException e) {
			System.out.println("=== [Error] Parser Configuration Error ===");
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("=== [Error] Error While Parsing ===");
		}
		catch (IOException e) {
			System.out.println("=== [Error] Error While Handing File ===");
			e.printStackTrace();
		}
		
		
		
	}

}
