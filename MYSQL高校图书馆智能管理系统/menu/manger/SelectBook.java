package code6.menu.manger;

import code6.entity.Book;
import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
public class SelectBook {
    public static void comTable() throws SQLException, ClassNotFoundException {
        JFrame f = new JFrame("图书管理");
        f.setSize(400, 300);
        f.setLocation(200, 200);
        f.setLayout(new BorderLayout());

        //表头
        String[] th = new String[]{ "书籍id", "书籍名称","类型", "总数","借出数量"};
        //获取所有书籍数据
        List<Book> all = SqlUtils.getAll();
        if (all == null || all.isEmpty()) {
            JOptionPane.showMessageDialog(f, "没有可显示的书籍数据！");
            return;
        }
        //创建数据二维数组
        String[][] td = new String[all.size()][th.length];  // th.length 表示列数

        //Book book = new Book();
        // 填充数据
        for (int i = 0; i < all.size(); i++) {
            Book book = all.get(i);

            // 确保 book 对象不为 null
            if (book != null) {
                td[i][0] = book.getBid() != null ? String.valueOf(book.getBid()) : "";
                td[i][1] = book.getName() != null ? book.getName() : "";
                td[i][2] = book.getType() != null ? book.getType() : "";
                td[i][3] = book.getAllnum() != null ? String.valueOf(book.getAllnum()) : "0";
                td[i][4] = book.getBorrownum() != null ? String.valueOf(book.getBorrownum()) : "0";
            }

        }

        //创建JTable 并填充数据
        JTable t = new JTable(td, th);
        JScrollPane sp = new JScrollPane(t);
        f.add(sp, BorderLayout.CENTER);
        //设置JFrame可见
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}