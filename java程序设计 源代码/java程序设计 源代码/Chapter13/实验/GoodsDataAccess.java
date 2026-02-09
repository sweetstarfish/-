package chapter13;
/** 数据层 */
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
public class GoodsDataAccess {                   //数据层类
	private static String url =                              //数据库地址
			"jdbc:mysql://localhost/SMSdb?useUnicode=true&characterEncoding=UTF-8";
	private static Connection con;                          //连接
	private Statement stmt;                                   //语句
	private PreparedStatement prpstmt;                        //预编译语句
	private ResultSet rs;                                     //结果集
	public static Connection createConnection(){            //建立连接方法
		try{
			if (con == null || con.isClosed()) {
				//Class.forName(driver);                       //加载驱动程序(可省)
				con=DriverManager.getConnection(url,"root","123456");  //建立连接
			}
		}
		catch(Exception e){ 
			System.err.println("建立连接异常："+ e);
		}
		return con;
	}
	public static void closeConnection(){                  //关闭连接方法
		try{
			if(con != null && ! con.isClosed()){
				con.close();
			}
		}
		catch(SQLException se){
			JOptionPane.showMessageDialog(null, "关闭库连接异常：" + 
					se.getMessage());
		}
	}
	
	private void closeResultSet(){                          //关闭结果集方法
		try{
			if (rs != null ){ rs.close();}
		}
		catch(SQLException se){
			JOptionPane.showMessageDialog(null, "关闭结果集异常：" + 
					se.getMessage());
		}
	}
	
	private void closeStatement(){                         //关闭语句方法
		try{
			if (stmt != null ){ stmt.close();}
			if (prpstmt !=null){ prpstmt.close();}
		}
		catch(SQLException se){
			JOptionPane.showMessageDialog(null, "关闭语句异常：" +
					se.getMessage());
		}
	}
	public Vector<Goods> getAllRecords(){                   //获取所有记录方法
		Vector<Goods> sgoods = new Vector<Goods>();             //商品记录集
		String sql = "select * from Goods";
		try{
			createConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Goods goods = new Goods();                      //商品对象
				goods.setGid(rs.getString("Gid"));
				goods.setCid(rs.getString("Cid"));
				goods.setName(rs.getString("Name"));
				goods.setPrice(rs.getBigDecimal("Price"));
				goods.setUnit(rs.getString("Unit"));
				sgoods.add(goods);
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "查找所有记录异常：" +
					e.getMessage());
		}
		finally{
			closeResultSet();
			closeStatement();
			closeConnection();         
		}
		return sgoods;
	}
	 //按商品编号查找商品记录方法：找到，返回Goods对象，否则，返回null
		public Goods searchRecord(String gid){                  //查找商品记录方法
			Goods goods = null;                                   //商品变量
			String sql = "select * from Goods where Gid = ?";
			try{
				createConnection();
				prpstmt = con.prepareStatement(sql);
				prpstmt.setString(1, gid);                    //参数索引从1开始
				rs = prpstmt.executeQuery();
				if(rs.next()){                               //如果查找到记录
					goods = new Goods();                         //构建商品对象
					goods.setGid(rs.getString("Gid"));
					goods.setCid(rs.getString("Cid"));
					goods.setName(rs.getString("Name"));
					goods.setPrice(rs.getBigDecimal("Price"));
					goods.setUnit(rs.getString("Unit"));
				}
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "查找记录异常：" +
						e.getMessage());
			}
			finally{
				closeStatement();
				closeConnection();         
			}
			return goods;
		}
		//添加一条商品记录方法：成功，返回1(更新语句数目)；不成功，返回0。
		public int addRecord(Goods goods){                        //添加商品记录方法
			String sql = "insert into Goods(Gid,Cid,Name,Price,Unit) values (?,?,?,?,?)";
			int recCount = 0;
			try{
				createConnection();
				prpstmt = con.prepareStatement(sql);
				prpstmt.setString(1, goods.getGid());
				prpstmt.setString(2, goods.getCid());
				prpstmt.setString(3, goods.getName());
				prpstmt.setBigDecimal(4, goods.getPrice());
				prpstmt.setString(5, goods.getUnit());
				recCount = prpstmt.executeUpdate();           //执行更新
			}
			catch(SQLException se){
				JOptionPane.showMessageDialog(null, "添加记录异常：" +
						se.getMessage());
			}
			finally{
				closeStatement();
				closeConnection();         
			}
			return recCount;
		}
		//修改一条商品记录：成功，返回1(更新语句数目)；不成功，返回0。
		public int updateRecord(Goods goods){                   //修改商品记录方法
			String sql = "update Goods set Cid=?,Name=?,Price=?,Unit=? where Gid=?";
			int recCount = 0;
			try{
				createConnection();
				prpstmt = con.prepareStatement(sql);
				prpstmt.setString(1, goods.getCid());
				prpstmt.setString(2, goods.getName());
				prpstmt.setBigDecimal(3, goods.getPrice());
				prpstmt.setString(4, goods.getUnit());
				prpstmt.setString(5, goods.getGid());
				recCount = prpstmt.executeUpdate();          //执行更新
			}
			catch(SQLException se){
				JOptionPane.showMessageDialog(null, "修改记录异常：" +
						se.getMessage());
			}
			finally{
				closeStatement();
				closeConnection();         
			}
			return recCount;
		}
		//删除一条商品记录：成功，返回1(更新语句数目)；不成功，返回0。
		public int deleteRecord(Goods goods){                   //删除商品记录方法
			String sql = "delete from Goods where Gid=?";
			int recCount = 0;
			try{
				createConnection();
				prpstmt = con.prepareStatement(sql);
				prpstmt.setString(1, goods.getGid());
				recCount = prpstmt.executeUpdate();          //执行更新
			}
			catch(SQLException se){
				JOptionPane.showMessageDialog(null, "删除记录异常：" +
						se.getMessage());
			}
			finally{
				closeStatement();
				closeConnection();         
			}
			return recCount;
		}	
		
		
}
