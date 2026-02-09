package chap5.test;

public class TestGoodsShop {
    public static void main(String[] args) {
       GoodsShop goodsShop =new GoodsShop();
       Goods g1=new Goods(001,"可口可乐");
       goodsShop.sellCar(g1);
       Car g2=new Car(002,"雪碧");
       goodsShop.sellCar(g2);
       g1.inforShow();
       g2.inforShow();
       double counMoney=goodsShop.getmoney();
         System.out.println("商品出售的价格是："+counMoney);
}
}