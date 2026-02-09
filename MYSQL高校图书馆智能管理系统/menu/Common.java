package code6.menu;

import code6.menu.com.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Common {
    public void ui(int uid) {
        JFrame jframe = new JFrame("用户主页");
        jframe.setBounds(300, 180, 650, 500);
        JPanel jPanel = new JPanel();
        JMenuBar jmenuBar=new JMenuBar();
        JMenu sf = new JMenu("借阅图书");
        JMenu af = new JMenu("归还图书");
        JMenu df = new JMenu("个人中心");
        JMenu uf = new JMenu("续借图书");
        JMenu cf = new JMenu("评论图书");
        JMenuItem d1 = new JMenuItem("借阅");
        JMenuItem d2 = new JMenuItem("归还");
        JMenuItem d3 = new JMenuItem("租借记录");
        JMenuItem d4 = new JMenuItem("续借");
        JMenuItem d5 = new JMenuItem("评论");
        d1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new BorrowBook().add(uid);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        d2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SendBook().send();

            }
        });
        d3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PersonalCenter().center(uid);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

            }
        });
        d4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContinueBook().con(uid);
            }
        });
        d5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CommentBook().comment(uid);
            }
        });
        af.add(d2);
        df.add(d3);
        sf.add(d1);
        uf.add(d4);
        cf.add(d5);
        jmenuBar.add(sf);
        jmenuBar.add(af);
        jmenuBar.add(df);
        jmenuBar.add(uf);
        jmenuBar.add(cf);
        jPanel.add(jmenuBar);
        jframe.add(jPanel);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Common().ui(1);
    }

}
