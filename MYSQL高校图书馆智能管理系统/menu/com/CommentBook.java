package code6.menu.com;

import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class CommentBook {
    public void comment(int uid){
        JFrame f = new JFrame("图书评价");
        JPanel jPanel = new JPanel();
        JLabel lid= new JLabel("图书的id");
        lid.setFont(new Font("宋体",Font.PLAIN,14));
        JTextField tid = new JTextField();
        Dimension dimension = new Dimension(100, 30);
        tid.setPreferredSize(dimension);
        JLabel lcon = new JLabel("评价内容");
        JTextArea tcon = new JTextArea(5,20);
        JTextArea tta = new JTextArea(10, 20);
        tta.setFont(new Font("宋体",Font.PLAIN,14));
        tta.setVisible(false);
//        JTextField tcon = new JTextField();
        JButton jbutton = new JButton("发表自己观点");
        tcon.setFont(new Font("宋体",Font.PLAIN,14));
//        lcon.setFont(new Font("宋体",Font.PLAIN,14));
        tcon.setPreferredSize(dimension);


        JButton jbutton2 = new JButton("查看ta评价");

        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                int bid= Integer.parseInt(tid.getText());
                try {
                    if(SqlUtils.IsRent(uid,bid)){
                        i=SqlUtils.addCom(tcon.getText(),bid,uid);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                if(i>0){
                    System.out.println("评价成功");
                }
                else {
                    System.out.println("评价失败");
                }
            }
        });


        jbutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bid= Integer.parseInt(tid.getText());
                try {
                    System.out.println(SqlUtils.IsRent(uid, bid));

                        List<String> com = SqlUtils.getComById(bid);
                        tta.setText("");
                        for (int i=0;i<com.size();i++){
                            String con=com.get(i);
                            tta.append(con+"\n");
                        }
                        if(com.size()==0){
                            tta.setText("当前书籍暂无评价");
                        }


                        tta.setVisible(true);



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }


            }
        });
        jPanel.add(lid);
        jPanel.add(tid);
        jPanel.add(lcon);
        jPanel.add(tcon);
        jPanel.add(jbutton2);
        jPanel.add(jbutton);
        jPanel.add(tta);
        f.add(jPanel);
        f.setSize(600,200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new CommentBook().comment(1);
    }
}
