package code6.menu.com;

import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class ContinueBook {
    public void con(int uid){
        JFrame f = new JFrame("续借图书");
        JPanel jPanel = new JPanel();
        JLabel lid= new JLabel("图书id");
        lid.setFont(new Font("宋体",Font.PLAIN,14));
        JTextField tid = new JTextField();
        Dimension dimension = new Dimension(100, 30);
        tid.setPreferredSize(dimension);
        JButton jbutton = new JButton("续借");
        JComboBox<String> box1 = new JComboBox<>();
        for (int i=1;i<=30;i++){
            box1.addItem(i+"天");
        }

        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                int days= Integer.parseInt(box1.getSelectedItem().toString().substring(0,1));
                int bid= Integer.parseInt(tid.getText());
                try {
                    i = SqlUtils.contineDays(bid, uid, days);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                if(i>0){
                    System.out.println("续借成功");
                }
                else {
                    System.out.println("续借失败");
                }
            }
        });
        jPanel.add(lid);
        jPanel.add(tid);
        jPanel.add(box1);
        jPanel.add(jbutton);
        f.add(jPanel);
        f.setSize(600,200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    }