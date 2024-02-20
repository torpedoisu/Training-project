package string;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class ChangeEncoding {
	
	
	public static void main(String[] args) {
		BufferedReader br = null;
		OutputStreamWriter osr = null;
		
		Scanner sc = new Scanner(System.in);
		
		String sourceFilePath = sc.nextLine();
		try {
			
			System.out.println("Reading file...");
			br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)));
			
			String data; 
			StringBuilder sb = new StringBuilder();
			while ((data = br.readLine()) != null) {
				sb.append(data).append("\n");
			}
			
			System.out.println("Encoding file...");
			osr = new OutputStreamWriter(new FileOutputStream(sourceFilePath), "UTF-8");
			osr.write(sb.toString());
			
			System.out.println("=== [Success] Encoding file ===");
		} catch (FileNotFoundException e) {
			System.out.println("=== [Error] Input file not found ===");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("=== [Error] Unsupported encoding ===");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("=== [Error] IO Exception while encoding ===");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("Closing stream...");
				if (br != null) br.close();
				if (osr != null) osr.close();
			} catch (IOException e) {
				System.out.println("=== [Error] IO Exception while closing  ===");
				e.printStackTrace();
			}
		}
		
	}

}
