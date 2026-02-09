package code6.login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public void loginUi(){
        JFrame frame = new JFrame();
        //设置窗体对象的属性值
        frame.setTitle("Login");//设置窗体标题
        frame.setSize(500, 300);//设置窗体大小，只对顶层容器生效
        frame.setDefaultCloseOperation(3);//设置窗体关闭操作，3表示关闭窗体退出程序
        frame.setLocationRelativeTo(null);//设置窗体相对于另一组间的居中位置，参数null表示窗体相对于屏幕的中央位置
        frame.setResizable(false);//禁止调整窗体大小
        frame.setFont(new Font("宋体",Font.PLAIN,14));//设置字体，显示格式正常，大小

        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,10,10);
        //实例化流式布局类的对象
        frame.setLayout(fl);

        //实例化JLabel标签对象，该对象显示“账号”
        JLabel labname = new JLabel("账号id：");
        labname.setFont(new Font("宋体",Font.PLAIN,14));
        frame.add(labname);

        JTextField text_name = new JTextField();
        Dimension dim1 = new Dimension(300,30);
        text_name.setPreferredSize(dim1);//设置除顶级容器组件以外其他组件的大小
        frame.add(text_name);

        JLabel labpass = new JLabel("密码：");
        labpass.setFont(new Font("宋体",Font.PLAIN,14));
        frame.add(labpass);
        JPasswordField text_password = new JPasswordField();
        //设置大小
        text_password.setPreferredSize(dim1);
        frame.add(text_password);
        JButton button1 = new JButton();
        JButton button2 = new JButton("注册");
        Dimension dim2 = new Dimension(100,30);
        button1.setText("登录");
        button1.setFont(new Font("宋体",Font.PLAIN,14));
        button2.setFont(new Font("宋体",Font.PLAIN,14));
        button1.setSize(dim2);
        button2.setSize(dim2);
        button2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new Register().reg();
                    }
                }
        );

        frame.add(button1);
        frame.add(button2);
        frame.setVisible(true);
        CListener listener = new CListener(frame, text_name, text_password);
        button1.addActionListener(listener);

    }
}
