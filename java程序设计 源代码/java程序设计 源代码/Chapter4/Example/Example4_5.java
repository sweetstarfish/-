package Chapter4;       //声明程序所在包
public class  Example4_5{
    public static void main(String[] args){
        //1)
        int[] moneys = new int[]{1,5,10};
        //2）
        int[] moneys = {1,5,10};
        /**
          *区别在于不需要显性new 
          *
        */
        //利用for循环
        //for(int 1 =0 ; i < 3; i++){
        //     System.out.println(moneys[i]);
        // }
        for(int i = 0 ; i < moneys.length ; i ++){  
            System.out.println(moneys[i]);
        }
    }
 
}