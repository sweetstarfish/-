  package test;
  public class Customer {
      public static void main(String[] args) {
          // TODO Auto-generated method stub
          System.out.println("顾客需要购买cola");
          Goods b = CarFactory.getorder("cola");
          System.out.println("配送货物：" + b.getInfo());
         System.out.println("顾客需要购买Sprite");
          Car1 e = CarFactory.getCar("Sprite");
         System.out.println("配送货物：" + e.getInfo());
     }
 }