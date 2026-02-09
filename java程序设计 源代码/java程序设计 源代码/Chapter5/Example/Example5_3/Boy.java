package Chapter5;       //声明程序所在包
public class Boy {
String name;
Boy(){this("张三");}
Boy(String name){
this.name=name;}
void  show(){
System.out.println(this.name);}
public static void main(String[] args){
Boy b=new Boy();
b.show();}}