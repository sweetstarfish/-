
public class Person {
private String name;
private int age;
public String getName() {return name;}
public void setName(String name) {
this.name = name;}
public int getAge() {return age;}
public void setAge(int age) 
    { if(age>0&&age<120){this.age = age;}else{System.out.println("年龄不合理");}}
public Person() {}
public Person(String name, int age) {super();
this.name = name;this.age = age;}
public void show(){ System.out.println("我是"+getName()+",今年"+getAge()+"岁了！");} 
}
Student类：
public class Student extends Person{
private int id;
public Student() {super();}//生成无参构造
public Student(String name, int age,int id) {
super(name, age);  
setId(id);}
public Student(int id) {
super(); 
this.id = id;}//生产有参构造
public int getId() {return id;}//生成get和set方法
public void setId(int id) {
if(id>0){
		   this.id=id;	}
else{
System.out.println(“学号不合理！！");}
}
public void show(){
super.show();
System.out.println("学号: "+getId());}
}
TestPersonStudnet类：
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