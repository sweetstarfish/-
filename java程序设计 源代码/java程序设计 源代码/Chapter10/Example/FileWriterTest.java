package InOutStreamTest;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
public class FileWriterTest {
    public static void main(String args[]){
    	FileReader fr=null;
    	FileWriter fw=null;
    	try {
			//1.创建File类的对象，指明读入和写出的文件
			File srcFile = new File("hello.txt");
			File destFile = new File("hello1.txt");
			//2.创建输入流和输出流的对象
			fr = new FileReader(srcFile);
			fw = new FileWriter(destFile);
			//3.数据的读入和写出操作
			char[] cbuf = new char[5];
			int len;//记录每次读入到cbuf数组中的字符的个数
			while ((len = fr.read(cbuf)) != -1) {
				//每次写出len个字符
				fw.write(cbuf, 0, len);
				System.out.print(cbuf);
			} 
		} catch (Exception e) {
			 e.printStackTrace();
        } finally {
            //4.关闭流资源      
            try {
                if(fw != null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    if(fr != null)
                        fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }	
    }
}


