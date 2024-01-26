package day1.string;

import java.util.Scanner;

public class 특정문자열치환 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("원본 text 문자열");
		String originText = sc.nextLine();
		
		System.out.println("치환 대상 문자열");
		String before = sc.nextLine();
		
		System.out.println("치환될 문자열");
		String after = sc.nextLine();
		
		originText = originText.replace(before, after);
		
		System.out.println(originText);
	}

}
