package Chapter4;       //声明程序所在包
public class Example4_1 {
    public static void main(String[] args) {
        int []age = new int[10];
        //动态初始化
       for (int i = 0; i < age.length; i++) {
            age[i] = i;
            System.out.print(age[i]+"     ");
        }
    }
}
