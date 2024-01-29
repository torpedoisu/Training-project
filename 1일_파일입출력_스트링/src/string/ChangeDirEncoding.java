package string;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Scanner;

public class ChangeDirEncoding {

	public static void main(String[] args) {
		ChangeDirEncoding changeDirEncoding = new ChangeDirEncoding();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("디랙토리 경로: ");
		String directoryPath = sc.nextLine();
		
		System.out.print("변환 인코딩 포맷: ");
		String targetEncoding = sc.nextLine();

        changeDirEncoding.convertEncodingRecursively(new File(directoryPath), targetEncoding);
        
        System.out.println("=== [Success] Encoding file/directory ===");
	}

    private void convertEncodingRecursively(File file, String targetEncoding){
    	// 디렉토리이면 계속 아래로 들어가기
        if (file.isDirectory()) {
        	System.out.println("Reading directory...");
            File[] files = file.listFiles();
            if (files != null) {
                for (File nestedFile : files) {
                    convertEncodingRecursively(nestedFile, targetEncoding);
                }
            }
        // 파일이면 인코딩 변경    
        } else {
        	BufferedReader br = null;

			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				System.out.println("=== [Error] File not found ===");
				e.printStackTrace();
			}
        	
        	
        	String data;
        	OutputStreamWriter osr = null;
        	try {
        		StringBuilder sb = new StringBuilder();
        	
        		System.out.println("Reading file...");
        		
				while((data = br.readLine()) != null) {
					sb.append(data).append("\n");
				}
				
				System.out.println("Encoding file...");
	    		osr = new OutputStreamWriter(new FileOutputStream(file), targetEncoding);
	    		osr.write(sb.toString());
	    		
			} catch (IOException e) {
				System.out.println("=== [Error] IOException while reding and writing ===");
				e.printStackTrace();
			} finally {
				try {
					if (br != null) br.close();
		    		if (osr != null) osr.close();	
				} catch (IOException e) {
					System.out.println("=== [Error] IOException while closing  ===");
					e.printStackTrace();
				}
	    		
			}
        	
        }
    }
}
