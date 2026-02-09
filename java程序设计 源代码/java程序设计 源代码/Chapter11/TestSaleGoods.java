package chapter11;

public class TestSaleGoods {

	public static void main(String[] args) {
		Example11_4 runGoods = new Example11_4();
		Thread th1 = new Thread(runGoods, "窗口1");
		Thread th2 = new Thread(runGoods, "窗口2");
		Thread th3 = new Thread(runGoods, "窗口3");
		Thread th4 = new Thread(runGoods, "窗口4");
		th1.start();
		th2.start();
		th3.start();
		th4.start();

	}

}
