package chap3.test;

public class VerifyCode {

	/**
	 * 生成4位网站验证码. 不能重复，验证码由数字和大小写字符，但是不能包含1,0,o,O,l,L,Z,z,2,9,g。
	 */
	public static void main(String[] args) {
		char[] code = new char[]{'3','4','5','6','7','8','a','b','c','d','e','f','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','A','B','C','D','E','F','G','H','I','J','K','M','N','P','Q','R','S','T','U','V','W','X','Y'};
		boolean[] flag = new boolean[code.length];
		
		int count=0;
		char[] selectedCode = new char[4];
		while(count<4){
			int selected = (int)(Math.random()*51);
			if(flag[selected]==false){
				flag[selected]=true;
				selectedCode[count]=code[selected];
				count++;
			}
		}
		for(char ch:selectedCode){
			System.out.print(ch);
		}
		System.out.println();
	}

}
