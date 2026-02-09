package code6.menu.com;

import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SendBook {
    public void send(){
        JFrame f = new JFrame("归还图书");
        JPanel jPanel = new JPanel();
        JLabel lid= new JLabel("图书id");
        lid.setFont(new Font("宋体",Font.PLAIN,14));
        JTextField tid = new JTextField();
        Dimension dimension = new Dimension(100, 30);
        tid.setPreferredSize(dimension);
        JButton jbutton = new JButton("归还");
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                try {
                    i= SqlUtils.delBook(Integer.parseInt(tid.getText()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                if(i>0){
                    System.out.println("归还成功");
                }
                else {
                    System.out.println("归还失败");
                }
            }
        });
        jPanel.add(lid);
        jPanel.add(tid);

        jPanel.add(jbutton);
        f.add(jPanel);
        f.setSize(600,200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
