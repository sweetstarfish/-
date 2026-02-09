package com.yiibai.file;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
public class AppendToFileExample 
{
    public static void main( String[] args )
    { 
     try{
      String data = "购车就上东风日产网站,首付1成起,购车0压力,提供全方位报价和专业的售前,售后服务.马上登录东风日产网站,了解更多优惠!汽车最新价格";
      File file =new File("javaio-appendfile.txt");
      //if file doesnt exists, then create it
      if(!file.exists()){
       file.createNewFile();
      }
      //true = append file
      FileWriter fileWritter = new FileWriter(file.getName(),true);
             BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
             bufferWritter.write(data);
             bufferWritter.close();
         System.out.println("Done");
     }catch(IOException e){
      e.printStackTrace();
     }
    }
}
