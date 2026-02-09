package chapter11;

public class ThreadTest2 {

	public static void main(String[] args) {
		
		 System.out.println("Starting ThreadTest1");
		 Runnable r1=new Example11_2("thread1");
		 Thread t1=new Thread(r1);
	     t1.start();
	     Runnable r2=new Example11_2("thread2");
	     Thread t2=new Thread(r2);
	     t2.start();
	     Runnable r3=new Example11_2("thread3");
	     Thread t3=new Thread(r3);
	     t3.start();
	     System.out.print("ThreadTest1 is done");
	}

}
