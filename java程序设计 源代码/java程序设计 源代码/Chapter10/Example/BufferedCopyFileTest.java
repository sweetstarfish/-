package InOutStreamTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
public class BufferedCopyFileTest {
     public static void main(String args[]){
    	 FileReader fr=null;
    	 FileWriter fw=null;
    	 BufferedReader br=null;
    	 BufferedWriter bw=null;
    	 try {
			 fr=new FileReader("file1.txt");
			 fw=new FileWriter("file2.txt");
			 br=new BufferedReader(fr);
			 bw=new BufferedWriter(fw);
			 long start = System.currentTimeMillis();
			 String data;
	         while((data = br.readLine())!= null){
	                bw.write(data + "\n");//data中不包含换行符
	                }
	         long end = System.currentTimeMillis();
	         System.out.println("缓冲流复制文件消耗时间为："+(end-start));
	         long start1 = System.currentTimeMillis();
	         int data1;
	         while((data1 = fr.read())!=-1){
	                fw.write(data);
	                }
	         long end1 = System.currentTimeMillis();
	         System.out.println("文件流复制文件消耗时间为："+(end1-start1));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 //关闭资源
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
			if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
     }
}


