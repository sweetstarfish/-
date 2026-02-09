package EXception;

import java.util.Scanner;

public class UserRegisteTest {		
		public void regist(int num) throws RegisterException {
			if (num < 0)
			throw new RegisterException("注册编号为负值，不合理");
			else
			System.out.println("注册编号为：" + num);
			}
		
		public static void main(String args[]) {
			UserRegisteTest t = new UserRegisteTest();
			Scanner scn=new Scanner(System.in);
			System.out.println("请输入要注册的用户编号为：");
			int ID=scn.nextInt();
			try {
				t.regist(ID);
			} catch (RegisterException e) {
				e.printStackTrace();
			}
			}		
}

