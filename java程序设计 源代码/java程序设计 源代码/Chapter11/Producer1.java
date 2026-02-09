package chapter11;
import java.util.concurrent.BlockingQueue;
public class Producer1 extends Thread{
	private BlockingQueue queue;

	public Producer1(String name, BlockingQueue queue){
		super(name);
		this.queue = queue;
	}

	public void run(){
		try {
			for(int i=0; i<5; i++){
				synchronized(this){
					queue.put(i);				
					System.out.println(this.getName()+" add Data:"+i);
					Thread.sleep(100);}
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
