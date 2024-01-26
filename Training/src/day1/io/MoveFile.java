package day1.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class MoveFile {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter source file(dir) name: ");
		String srcFile = sc.nextLine();
		
		System.out.println("Enter destination file(dir) name: ");
		String dstFile = sc.nextLine();;
		
		File src = new File(srcFile);
		File dst = new File(dstFile);
		
		try {
			
			if (src.isFile()) {
				// 파일이라면 바로 이동
				Files.move(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);	
			} else {
				// 디렉토리라면 중첩되어 있는 파일과 디렉토리 고려해서 이돔 후 원본 삭제
				moveDir(src, dst);
			}
			
			System.out.println("=== Success File Moving ===");
			
		} catch (NoSuchFileException e) {
			e.printStackTrace();
			System.out.println("=== No Such file ===");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("=== IOException ===");
		}

	}
	
	private static void moveDir(File srcFile, File dstFile) throws IOException{
		
		if (!dstFile.exists()) {
			dstFile.mkdir();
		}
		
		File[] files = srcFile.listFiles();
		
		if (files == null) {
			return;
		}
		
		for (File nestedFile : files) {
			if (nestedFile.isFile()) {
				Files.move(nestedFile.toPath(), new File(dstFile, nestedFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
			} else {
				moveDir(nestedFile, new File(dstFile, nestedFile.getName()));
			}
		}
		
		// 파일 다 옮기면 상위 원본 디렉토리 삭제
		srcFile.delete();
		
	}

}
