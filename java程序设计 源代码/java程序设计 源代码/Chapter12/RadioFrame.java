package chapter12;
import javax.swing.*;
import java.awt.event.*;
public class RadioFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel lab = new JLabel("目的地：");
	private JRadioButton rb1 = new JRadioButton("北京");
	private JRadioButton rb2 = new JRadioButton("上海");
	private JRadioButton rb3 = new JRadioButton("武汉");
	private ButtonGroup bg = new ButtonGroup();
	private JTextField tf = new JTextField(20);
	private JPanel pan = new JPanel();
	public RadioFrame(){
		this.setTitle("RadioFrame");
		this.setBounds(100, 200, 300, 120);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
		this.setVisible(true);
	}
	public void initialize(){
		pan.add(lab);
		pan.add(rb1);
		pan.add(rb2);
		pan.add(rb3);
		bg.add(rb1);
		bg.add(rb2);
		bg.add(rb3);
		tf.setEditable(false);
		pan.add(tf);
		this.add(pan);
		rb1.addActionListener(this);                      //单选按钮添加动作监听器
		rb2.addActionListener(this);                     //单选按钮添加动作监听器
		rb3.addActionListener(this);  
	}
	public void actionPerformed(ActionEvent e){                    //动作事件处理方法
		StringBuffer sb = new StringBuffer("您选择的目的地：");
		if (rb1.isSelected()){
			sb.append(rb1.getText() + " ");
		}
		else if (rb2.isSelected()){
			sb.append(rb2.getText() + " ");
		}
		else if (rb3.isSelected()){
			sb.append(rb3.getText() + " ");
		}
		tf.setText(sb.toString());
	}
}
