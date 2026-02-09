package code6.menu.manger;

import code6.entity.Book;
import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddBook {
    public static void add(){
        JFrame f = new JFrame("添加书籍");
        JPanel jPanel = new JPanel();
        JTextField textfield = new JTextField();
        JTextField tnum = new JTextField();
        JTextField tbrrow = new JTextField();
        Dimension dimension = new Dimension(100, 30);
        JLabel lname= new JLabel("书籍名称");
        JLabel lnum= new JLabel("总数量");
        JLabel lbrrow= new JLabel("借出数量");
        JLabel lgroup= new JLabel("书籍类型");
        lname.setFont(new Font("宋体",Font.PLAIN,14));
        lnum.setFont(new Font("宋体",Font.PLAIN,14));
        lgroup.setFont(new Font("宋体",Font.PLAIN,14));
        lbrrow.setFont(new Font("宋体",Font.PLAIN,14));
        tnum.setPreferredSize(dimension);
        tbrrow.setPreferredSize(dimension);
        textfield.setPreferredSize(dimension);
        JButton jbutton = new JButton("添加");
        JComboBox<String> box = new JComboBox<>();
        box.addItem("小说");
        box.addItem("教材");
        box.addItem("科普");
        box.addItem("其他");
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bname=null;
                int allnum=0;
                int brrow=0;
                String type=null;
                bname=textfield.getText();
                allnum= Integer.parseInt(tnum.getText());
                brrow= Integer.parseInt(tbrrow.getText());
                type=box.getSelectedItem().toString();
                Book book = new Book(bname,null,allnum,brrow,type);
                int i=0;
                try {
                   i=SqlUtils.addBook(book);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                if(i>0){
                    System.out.println("添加成功");
                }else
                {
                    System.out.println("添加失败");
                }
            }
        });

        jPanel.add(lname);
        jPanel.add(textfield);
        jPanel.add(lnum);
        jPanel.add(tnum);
        jPanel.add(lbrrow);
        jPanel.add(tbrrow);
        jPanel.add(lgroup);
        jPanel.add(box);
        jPanel.add(jbutton);
        f.add(jPanel);
        f.setSize(700,300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }







}
