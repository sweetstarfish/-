package code6.menu.manger;
import code6.entity.Book;
import code6.utils.SqlUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class UpdateBook {
    public void update(){
        JFrame f = new JFrame("修改书籍");
        JPanel jPanel = new JPanel();
        JTextField textfield = new JTextField();
        JTextField tnum = new JTextField();
        JTextField tid = new JTextField();
        JTextField tbrrow = new JTextField();
        Dimension dimension = new Dimension(100, 30);
        JLabel lbid = new JLabel("要修改的图书id");
        JLabel lname= new JLabel("书籍名称");
        JLabel lnum= new JLabel("总数量");
        JLabel lbrrow= new JLabel("借出数量");
        JLabel lgroup= new JLabel("书籍类型");
        lbid.setFont(new Font("宋体",Font.PLAIN,14));
        lname.setFont(new Font("宋体",Font.PLAIN,14));
        lnum.setFont(new Font("宋体",Font.PLAIN,14));
        lgroup.setFont(new Font("宋体",Font.PLAIN,14));
        lbrrow.setFont(new Font("宋体",Font.PLAIN,14));
        tnum.setPreferredSize(dimension);
        tbrrow.setPreferredSize(dimension);
        textfield.setPreferredSize(dimension);
        tid.setPreferredSize(dimension);
        JButton jbutton = new JButton("修改");
        JComboBox<String> box = new JComboBox<>();
        box.addItem("小说");
        box.addItem("教材");
        box.addItem("科普");
        box.addItem("其他");
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bid=0;
                String bname=null;
                int allnum=0;
                int brrow=0;
                String type=null;
                bid= Integer.parseInt(tid.getText());
                bname=textfield.getText();
                allnum= Integer.parseInt(tnum.getText());
                brrow= Integer.parseInt(tbrrow.getText());
                type=box.getSelectedItem().toString();
                Book book = new Book();
                book.setBid(bid);
                book.setBorrownum(brrow);
                book.setType(type);
                book.setName(bname);
                book.setAllnum(allnum);
                System.out.println(book);
                int i=0;
                try {
                    SqlUtils.upBook(book);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                if(i>0){
                    System.out.println("修改成功");
                }else
                {
                    System.out.println("修改失败");
                }
            }
        });
        jPanel.add(lbid);
        jPanel.add(tid);
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
    public static void main(String[] args) {
        new UpdateBook().update();
    }
}
