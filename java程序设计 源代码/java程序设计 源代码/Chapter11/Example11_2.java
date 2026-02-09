package chapter11;

public class Example11_2 implements Runnable {
	 String threadId;
     public Example11_2(String threadId) {
  	   this.threadId=threadId;
     }
	public void run() {
		System.out.print("\n Thread started:"+this.threadId);
        for (int i=0;i<6;i++)
       	 System.out.print(" i="+(i+1)+"\t");
        System.out.print("\n Thread stopped:"+this.threadId);
	}

}
