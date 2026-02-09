package code6.login;

import code6.entity.Book;
import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static code6.utils.SqlUtils.RegisterUser;

public class Register {

    public static void reg() {
        JFrame f = new JFrame("注册普通用户");
        JPanel jPanel = new JPanel();

        JTextField tname = new JTextField();
        JTextField tpasswd = new JTextField();
        Dimension dimension = new Dimension(100, 30);
        JLabel lname = new JLabel("用户名");
        JLabel lpassd = new JLabel("密码");

        lname.setFont(new Font("宋体", Font.PLAIN, 14));
        lpassd.setFont(new Font("宋体", Font.PLAIN, 14));

        tname.setPreferredSize(dimension);
        tpasswd.setPreferredSize(dimension);

        JButton jbutton = new JButton("注册");

        // 注册按钮的事件处理
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bname = tname.getText();
                String passwd = tpasswd.getText();

                // 1. 校验用户名和密码是否为空
                if (bname.isEmpty() || passwd.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "用户名和密码不能为空！");
                    return;
                }

                // 2. 尝试注册
                int i = 0;
                try {
                    i = RegisterUser(bname, passwd); // 调用数据库注册方法
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(f, "数据库错误：" + throwables.getMessage());
                    return;
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                    JOptionPane.showMessageDialog(f, "数据库驱动未找到：" + classNotFoundException.getMessage());
                    return;
                }

                // 3. 根据注册结果显示不同消息
                if (i > 0) {
                    JOptionPane.showMessageDialog(f, "注册成功！");
                    System.out.println("注册成功");
                } else {
                    JOptionPane.showMessageDialog(f, "注册失败，请重试！");
                    System.out.println("注册失败");
                }
            }
        });

        // 布局设置
        jPanel.setLayout(new GridLayout(3, 2));  // 使用 GridLayout 来控制表单布局
        jPanel.add(lname);
        jPanel.add(tname);
        jPanel.add(lpassd);
        jPanel.add(tpasswd);
        jPanel.add(jbutton);

        // JFrame 配置
        f.add(jPanel);
        f.setSize(700, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    // 主函数来运行注册窗口
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            reg(); // 调用注册窗口
        });
    }
}









