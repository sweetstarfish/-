package Chapter6;
class Person {
     String name;	    
int age;  //声明两个成员变量
        public Person(String name, int age) {	 //有参构造方法
	           this.name = name;	this.age = age;	}
	    public Person() {		 //无参构造方法
			name
			String name2 = name;
			name
			String name3 = name;
			this.name = person name;	this.age = 23;	}
	    void pprint() {	 //成员方法，此时显示的是父类中成员变量的结果
			System.out.println(classPerson;   + Name  + this.name + ;  age + this.age);
         }
}
class Student extends Person {	 //基于Person类定义Student子类
	String name;		 //在派生类中声明自己的成员变量
	int classno;		 //声明新成员变量
	public Student() {	 //无参构造方法
	     this.name = student name;		this.age = 20;	}
	public Student(String name, int age, int classno) { //有参构造方法
	   this.name = name;        
this.age = age;       
this.classno = classno;	}

	public <String> Student(String liXiao, int age, int classno) {
	}

	void sprint() {	 //成员方法，此时显示的是子类中成员变量的
		System.out.println(classStudent;   + Name  + this.name + ;  age + this.age + ;                                                                            classno  + this.classno);}
}
public class VarInherit { //声明公共类
	public static <String> void main(String[] args) {
	      Student obj1 = new Student(); // 调用无参构造方法创建对象
		String LiXiao = null;
		Student obj2 = new Student(LiXiao, 18, 1); // 调用有参构造方法创建对象
	      obj1.pprint();	 obj2.pprint();  //调用父类的成员方法
		  obj1.sprint();	 obj2.sprint();  //调用子类的成员方法
	}
}