

public class testPersonStudent {
	public static void main(String[] args){
	Person p =new Person();//使用父类的引用指向父类自己的对象
	p.show();//调用Person类自己的show()方法
	System.out.println("------------");
	Student s =new Student();//使用子类的引用指向子类自己的对象
//当子类中没有show()方法时，则调用父类Person的show()方法
//当子类中重写show()方法后，则调用子类Student自己的show()方法
s.show();       
System.out.println("------------");
    Person ps=new Student("张三",18,1010); //使用父类引用指向子类对象，形成多态
	ps.show();}      
}