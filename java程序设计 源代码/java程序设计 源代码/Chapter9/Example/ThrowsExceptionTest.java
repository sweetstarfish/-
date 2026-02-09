package EXception;

import java.util.Scanner;

public class ThrowsExceptionTest {
	public static void main(String args[]) throws NumberFormatException{
		Scanner scn=new Scanner(System.in);
		System.out.println("请输入字符串：");
		String str = scn.next();
		int num = Integer.parseInt(str);
		System.out.println(num);
	}
}

