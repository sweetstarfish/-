public class MartDemo {
	int price = 0;//购物券金额
	//根据优惠规则计算实付款以及获取的购物券金额
	 void add(double sumMoney){
		 if(sumMoney>=1000){
				sumMoney *= 0.8;  //8 折优惠
				price = 200;//200元购物券
			}else if(sumMoney>=500){
				sumMoney *= 0.85; //8.5 折优惠
				price = 100;//100元购物券
			}else if(sumMoney>=300){
				sumMoney *= 0.9;  //9 折优惠
				price = 70;//70元购物券		
			}else{
				sumMoney *= 0.95; //9.5 折优惠
			}
			System.out.printf("实际付款金额：%8.2f",sumMoney);
			System.out.printf("获取购物券金额：%d",price);
		}
	 void add(int sumMoney1){
		 if(sumMoney1>=1000){
				sumMoney1 *= 0.8;  //8 折优惠
				price = 200;//200元购物券
			}else if(sumMoney1>=500){
				sumMoney1 *= 0.85; //8.5 折优惠1
				price = 100;//100元购物券
			}else if(sumMoney1>=300){
				sumMoney1 *= 0.9;  //9 折优惠
				price = 70;//70元购物券		
			}else{
				sumMoney1 *= 0.95; //9.5 折优惠
			}
			System.out.printf("实际付款金额：%d",sumMoney1);
			System.out.printf("获取购物券金额：%d",price);
		}
	 	public static void main(String args[]){
		 		MartDemo md = new MartDemo();
		 md.add(50000.0);
		 System.out.println();
		 md.add(50000);	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	}
}
