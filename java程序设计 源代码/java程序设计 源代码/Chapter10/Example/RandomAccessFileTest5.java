package InOutStreamTest;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class RandomAccessFileTest5 {
	private static RandomAccessFile raf1;

	public  static void main(String[] args){	 
		try {
		RandomAccessFile raf1 = new RandomAccessFile("hello.txt", "rw");
		//将指针调到角标为3的位置
		raf1.seek(3);
		//保存指针3后面的所有数据到StringBuilder中
		StringBuilder builder = new StringBuilder((int) new File("hello.txt").length());
		byte[] buffer = new byte[50];
		int len;
		while ((len = raf1.read(buffer)) != -1) {
			builder.append(new String(buffer, 0, len));
		}
		//调回指针，写入“xyz”
		raf1.seek(3);
		raf1.write("xyz".getBytes());
		//将StringBuilder中的数据写入到文件中
		raf1.write(builder.toString().getBytes());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		}	 
	}



