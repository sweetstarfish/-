package chapter13;
import java.sql.*;
public class Example13_1 {

	public static void main(String[] args) {
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
             //上面是加载SQL Server数据库驱动程序的语句，可省略该语句
			String url = "jdbc:mysql://localhost:3306/SMSdb?useUnicode=true&characterEncoding=UTF-8";
			Connection con=DriverManager.getConnection(url,"root","123456");  //建立连接
			Statement stmt=con.createStatement();                        //连接创建语句
			ResultSet rs=stmt.executeQuery("select * from Goods");  
			                                                   //语句执行查询得结果集
			System.out.println("=商品编号=类别编号=名称====售价==计量单位==");
			while(rs.next()){                                          //循环输出结果集所有行
				System.out.print(rs.getString(1) + "  ");              //字段(列)序号从1开始
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t ");
				System.out.print(rs.getBigDecimal(4) + "\t ");
				System.out.println(rs.getString(5));
				
			}
			rs.close();                                               //关闭结果集
			stmt.close();                                             //关闭语句
			con.close();                                              //关闭连接
		}
		catch(Exception e){                                          //捕获处理异常
			System.err.println("异常：" + e);
		}
	}

}
