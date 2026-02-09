import javax.swing.*;
public class ex5 {
    public static void main(String[] args) {
        // TODO code application logic here
        int i,j,max;
        String s1,s2;
        s1=JOptionPane.showInputDialog(null,"输入第1个整数");
        s2=JOptionPane.showInputDialog(null,"输入第2个整数");
        i=Integer.parseInt(s1);
        j=Integer.parseInt(s2);
        max=i>j?i:j;
        JOptionPane.showMessageDialog(null,i+"和"+j+"的最大数是"+max);
        System.exit(0);
    }
}




