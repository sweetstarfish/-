package code6.login;


import code6.entity.User;
import code6.menu.Common;
import code6.menu.MangerMenu;
import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CListener implements ActionListener {
    JFrame login;
    JTextField text_name;
    JPasswordField text_passwd;

    public CListener() {
    }

    public CListener(JFrame login, JTextField text_name, JPasswordField text_passwd) {
        this.login = login;
        this.text_name = text_name;
        this.text_passwd = text_passwd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Dimension dim3 = new Dimension(300, 30);

        JFrame login2 = new JFrame();
        login2.setSize(400, 200);
        login2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 用常量替代数字
        login2.setLocationRelativeTo(null);
        login2.setFont(new Font("宋体", Font.PLAIN, 14));
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();

        String userIdText = text_name.getText();
        String passwordText = text_passwd.getText();

        if (userIdText.isEmpty() || passwordText.isEmpty()) {
            JOptionPane.showMessageDialog(login, "用户名或密码不能为空", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int userId = Integer.parseInt(userIdText);
            System.out.println("查询用户ID: " + userId);  // 打印出正在查询的用户ID
            User user = SqlUtils.getUserById(userId);

            if (user == null) {
                JOptionPane.showMessageDialog(login, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String passwd = user.getPasswd();
            if (passwordText.equals(passwd)) {
                JLabel message = new JLabel("登陆成功！");
                message.setFont(new Font("宋体", Font.PLAIN, 14));
                message.setPreferredSize(dim3);
                jp1.add(message);
                login2.add(jp1, BorderLayout.CENTER);
                login2.setResizable(false);
                login2.setVisible(true);
                login.dispose();

                if (user.getIsmanger() == 1) {
                    new MangerMenu().ui(user.getId());
                } else {
                    new Common().ui(user.getId());
                }

            } else {
                JLabel message = new JLabel("账号或密码错误");
                message.setFont(new Font("宋体", Font.PLAIN, 14));
                message.setPreferredSize(dim3);
                jp1.add(message);
                login2.add(jp1, BorderLayout.CENTER);

                JButton close = new JButton("确定");
                close.setFont(new Font("宋体", Font.PLAIN, 14));
                close.setPreferredSize(dim3);
                jp2.add(close);
                login2.add(jp2, BorderLayout.SOUTH);

                close.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        login2.dispose();
                    }
                });

                login2.setResizable(false);
                login2.setVisible(true);
                login.dispose();
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(login, "请输入有效的数字ID", "输入错误", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(login, "数据库错误，请稍后重试", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
