package string;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class ChangeEncoding {

	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(System.in);
		
		System.out.print("파일 경로 입력: ");
		String sourceFilePath = sc.nextLine();
	
		FileReader fr = new FileReader(sourceFilePath);
		BufferedReader br = new BufferedReader(fr);
		
		String data; 
		StringBuilder sb = new StringBuilder();
		while ((data = br.readLine()) != null) {
			sb.append(data).append("\n");
		}
		
		br.close();
		
		FileOutputStream fos= new FileOutputStream(sourceFilePath);
		OutputStreamWriter osr = new OutputStreamWriter(fos, "UTF-8");
		osr.write(sb.toString());
		
		
		osr.close();
		
	}

}
