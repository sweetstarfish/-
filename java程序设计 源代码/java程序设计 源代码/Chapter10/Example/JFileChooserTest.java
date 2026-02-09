package InOutStreamTest;

import java.util.Scanner;
import javax.swing.JFileChooser;
public class JFileChooserTest {
	public static void main(String[] args) throws Exception{
		JFileChooser fileChooser =  new JFileChooser();//创建一个JFileChooser
	    //showOpenDialog（null）显示对话框，返回值是一个int值，或是APPROVE_OPTION  或是   CANCEL_OPTION表示点击open或是cancel
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {     			                                                             
			java.io.File file = fileChooser.getSelectedFile(); //getSelectedFile获取对话框点击的文件			
			Scanner input = new Scanner(file); //为该文件创建一个Scanner
			while(input.hasNext()) { //读取文件
				System.out.println(input.nextLine());				
			}
			input.close();
		}
		else
			System.out.println("No file selected");
	}
}
