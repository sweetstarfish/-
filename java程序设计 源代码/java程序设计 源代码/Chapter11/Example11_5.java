package chapter11;

public class Example11_5 {

	public static void main(String[] args) {
		Store s=new Store(5);
		Producer p1 = new Producer("A",s);
		Producer p2 = new Producer("B",s);
		Customer c1 = new Customer("C",s);
		Customer c2 = new Customer("D",s);
		p1.start();
		p2.start();
		c1.start();
		c2.start();
	}

}
