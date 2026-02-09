package SecondWeekSixthDay;
    public class TestGoodsShop {
    //计算卖出商品的收入
    public static void main(String[] args) {
        GoodsShop goodsShop =new GoodsShop ();
        Good goods1=new Cola();
        goodsShop.sellGoods(goods1);
        Good goods2=new Fanta();
        carShop.sellGoods(goods2);
        int counMoney=carShop.getmoney();
        System.out.println("商品出售的价格是："+counMoney);
    }
}