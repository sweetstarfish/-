package chapter11;
import java.util.concurrent.BlockingQueue;
public class Customer1 extends Thread {
	private BlockingQueue queue;

	public Customer1(String name, BlockingQueue queue){
		super(name);
		this.queue = queue;
	}

	public void run(){
		try{
			for(int i=0; i<10; i++){
				synchronized(this){
					Object obj = queue.take();
					System.out.println(this.getName()+" remove data:"+obj);
					Thread.sleep(200);
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
