package chapter11;

public class Store {
	private int count;
	public final int SIZE;
	
	public Store(int size){
		this.SIZE=size;
		count=0;
	}
	
	public synchronized void addData(){
		while(count==SIZE){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count++;
		System.out.println(Thread.currentThread().getName()+" add Data:"+count);
		this.notifyAll();
	}
	
	public synchronized void removeData(){
		while(count==0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" remove data:"+count);
		count--;
		this.notifyAll();
	}
}
