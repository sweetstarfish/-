package Chapter4;       //声明程序所在包
public class  Example4_3{
    public static void main(String[] args){
        //先声明
        int[] hobbies;
        hobbies=new int[3];
        hobbies[0]=1;
        hoobies[2]=2;
        System.out.println(hobbies[0]); //输出第一个数
        //输出数组长度为3,如何输出？数的属性length
        System.out.println(hobbies.length);
        }
}