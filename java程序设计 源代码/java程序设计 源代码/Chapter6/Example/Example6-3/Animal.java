//父类Animal的定义
public class Animal{
    public String name;    //动物名字
}
//子类Cat的定义
public class Cat extends Animal{
    private String name;    //名字
    public Cat(String aname,String dname)    {
        super.name=aname;    //通过super关键字来访问父类中的name属性
        this.name=dname;    //通过this关键字来访问本类中的name属性
    }
    public String toString()    {
        return"我是"+super.name+"，我的名字叫"+this.name;
    }
    public static void main(String[] args)    {
        Animal cat=new Cat("动物","喵星人");
        System.out.println(cat);
    }
}