package string;

import java.util.Scanner;

public class ReplaceString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.print("원본 text 문자열: ");
		String originText = sc.nextLine();
		
		System.out.print("치환 대상 문자열: ");
		String before = sc.nextLine();
		
		System.out.print("치환될 문자열: ");
		String after = sc.nextLine();
		
		String convertedString = originText.replace(before, after);
		
		System.out.println("=== 결과: " + convertedString + " ===");
	}

}
