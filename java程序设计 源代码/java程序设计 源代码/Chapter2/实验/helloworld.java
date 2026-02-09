//1、首先我们来实现最简单的第一步，只加法只一次计算。
public class helloworld {
public static void main(String [] args){
        System.out.println("欢迎使用计算器");
        System.out.print("请输入第一个整数：");
        Scanner scanner = new Scanner(System.in);
        int data1 = scanner.nextInt();
        System.out.print("请输入第二个数字：");
        int data2 = scanner.nextInt();
        int data = data1 + data2;
        System.out.println("计算结果是：" + data);
    }
}
//2、把他改成四则运算，并且带有循环，知道想要退出为止。
public class helloworld {
public static void main(String [] args){
        System.out.println("欢迎使用计算器");
        while(true){
            if(new helloworld().input_output()){
            }else{
                System.out.println("谢谢使用计算器OVER");
                break;
            }
        }
    }
    int count_data(int data1 , int data2 , String sign){
        int data = 0;
        if(sign.equals("+")){
            data = data1 + data2;
        }else if(sign.equals("-")){
            data = data1 - data2;
        }else if(sign.equals("*")){
            data = data1 * data2;
        }else if(sign.equals("/")){
            data = data1 / data2;
        }else{
            data = -999999999;
        }
        return data;
    }
     boolean input_output(){
        System.out.print("请输入第一个整数：");
        Scanner scanner = new Scanner(System.in);
        String l = scanner.nextLine();
        int data1 = Integer.valueOf(l);
        System.out.print("请输入运算符号：");
        String sign = scanner.nextLine();
        System.out.print("请输入第二个数字：");
        int data2 = Integer.valueOf(scanner.nextLine());
        int data = new helloworld().count_data(data1,data2,sign);
        if(data == -999999999){
            System.out.println("计算结果错误！");
        }else {
            System.out.println("计算结果是：" + data);
        }
        System.out.print("是否继续Y/N：");
        String contu = scanner.nextLine();
        if("Y".equals(contu) || "y".equals(contu)){
            return true;
        }else {
            return false;
        }
    }
}
