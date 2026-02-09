package chapter13;
import java.sql.*;
import java.util.Scanner;
import java.math.*;
public class Example13_2 {

	public static void main(String[] args) {
		Connection con = null;                            //声明连接
		Statement stmt = null;                             //声明语句
		PreparedStatement prpstmt = null;                  //声明预编译语句
		ResultSet rs = null; //声明结果集
		BigDecimal d=null;
		
		try{
//			Class.forName("com.mysql.jdbc.Driver");        //可省略该语句
			String url =
				"jdbc:mysql://localhost/SMSdb?useUnicode=true&characterEncoding=UTF-8";
			con=DriverManager.getConnection(url,"root","123456");  //建立连接

			stmt = con.createStatement();                   //连接创建语句
			rs = stmt.executeQuery("select * from Goods");   //语句执行查询得结果集
			System.out.println("数据库原来的内容：");
			System.out.println("=商品编号=类别编号=名称====售价==计量单位==");
			while(rs.next()){                              //循环输出结果集各行
				System.out.print(rs.getString(1) + "  ");  //字段号从1开始
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t ");
				System.out.print(rs.getBigDecimal(4) + "\t ");
				System.out.println(rs.getString(5));
			}
			Scanner sc=new Scanner(System.in);
			String sql, gid,cid, name, price,choice;
			
			//插入一条记录：
			System.out.println("\n插入一条记录……");
			System.out.print("请输入8位的商品编号：");
			gid=sc.nextLine();
			System.out.print("请输入4位的类别编号：");
			cid=sc.nextLine();
			System.out.print("请输入名称：");
			name=sc.nextLine();
			System.out.print("请输入售价：");
			price=sc.nextLine();
			d=new BigDecimal(price);
			sql = "insert into Goods(Gid,Cid,Name,Price) values('"+
					gid+"','"+cid+"','"+name+"',"+d+")";
			stmt.executeUpdate(sql);                       //语句执行插入操作
			//更改所插入的记录：
			System.out.println("\n更改所插入的记录……");
			System.out.print("请输入类别编号：");
			cid=sc.nextLine();
			System.out.print("请输入名称：");
			name=sc.nextLine();
			sql = "update Goods set Cid=?,Name=? where Gid='"+gid+"'";
			                                         //带2个?参数的SQL语句字符串
			prpstmt = con.prepareStatement(sql);           //连接创建预编译语句
			prpstmt.setString(1, cid);                     //设置预编译语句第1个参数值
			prpstmt.setString(2, name);
			prpstmt.executeUpdate();                       //执行预编译语句更改记录
			//选择删除所更改的记录：
			System.out.println("\n选择删除所更改的记录……");
			System.out.print("删除记录吗(请回答y或n)？");
			choice=sc.nextLine();
			sc.close();
			if(choice.equalsIgnoreCase("y")){
				sql = "delete from Goods where Gid='"+gid+"'";
				prpstmt.close();
				prpstmt = con.prepareStatement(sql);       //连接创建预编译语句
				prpstmt.executeUpdate();                   //执行预编译语句，删除记录
			}
			rs.close();
			rs = stmt.executeQuery("select * from Goods");  //语句执行查询得结果集
			System.out.println("\n更新后的内容：");
			System.out.println("=商品编号=类别编号=名称====售价==计量单位==");
			while(rs.next()){                             //循环输出结果集各行内容
				System.out.print(rs.getString(1) + "  "); //字段(列)号从1开始
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t ");
				System.out.print(rs.getBigDecimal(4) + "\t ");
				System.out.println(rs.getString(5));
			}
		}
		catch(Exception e){                              //捕获处理异常
			System.err.println("异常：" + e);
		}
		finally{
			try{
				if (rs != null ){ rs.close(); }          //关闭结果集
				if (stmt != null) { stmt.close();}       //关闭语句
				if (prpstmt != null) { prpstmt.close();} //关闭预编译语句
				if (con != null) { con.close();}         //关闭连接
			}
			catch(SQLException se){
				System.err.println("关闭异常：" + se);
			}
		}
	}

}
