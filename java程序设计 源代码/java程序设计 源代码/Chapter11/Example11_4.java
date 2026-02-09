package chapter11;

public class Example11_4 implements Runnable{
 private int goodsCount=5;  //某商品库存量，多个线程都访问该共享变量
 public  synchronized void sellGoods() {
	 if(goodsCount>0) {
		 System.out.println(Thread.currentThread().getName()+"正在出售第"+(5-goodsCount+1)+"件库存商品，"+"还剩"+(--goodsCount)+"库存");
	 }else {
		 System.out.println("该商品已经卖完！");
	 }
 }
 public void run() {
	 while(goodsCount>0) {   //线程不停循环出售商品
		 sellGoods();
		 try {
			 Thread.sleep(100);
		 }  catch(InterruptedException e) {
			 e.printStackTrace();
		 }
	 }
 }
}
