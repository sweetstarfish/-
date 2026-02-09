package chapter12;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Login {

	public static void main(String[] args) {
		JFrame frame = new JFrame("超市管理系统");
		//定义登录界面所需要的组件
		JLabel lbl_title = new JLabel("超市管理系统");
		JLabel lbl_name = new JLabel("用户名：");
		JLabel lbl_pwd = new JLabel("密码：");
		JTextField txt_name = new JTextField(16);
		JPasswordField txt_pwd = new JPasswordField(16);
		txt_pwd.setEchoChar('*');
		JButton btn_OK = new JButton("确定");
		JButton btn_cancel = new JButton("取消");
		//frame设置为边界布局，设置存放在frame上north、center、south位置的panel
		JPanel panel_north = new JPanel();
		panel_north.add(lbl_title);
		
		JPanel panel_name = new JPanel();
		panel_name.add(lbl_name);
		panel_name.add(txt_name);
		JPanel panel_pwd = new JPanel();
		panel_pwd.add(lbl_pwd);
		panel_pwd.add(txt_pwd);
		JPanel panel_center = new JPanel();
		panel_center.setLayout(new GridLayout(2,1));
		panel_center.add(panel_name);
		panel_center.add(panel_pwd);
		
		JPanel panel_south = new JPanel();
		panel_south.add(btn_OK);
		panel_south.add(btn_cancel);
		//将panel放在frame相应的位置上
		frame.setLayout(new BorderLayout());
		frame.add(BorderLayout.NORTH,panel_north);
		frame.add(BorderLayout.CENTER,panel_center);
		frame.add(BorderLayout.SOUTH,panel_south);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 180);
		frame.setVisible(true);
	}

}
