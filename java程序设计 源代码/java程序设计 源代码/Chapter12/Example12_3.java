package chapter12;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Example12_3 {
	
	public static void main(String[] args) {
		 class WindowCloseHandler extends WindowAdapter{
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}		
		  Frame myFrame = new Frame("My First Frame");
	      myFrame.setVisible(true); 
		  myFrame.setSize(250,100);  //使一个Frame 可见
		  myFrame.addWindowListener(new WindowCloseHandler());
	}
}
