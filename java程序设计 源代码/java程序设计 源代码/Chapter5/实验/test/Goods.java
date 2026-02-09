package chap5.test;

public class Goods{
	private int num;±àºÅ
    private String name;ÐÍºÅ
    private double price;µ¥¼Û
    Goods (){ super();}
    public Goods (int num, String name) {
    super();  this.num = num; this.name = name;}
    public int getNum() { return num; }
    public void setNum(int num) { this.num = num;}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { 
    	int NAME=getNum();
    	switch(NAME){
			case 001 price= 20000;break;
			case 002 price= 300000;break;
			default  price=150000;
      }
    	return price;
    }
    public void  inforShow() {
        System.out.println(Car num=+getNum()+, name=+ getName() + , price= + getPrice() );
     }
 }
