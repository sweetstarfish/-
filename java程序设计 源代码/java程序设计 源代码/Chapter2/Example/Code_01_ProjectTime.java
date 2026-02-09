public class Code_01_ProjectTime {
    public static void main(String[] args) {
        //毫秒时间
        System.out.println(currentTimeMillis()+"ms");
        //纳秒时间
        System.out.println(nanoTime()+"ns");
}
// Test 毫秒时间
    public static long currentTimeMillis() {
        long startTime=System.nanoTime();   //获取开始时间  
        int sum = 0;
        for(int i=0;i<10000000;i++){
            sum +=i;
        }
        long endTime=System.nanoTime(); //获取结束时间  
        return endTime-startTime;
}
// Test 纳秒时间
    public static long nanoTime() {
        long startTime=System.nanoTime();   //获取开始时间  
        int sum = 0;
        for(int i=0;i<10000000;i++){
            sum +=i;
        }
//        System.out.println(sum);
        long endTime=System.nanoTime(); //获取结束时间  
        //        System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
        return endTime-startTime;
    }
}





