package code6.menu;


import code6.menu.manger.AddBook;
import code6.menu.manger.DelBook;
import code6.menu.manger.SelectBook;
import code6.menu.manger.UpdateBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MangerMenu {
    public void ui(int uid) {
        JFrame jframe = new JFrame("管理员个人主页");
        jframe.setBounds(300, 180, 650, 500);
        JPanel jPanel = new JPanel();
        JMenuBar jmenuBar=new JMenuBar();
        JMenu sf = new JMenu("查看所有书籍");
        JMenu af = new JMenu("添加书籍");
        JMenu df = new JMenu("删除书籍");
        JMenu uf = new JMenu("修改书籍");
        JMenuItem d1 = new JMenuItem("查询");
        JMenuItem d2 = new JMenuItem("添加");
        JMenuItem d3 = new JMenuItem("删除");
        JMenuItem d4 = new JMenuItem("修改");
        d1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    new SelectBook().comTable();
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
                new AddBook().add();

            }
        });
        d3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DelBook().del();

            }
        });
        d4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateBook().update();
            }
        });
        af.add(d2);
        df.add(d3);
        sf.add(d1);
        uf.add(d4);

        jmenuBar.add(sf);
        jmenuBar.add(af);
        jmenuBar.add(df);
        jmenuBar.add(uf);
        jPanel.add(jmenuBar);
        jframe.add(jPanel);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
