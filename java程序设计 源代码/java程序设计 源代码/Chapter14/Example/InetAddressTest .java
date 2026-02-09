public class InetAddressTest {
	public static void main(String[] args) {
        try {
            InetAddress inet1 = InetAddress.getByName("120.192.83.125");
            System.out.println(inet1);
            InetAddress inet2 = InetAddress.getByName("www.sina.com.cn");
            System.out.println(inet2);
            //获取本机的域名和IP地址
            InetAddress inet3 = InetAddress.getLocalHost();
            System.out.println(inet3);
            //获取InetAddress对象所含域名
            System.out.println(inet2.getHostName());
            //获取InetAddress对象所含IP地址
            System.out.println(inet2.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
	}
}
