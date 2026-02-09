class Socket {
public static void main(String[] args) {
//1）基于Socket的TCP编程，客户端根据指定服务器端的IP地址（或端口号）创建Socket对象，打开连接到Socket的输入输出流进行传输，按照TCP协议对Socket进行读写操作。
Socket s = new Socket(“192.168.40.165”,9999); 
OutputStream out = s.getOutputStream(); 
out.write(" 您好，我想咨询下该商品适合几岁的孩子使用？".getBytes()); 
s.close();
//2）服务器端建立ServerSocket对象，负责等待客户端请求建立套接字连接，接收客户的套接字请求。
ServerSocket ss = new ServerSocket(9999); 
Socket s = ss.accept (); 
InputStream in = s.getInputStream(); 
byte[] buf = new byte[1024]; 
int num = in.read(buf); 
String str = new String(buf,0,num); 
System.out.println(s.getInetAddress().toString()+”:”+str); 
s.close(); 
ss.close();
}
}