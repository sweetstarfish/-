package chapter12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MouseTestFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel lab = new JLabel("测试区：");
	private JPanel panTest = new JPanel();
	private JTextField tf = new JTextField();
	public MouseTestFrame(){                            //构造方法
		this.setTitle("测试鼠标按键");
		this.setBounds(100, 200, 300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();                                    //调用初始化方法
		this.setVisible(true);
	}
	
	public void initialize(){                          //初始化方法
		this.add(lab, BorderLayout.WEST);
		this.add(panTest, BorderLayout.CENTER);
		this.add(tf, BorderLayout.SOUTH);
		panTest.setBackground(Color.GRAY);              //设测试面板灰色
		panTest.addMouseListener(new MouseHandler());   //面板添加鼠标事件监听器
	}
	
	//继承鼠标适配器类的事件监听(处理)类（内部类）：
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1){
				tf.setText("单击了鼠标左键");
			}
			if(e.getButton() == MouseEvent.BUTTON2){
				tf.setText("单击了鼠标中间的滚轮键");
			}
			if(e.getButton() == MouseEvent.BUTTON3){
				tf.setText("单击了鼠标右键");
			}
		}
	}

	/*
	//实现鼠标监听接口的事件监听(处理)类（内部类）：
	private class MouseHandler implements MouseListener {
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1){
				tf.setText("单击了鼠标左键");
			}
			if(e.getButton() == MouseEvent.BUTTON2){
				tf.setText("单击了鼠标中间的滚轮键");
			}
			if(e.getButton() == MouseEvent.BUTTON3){
				tf.setText("单击了鼠标右键");
			}
		}
		public void mousePressed(MouseEvent e){ }
		public void mouseReleased(MouseEvent e){ }
		public void mouseEntered(MouseEvent e){ }
		public void mouseExited(MouseEvent e){ }
	}
	*/
}
