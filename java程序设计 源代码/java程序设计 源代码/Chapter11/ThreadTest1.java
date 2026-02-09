package chapter11;

public class ThreadTest1 {

	public static void main(String[] args) {
		
     System.out.println("Starting ThreadTest1");
     Example11_1 t1=new Example11_1("thread1");
     t1.start();
     Example11_1 t2=new Example11_1("thread2");
     t2.start();
     Example11_1 t3=new Example11_1("thread3");
     t3.start();
     System.out.print("ThreadTest1 is done");
	}

}
