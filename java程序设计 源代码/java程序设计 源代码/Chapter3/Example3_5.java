package Chapter3;       //声明程序所在包
public class Example3_5{
         public static String name=" 微学院";
         public int i;
         public void test1() {
             //int j;//在方法中或者块中则不能这样初始化，不然会报错
             int j=3;
             if (j==3){ //块可以访问方法级别的变量，j为test1方法中的变量
                 int k=5;
                System.out.println("k="+k);//这样写就没有问题
             }
            // System.out.println("k="+k);块中的变量不能被外部调用，这样写会报错
             System.out.println("name="+name+"，i="+i+",j="+j);
         }
         public static void main(String[] args){
             System.out.println(Demo.name);
             Demo t=new Demo();
             t.test1();
     }
}
