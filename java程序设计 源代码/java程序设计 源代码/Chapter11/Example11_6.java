package chapter11;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public class Example11_6 {
	public static void main(String[] args) {
		BlockingQueue store = new ArrayBlockingQueue(5);
		Producer1 p1 = new Producer1("A",store);
		Producer1 p2 = new Producer1("B",store);
		Customer1 c1 = new Customer1("C",store);
		Customer1 c2 = new Customer1("D",store);
		p1.start();
		p2.start();
		c1.start();
		c2.start();
	}
}
