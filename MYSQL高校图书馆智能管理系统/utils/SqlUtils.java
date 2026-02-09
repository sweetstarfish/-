package code6.utils;
import code6.entity.Book;
import code6.entity.Rent;
import code6.entity.User;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class SqlUtils {
    public static Connection getCon() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "040811");
        return con;
    }
    public static int AutoId() {
        int orderId = UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId;
        return orderId;
    }

    public static User getUserById(int id) throws SQLException, ClassNotFoundException {
        System.out.println("尝试使用获取用户ID: " + id);
        Connection con = getCon();
        Statement statement =  con.createStatement();
        String sql = "SELECT * from `user` where uid=" + id;
        System.out.println("正在执行查询: " + sql);  // 打印出执行的查询 SQL
        ResultSet resultSet = statement.executeQuery(sql);
        //System.out.println(resultSet.next());
        // 检查是否有数据行
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt(1));        // 获取第1列，假设是ID
            user.setPasswd(resultSet.getString(2)); // 获取第2列，假设是密码
            user.setUname(resultSet.getString(3));  // 获取第3列，假设是用户名
            user.setIsmanger(resultSet.getInt(4));  // 获取第4列，假设是是否管理员
            statement.close();
            con.close();
            return user;
        } else {
            // 如果没有找到该用户，返回 null 或抛出异常
            statement.close();
            con.close();
            return null;  // 或者抛出自定义异常，具体依需求决定
        }
    }

    public static List<Book> getAll() throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        Statement statement =  con.createStatement();
        String sql = "SELECT * FROM book";
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<Book> books = new ArrayList<>();
        //Book book = new Book();
        while (resultSet.next()) {
            Book book = new Book();
            book.setName(resultSet.getString("bname"));
            book.setBid(resultSet.getInt("bid"));
            book.setAllnum(resultSet.getInt("allnum"));
            book.setBorrownum(resultSet.getInt("borrownum"));
            book.setType(resultSet.getString("type"));


            // 将 book 对象添加到 books 列表
            books.add(book);
        }
      statement.close();
        con.close();

        return books;
    }

    public static int addBook(Book book) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        String sql = "INSERT book VALUES(?,?,?,?,?);";
        PreparedStatement prepareStatement = con.prepareStatement(sql);
        prepareStatement.setString(1, book.getName());
        prepareStatement.setInt(2, AutoId());
        prepareStatement.setInt(3, book.getAllnum());
        prepareStatement.setInt(4, book.getBorrownum());
        prepareStatement.setString(5, book.getType());
        int i = prepareStatement.executeUpdate();
        prepareStatement.close();
        con.close();
        return i;
    }
    public static int delBook(int bid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        String sql="DELETE from book WHERE bid=?";
        PreparedStatement prepareStatement = con.prepareStatement(sql);
        prepareStatement.setInt(1,bid);
        //返回删除操作影响的行数 如果删除成功并且该书籍 ID 存在，则返回 1，如果没有找到对应的书籍，返回 0
        return prepareStatement.executeUpdate();
    }

    //更新书籍的相关信息
    public static int upBook(Book book) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        String sql="UPDATE book SET bname=?,allnum=?,borrownum=?,type=? WHERE bid=?";
        PreparedStatement prepareStatement = con.prepareStatement(sql);
        prepareStatement.setString(1,book.getName());
        prepareStatement.setInt(2,book.getAllnum());
        prepareStatement.setInt(3,book.getBorrownum());
        prepareStatement.setString(4,book.getType());
        prepareStatement.setInt(5,book.getBid());
        int i = prepareStatement.executeUpdate();
        prepareStatement.close();
        con.close();
        return i;
    }

    public static int addRent(int uid,int bid,int days,int month) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        PreparedStatement prepareStatement = null;
        PreparedStatement updateStatement = null;
        //设置 rent 表的第一列（假设是租借记录的 ID）为自增ID
        Statement statement = null;

        try {
            con.setAutoCommit(false); // 开始事务

            // 构建插入租借记录的 SQL 语句
            String sql = "INSERT INTO rent VALUES(?,?,?,?,?)";
            prepareStatement = con.prepareStatement(sql);
            prepareStatement.setInt(1, AutoId());  // 假设AutoId()是获取自增ID的函数

            // 设置借书实时间
            Date date = new Date();
            //获取当前日期和时间，并将其格式化
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //设置租借记录的开始时间为当前时间
            String sbegin = format.format(date);
            prepareStatement.setString(2, sbegin);

            // 设置归还时间（借书时长加上月数）
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, month);
            Date temptime = cal.getTime();

            // 计算结束日期（借书时长加上天数）
            //增加指定的 month（月份）到当前日期 这样得到的 temptime 即为归还时间
            cal.setTime(temptime);
            cal.add(Calendar.DAY_OF_MONTH, days);
            Date endDate = cal.getTime();
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            String endday = format2.format(endDate);
            prepareStatement.setString(3, endday);

            prepareStatement.setInt(4, uid);
            prepareStatement.setInt(5, bid);

            // 更新书籍的借阅数量
            String sql2 = "UPDATE book SET borrownum = borrownum + 1 WHERE bid = ?";
            updateStatement = con.prepareStatement(sql2);
            updateStatement.setInt(1, bid);
            int i1 = updateStatement.executeUpdate();

            // 执行插入租借记录
            int i = prepareStatement.executeUpdate();

            // 如果两个操作都成功，提交事务
            if (i == 1 && i1 == 1) {
                con.commit();//显式调用
                return 1;  // 成功
            } else {
                con.rollback();  // 如果有任何操作失败，回滚事务
                return 0;  // 失败
            }
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();  // 发生异常时回滚事务
            }
            e.printStackTrace();  // 打印错误信息
            return 0;  // 失败
        } finally {
            // 关闭连接和资源
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            if (updateStatement != null) {
                updateStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static Boolean isAddRend(int bid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();//连接数据库
        String sql = "SELECT allnum, borrownum FROM book WHERE bid = ?";
        //防止 SQL 注入并提高查询效率
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        //设置为 SQL 语句中的第一个占位符 ?  这保证了 SQL 查询只会查找指定 bid 的书籍信息
        preparedStatement.setInt(1, bid);
        //查询并返回结果集 resultSet。resultSet 将包含查询返回的数据
        ResultSet resultSet = preparedStatement.executeQuery();

        //检查查询结果中是否存在数据 如果存在，调用 next() 会移动到第一行数据并返回 true
        if (resultSet.next()) {
            int all = resultSet.getInt("allnum");
            int rent = resultSet.getInt("borrownum");
            resultSet.close();
            preparedStatement.close();
            con.close();
            return all > rent;  // 如果库存大于借阅数，表示可以借书
        } else {
            resultSet.close();
            preparedStatement.close();
            con.close();
            return false;
        }
    }
    //获取所有用户租借记录
    public static List<Rent> getAllRentByUid(int uid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();//数据库连接
        String sql = "SELECT * FROM rent WHERE uid = ?";
        //止 SQL 注入并提高查询效率
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        //查询条件中的 uid 被替换为传入的实际值
        preparedStatement.setInt(1, uid);
        ResultSet resultSet = preparedStatement.executeQuery();

        //创建一个空的 List 集合，用于存储所有查询到的 Rent 对象
        List<Rent> rents = new ArrayList<>();
        //如果结果集存在下一行记录，返回 true，并将 resultSet 的指针移到下一行
        while (resultSet.next()) {
            Rent rent = new Rent();
            rent.setRid(resultSet.getInt(1));
            rent.setBtime(resultSet.getString(2));
            rent.setDays(resultSet.getString(3));
            rent.setUid(resultSet.getInt(4));
            rent.setBid(resultSet.getInt(5));
            //当前的 Rent 对象添加到 rents 列表中
            rents.add(rent);
        }

        resultSet.close();
        preparedStatement.close();
        con.close();
        return rents;
    }
    public static Book getBookByBid(int bid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        String sql="SELECT * from book where bid="+bid;
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Book book = new Book();
        while (resultSet.next()){
            book.setName(resultSet.getString(1));
            book.setBid(resultSet.getInt(2));
            book.setAllnum(resultSet.getInt(3));
            book.setBorrownum(resultSet.getInt(4));
            book.setType(resultSet.getString(5));
        }
    return book;
    }
    public static List<Map> getCentosByUid(int uid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        Statement statement = con.createStatement();
        String sql="SELECT bname,b.bid,type,btime,days from book b ,rent r WHERE b.bid=r.bid and r.uid="+uid;
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Map> maps = new ArrayList<>();
        while (resultSet.next()){
            HashMap<String, String> map = new HashMap<>();
            map.put("bname",resultSet.getString(1));
            map.put("bid", String.valueOf(resultSet.getInt(2)));
            map.put("type",resultSet.getString(3));
            map.put("btime",resultSet.getString(4));
            map.put("days",resultSet.getString(5));
            maps.add(map);
        }
        return maps;
    }
    public static Boolean IsRent(int uid,int bid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        String sql="SELECT bid from rent WHERE uid="+uid;
        Statement statement = con.createStatement();
        ArrayList<Object> list = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            list.add(resultSet.getInt(1));
        }
        System.out.println(list);
        for (int i=0;i<list.size();i++){
            if(list.get(i).equals(bid)){
                return true;
            }
        }
        return false;
    }
    public static Boolean decRend(int uid,int bid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        con.setAutoCommit(false);
        String sql="DELETE from rent WHERE uid=? and bid=?";
        PreparedStatement prepareStatement = con.prepareStatement(sql);
        prepareStatement.setInt(1,uid);
        prepareStatement.setInt(2,bid);
        int i =0;
        String sql2="UPDATE book set borrownum=borrownum-1 where bid="+bid;
        Statement statement = con.createStatement();
        int i1 =0;
        try {  i=prepareStatement.executeUpdate();
             i1=statement.executeUpdate(sql2);
             con.commit();
        }
        catch (Exception e){
            System.out.println("失败");
            con.rollback();
        }
        return (i1>0 &&i>0)? true:false;
    }
    public static int contineDays(int bid,int uid,int days) throws SQLException, ClassNotFoundException, ParseException {
        Connection con = getCon();
        String sql1="SELECT days from rent WHERE uid="+uid+" and bid="+bid;
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql1);
        // 确保ResultSet有数据
        if (!resultSet.next()) {
            // 如果没有数据，返回0表示失败或处理为无结果情况
            System.out.println("没有找到相关记录");
            return 0;
        }
        String  nowtime= resultSet.getString(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse(nowtime);

        Calendar instance = Calendar.getInstance();
        instance.setTime(parse);
        instance.add(Calendar.DAY_OF_MONTH,days);

        String afterdays = format.format(instance.getTime());
        // 更新数据库中的日期
        String sql2 = "UPDATE rent SET days=? WHERE uid=? AND bid=?";
        PreparedStatement prepareStatement = con.prepareStatement(sql2);
        prepareStatement.setString(1,afterdays);
        prepareStatement.setInt(2,uid);
        prepareStatement.setInt(3,bid);

        int i = prepareStatement.executeUpdate();//执行更新
        return i;
    }

    public static int addCom(String content,int bid,int uid) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        String sql="INSERT `comment`VALUES(?,?,?,?)";
        PreparedStatement prepareStatement = con.prepareStatement(sql);
        prepareStatement.setInt(1,AutoId());
        prepareStatement.setString(2,content);
        prepareStatement.setInt(3,uid);
        prepareStatement.setInt(4,bid);
        int i = prepareStatement.executeUpdate();
        prepareStatement.close();
        con.close();
        return i;
    }
    public static List<String> getComById(int bid) throws SQLException, ClassNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        Connection con = getCon();
        Statement statement = con.createStatement();
        String sql="SELECT * from `comment` WHERE bid="+bid;
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            String content = resultSet.getString(2);
            int uid = resultSet.getInt(3);
            String str=content+" By [user:"+uid+"]";
            list.add(str);
        }
        statement.close();
        con.close();
        return list;
    }
    public static int RegisterUser(String uname,String passwd) throws SQLException, ClassNotFoundException {
        Connection con = getCon();
        // 修改 SQL 语句，明确指定列名，避免列数不匹配
        String sql = "INSERT INTO user (passwd, uname, ismanger) VALUES (?, ?, ?)";

        PreparedStatement prepareStatement = con.prepareStatement(sql);

        // 设置 SQL 参数：第一个参数是密码，第二个是用户名，第三个是 ismanger（默认为 NULL）
        prepareStatement.setString(1, passwd);
        prepareStatement.setString(2, uname);
        prepareStatement.setNull(3, Types.INTEGER);  // 传入 NULL 值给 ismanger 字段，假设没有指定值时，插入 NULL

        // 执行插入操作
        int i = prepareStatement.executeUpdate();

        return i; // 返回受影响的行数
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    }


}






