//1）定义仓库类
class Storage {                                           //仓库类
  private String name;                                 //产品名称
  private int max,  num, no;                      //最大库存量、产品库存数，产品编号
  public Storage(String name, int max){ 
this.name=name;
This.max=max;
}
  public synchronized void input() {                  //同步的生产(入仓)方法
	   while (num >= max) {                      //若产品数超出最大库存量
	      try{ wait();}  catch(Exception e){} }      //则等待
       num ++;                               //直到被通知唤醒才生产
	   System.out.printf("生产%d号%s，当前库存数：%d\n", ++no, name, num); 
        notify();                             //通知消费
  }
  public synchronized void output() {               //同步的消费(出仓)方法
	  while (num <= 0) {                        //若库存没有产品
	     try{ wait();}  catch(Exception e){} }     //则等待
	 num --;                                //直到被通知唤醒才消费
	 System.out.printf("消费%d号%s，当前库存数：%d\n",no-num, ...);
	 notify();                                      //通知生产
  }
Public String getName(){
Return this.name;
}

Public String getMax(){
Return this.max;
}
}
//2）定义生产者线程
class Producer extends Thread {    //生产者类
	Storage store;
	public Producer(Storage store) {
	  this.store = store; }
	public void run() {
	  for(int i=0; i<10; i++){
	       store.input();     //调用同步生产方法
}  
   }
}
//3）定义消费者线程
class Consumer extends Thread {    //消费者类
	Storage store;
	public Consumer(Storage store) {
	  this.store = store;  }
	public void run() {               //调用同步消费方法
	  for(int i=0; i<10; i++){ store.output(); 
}
}
 }
//4）定义主类
public class Test11{
Public static void main(String[] args) {
	Storage store = new Storage("洗衣机", 4);  //库存4洗衣机仓库
    System.out.println("构建最大库存量为"+store.getMax()+store.getName()+"仓库\n");
	Producer producer = new Producer(store);   //生产者
	Consumer consumer = new Consumer(store);   //消费者
	producer.start();	
consumer.start();
}
}
