package chapter11;

public class Customer extends Thread{
	private Store store;

	public Customer(String name, Store store){
		super(name);
		this.store =store;
	}

	public void run(){
		try{
			for(int i=0; i<10; i++){
				store.removeData();
				Thread.sleep(200);
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
