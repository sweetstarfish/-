package Chapter5;       //声明程序所在包
/* 1.私有化成员变量 */
public class Girl{

        public void setAge() {
        }
   private String name;
   private int age;
   private boolean flag;
public Girl(){}
public Girl(String name,int age, boolean flag){
 this.name=name;
 this. age=age;
 this. flag =flag;}
public String show(){
  System.out.println("我是”+name+ “ ,今年”+age+ “目前 ” +flag );
}


//2.提供公共的get和set方法，并且在方法体中进行合理的判断
public String getName(){  return name; }
   public void setName(String name){ this.name=name; }
   public int getAge(){  return age;  }
   public void setAge(int age){ if(age>0 && age<=18){
    this.age=age;}else{System.out.println("年龄不合理");}}
   public boolean getFlag(){  return flag; }
   public void setFlag(){  this.flag=flag; }
}