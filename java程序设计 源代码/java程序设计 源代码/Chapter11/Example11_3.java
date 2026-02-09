package chapter11;
public class Example11_3 extends Thread{
	          String threadId;
	        public Example11_3(String threadId) {
        	   this.threadId=threadId;
           }
	public  void run() {
		 try {
		       System.out.println(" Thread started:"+this.threadId);
               for (int i=0;i<6;i++)
               {  sleep((int)(500*Math.random()));
                 System.out.println(threadId);
               }
        	   System.out.println(" Thread stopped:"+this.threadId);
        	 }
        	 catch(InterruptedException e)
        	 { return;
        	 }
      }

	public static void main(String[] args) {
		Example11_3  t1=new Example11_3("1");
		Example11_3  t2=new Example11_3("2");
		t1.start();
		try{
			t1.join();
		}
		catch(InterruptedException e)
		{}
		t2.start();
		System.out.println(" main stopped");
	}

}
