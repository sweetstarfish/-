package InOutStreamTest;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
public class PrintWriterTest {
	public static void main(String args[]){
		FileOutputStream fos=null;
		PrintWriter pw = null;
	        try {
	            fos = new FileOutputStream("f.txt");	         
	            pw = new PrintWriter(fos);	          
	            for (int i = 1; i <= 255; i++) { 	             
	               System.out.print(i+" ");	             
	                if (i % 50 == 0) { // 每50个数据一行
	                    System.out.println(); // 换行
	                }
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } finally {
	            if (pw != null) {
	                pw.close();
	            }
	        }
	}
}
