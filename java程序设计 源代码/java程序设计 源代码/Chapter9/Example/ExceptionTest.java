package EXception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionTest {
	public static void main(String[] args){
		FileInputStream fis=null;
		try {
			fis = new FileInputStream("hello.txt");
			int b;
			b = fis.read();
			while (b != -1) {
			System.out.print((char) b);
			b = fis.read();
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件找不到，出现异常");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("文件读写出现异常");
			e.printStackTrace();
		}finally{	
				try {
					fis.close();
				} catch (IOException e) {
					System.out.println("文件关闭出现异常");
					e.printStackTrace();
				}
		}		
	}
}
