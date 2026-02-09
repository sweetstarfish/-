package chapter11;

public class Producer extends Thread{
	private Store store;

	public Producer(String name, Store store){
		super(name);
		this.store =store;
	}

	public void run(){
		try {
			for(int i=0; i<5; i++){
				store.addData();
				Thread.sleep(100);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
