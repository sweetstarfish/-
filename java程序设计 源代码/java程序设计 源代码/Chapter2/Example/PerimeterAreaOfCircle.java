public class PerimeterAreaOfCircle{
     final static double PI=3.1415926;      // 定义常量PI
     public static void  main(String args []){
        double r,perimeter,area;
        r=Double.parseDouble(args[0]); 
/*从命令行读入的字符串args[0]转换为实型dobule*/
perimeter=2*PI*r;
        area=PI*r*r;
        System.out.println("圆的周长为："+perimeter);  // 实现字符串的输出
        System.out.println("圆的面积为："+area);
  }
}

