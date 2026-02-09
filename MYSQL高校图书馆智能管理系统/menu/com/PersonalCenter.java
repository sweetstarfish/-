package code6.menu.com;
import java.util.Arrays;

import code6.entity.Book;
import code6.entity.Rent;
import code6.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PersonalCenter {
    public void center(int uid) throws SQLException, ClassNotFoundException {
            JFrame f = new JFrame("个人中心");
            f.setSize(800, 300);
            f.setLocation(200, 200);
            f.setLayout(new BorderLayout());

            String[] th = new String[]{ "书籍id", "书籍名称","类型", "借入时间","归还日期"};
            //获取数据
            List<Map> centosByUid = SqlUtils.getCentosByUid(uid);
            HashMap<String, String> map = new HashMap<>();
            // 调试输出，检查数据
            System.out.println("返回列表中元素数量: " + centosByUid.size());
            for (int i = 0; i < centosByUid.size(); i++) {
                System.out.println(centosByUid.get(i));  // 打印每条记录
            }

            // 如果没有数据，显示提示信息
            if (centosByUid.isEmpty()) {
                JOptionPane.showMessageDialog(f, "没有找到借书记录！");
                return;
            }
            String[][] td=new String[centosByUid.size()][];
            for (int i=0;i<td.length;i++) {
                map= (HashMap<String, String>) centosByUid.get(i);
                td[i] = new String[5];
                td[i][0] = map.get("bid");
                td[i][1] = map.get("bname");
                td[i][2] = map.get("type");
                td[i][3] = map.get("btime");
                td[i][4] = map.get("days");
                // 打印每行数据，确保数据被正确填充
                System.out.println(Arrays.toString(td[i]));
            }
            JTable t = new JTable(td, th);
            JScrollPane sp = new JScrollPane(t);
            f.add(sp, BorderLayout.CENTER);

            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
    }
    public static void main(String[] args) {
        try {
            new PersonalCenter().center(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
