package fileIO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class CopyFile {
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter source file(dir) name: ");
		String srcFile = sc.nextLine();
		
		System.out.print("Enter destination file(dir) name: ");
		String dstFile = sc.nextLine();;
		
		File file = new File(srcFile);
		File newFile = new File(dstFile);

		try {
			CopyFile copyFile = new CopyFile();
			
			if (file.isFile()) {
				// 파일이라면 바로 복사
				System.out.println("Start copying file: " + file.getName() + " ...");
				Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);	
			} else {
				// 디렉토리라면 중첩되어 있는 파일과 디렉토리 고려해서 복사
				System.out.println("Start copying folder: " + file.getName() + "/ ...");
				copyFile.copyDir(file, newFile);
			}
			
			System.out.println("=== [Success] File Copy ===  ");
			
		} catch (NoSuchFileException e) {
			e.printStackTrace();
			System.out.println("=== [Error] No Such file === ");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("=== [Error] IOException ===");
		}
		
	}
	
	private void copyDir(File file, File newFile) throws IOException{ 
		
		if (!newFile.exists()) {
			newFile.mkdir();
		}
		
		File[] files = file.listFiles();
		
		if (files == null) {
			return; 
		}
		
		for (File nestedFile : files) {
			if (nestedFile.isDirectory()) {
				copyDir(nestedFile, new File(newFile, nestedFile.getName()));	
			} else {
				Files.copy(nestedFile.toPath(), new File(newFile, nestedFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);	
			}
		}
	
	}
}
