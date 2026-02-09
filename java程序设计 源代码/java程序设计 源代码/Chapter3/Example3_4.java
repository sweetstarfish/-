package Chapter3;       //声明程序所在包
class Example3_4{
      void test(){
             System.out.println("No parameters");
             }
      void test(int a){
           System.out.println("a: "+a);
          }
      void test(int a,int b){
           System.out.println("a: "+a+" b: "+b);
           }
      double test(double a){
              System.out.println("double a: "+a);
               return a*a;
         }
        }
       class Overload{
     public static void main(String args[]){
              OverloadDemo ob=new OverloadDemo();
              double result;
              ob.test();
              ob.test(10);
              ob.test(10,20);
              result=ob.test(123.22);
              System.out.println("Result of ob.test(123.22): "+result);
           }
          }
