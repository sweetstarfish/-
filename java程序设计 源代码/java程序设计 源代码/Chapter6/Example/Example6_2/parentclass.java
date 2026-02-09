
package Chapter6;
class parentclass {	// 声明父类
	void pprint() {// 声明成员方法
	     this.print();		
this.print1(0);
}
	void print() {//声明同类型、同名、同参数成员方法
	     System.out.println("父类：同类型、同名、同参数成员方法！");	}
	void print1(int a) {//声明同类型、同名但参数不同的成员方法
		System.out.println("父类：同类型、同名但参数不同的成员方法！");	}
}
class subclass extends parentclass {//基于parentclass类定义subclass子类
	   void sprint() {//声明成员方法
		    this.print();		
this.print1();
}
	void print() {//声明同类型、同名、同参数成员方法
		 System.out.println("子类：同类型、同名、同参数成员方法！");	}
		 void print1() {//声明同类型、同名但参数不同的成员方法
		 System.out.println("子类：同类型、同名但参数不同的成员方法！");	}
}
public class MethodInherit {	// 声明公共类
	public static void main(String[] args) {
		subclass obj = new subclass();
		obj.pprint(); // 调用父类的成员方法
		obj.sprint(); // 调用子类的成员方法
	}
}