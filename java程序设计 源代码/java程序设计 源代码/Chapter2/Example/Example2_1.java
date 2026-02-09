package Chapter2;       //声明程序所在包
import java.util.Scanner;
public class Example2_1 {
	public static void main(String[] args) {
		int x, y, z; 	// 声明三个变量
		System.out.println("请输入两个整数：");
                            // Scanner类表示一个文本扫描器，
                            //它可以扫描从键盘上输入的字符
		Scanner in = new Scanner(System.in); 
                            // 方法nextInt()返回键盘上输入的一个整数
		x = in.nextInt();	y = in.nextInt();	z = x + y;
		System.out.println(x + "+" + y + "=" + z);
	}
}
