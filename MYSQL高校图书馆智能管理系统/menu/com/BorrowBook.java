package code6.menu.com;
import code6.entity.Book;
import code6.menu.manger.SelectBook;
import code6.utils.SqlUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class BorrowBook {
    public void add(int uid) throws SQLException, ClassNotFoundException {
        JFrame f = new JFrame("借阅书籍");
        JPanel jPanel = new JPanel();
        JTextField tid = new JTextField();
        Dimension dimension = new Dimension(100, 30);
        new SelectBook().comTable();


        JLabel lid= new JLabel("图书id");
        JLabel lgroup= new JLabel("月份");
        lid.setFont(new Font("宋体",Font.PLAIN,14));
        lgroup.setFont(new Font("宋体",Font.PLAIN,14));
        tid.setPreferredSize(dimension);
        JLabel lday= new JLabel("天数(0< and <30)");
        JComboBox<String> box2 = new JComboBox<>();
        for (int i=0;i<30;i++){
            box2.addItem(i+"天");
        }

        JComboBox<String> box1 = new JComboBox<>();
        box1.addItem("0个月");
        box1.addItem("1个月");
        box1.addItem("2个月");
        JButton jbutton = new JButton("添加");
       jbutton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int id = Integer.parseInt(tid.getText());
               System.out.println("书籍ID：" + id);

               int months = Integer.parseInt(box1.getSelectedItem().toString().substring(0, 1));
               int days = Integer.parseInt(box2.getSelectedItem().toString().substring(0, 1));

               int i = 0;

               try {
                   // 检查书籍是否已被借出
                   boolean isRent = SqlUtils.IsRent(uid, id);
                   System.out.println("IsRent: " + isRent);

                   if (isRent) {
                       // 检查是否可以借书
                       boolean canAddRent = SqlUtils.isAddRend(id);
                       System.out.println("isAddRend: " + canAddRent);

                       if (canAddRent) {
                           // 执行借书操作
                           i = SqlUtils.addRent(uid, id, days, months);
                           System.out.println("addRent返回值: " + i);
                       } else {
                           System.out.println("库存不足，无法借书");
                       }
                   } else {
                       System.out.println("该书已被借出");
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               } catch (ClassNotFoundException classNotFoundException) {
                   classNotFoundException.printStackTrace();
               }

               // 根据返回值显示结果
               String res = i > 0 ? "借阅成功" : "借阅失败";
               System.out.println(res);
           }

       });
        jPanel.add(lid);
        jPanel.add(tid);
        jPanel.add(lgroup);
        jPanel.add(box1);
        jPanel.add(lday);
        jPanel.add(box2);
        jPanel.add(jbutton);
        f.add(jPanel);
        f.setSize(700,300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    }