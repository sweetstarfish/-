package test;
public class Goods{
	public String getInfo(){//用来描述客户的订单信息
               return "客户订单信息";
};   
 }
2、编写Cola类，该类继承自Goods并重写getInfo( )方法。
public class Cola extends Goods {
     @Override
     public String getInfo() {
              return "cola";
     }
 }
3、编写Sprite类，该类继承自Goods并实现getInfo( )方法。
public class Sprite extends Goods{
     @Override
     public String getInfo() {
         return "Sprite";
     }
 }
4、编写GoodsFactory类，该类定义了一个静态方法getorder ( )，它可以根据用户指定的订单来创建对象。
  package test;
  public classGoodsFactory {
      public static Goods getorder(String name) {
          if (name.equalsIgnoreCase("cola")) {//如果需要cola，则创建cola对象
              return new cola();
          } else if (name.equalsIgnoreCase("Sprite")) { //如果需要Sprite，则创建Sprite对象
              return new Sprite ();
          } else {
              return null;  //暂时不支持其他车型
         }
     }
 }
5、编写Customer类用来进行测试。在main()方法中，根据用户需要不同的商品。
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