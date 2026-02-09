package chapter12;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Example12_4 {
	private Frame frame;
	private TextArea area;
	private TextField field;
	private Button  buttonEnter;
	
	public Example12_4(){
		frame = new Frame("chat");

		area  = new TextArea(10,30);
		area.setFont(new Font("Times New Roman",Font.BOLD,14));
		area.setEditable(false);			
		
		field = new TextField(26);
		buttonEnter = new Button("发送");
	}
	private void init(){		
		Panel panelCenter = new Panel();
		panelCenter.add(area);	
		
		Panel panelSouth = new Panel();
		panelSouth.add(field);
		panelSouth.add(buttonEnter);
		
		frame.add(panelCenter, BorderLayout.CENTER);
		frame.add(panelSouth, BorderLayout.SOUTH);
	}
	private void addEventHandler(){
		//为事件源注册事件监听器
		buttonEnter.addActionListener(new TextButtonActionHandler());
		field.addActionListener(new TextButtonActionHandler());
		
	}
	
	public void showMe(){
		init();
		addEventHandler();
		frame.pack();
		frame.setVisible(true);
	}
	//成员内部类做监听器
	private class TextButtonActionHandler implements ActionListener{	
		public void actionPerformed(ActionEvent e) {			
			area.append(field.getText()+"\n" );	
			field.setText("");
		}
	}
	
	public static void main(String[] args) {
		new Example12_4().showMe();

	}

}
