package Chapter5;
public class TestThis{
String name; int age;
TestThis(){System.out.println(this);}
TestThis(String name,int age) {this.name=name;this.age=age;}
void show(){System.out.println(this);}
void show1(){System.out.println(name+" "+age);}
public static void main(String[] args){
TestThis tt=new TestThis();
tt.show();
System.out.println(tt);
TestThis tt1=new TestThis("王林",18);
tt1.show1();}
}