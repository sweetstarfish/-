package chapter12;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Example12_5 implements ActionListener{
	private Frame frame;
	private Button  button;
	private TextArea area;
	
	public Example12_5(){
		frame = new Frame("EventTest");			
		button = new Button("click");		
		area  = new TextArea(10,20);	
	}
	private void init(){
		frame.add(button, BorderLayout.NORTH);
		frame.add(area, BorderLayout.CENTER);
	}	
	public void showMe(){
		init();
		
		//为事件源注册事件监听器--匿名内部类对象
		button.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {//ActionEvent为事件对象
				area.append(e.getActionCommand()+"按钮被点击"+"\n" );		
			}			
		});
		
		frame.setBounds(50,50,200,150);
		frame.setVisible(true);		
	}
	
	//本类做监听,实现事件处理器
	public void actionPerformed(ActionEvent e) {//ActionEvent为事件对象
		area.append("按钮被点击"+"\n" );		
	}
	
	public static void main(String[] args){
		new Example12_5().showMe();
	}
}
