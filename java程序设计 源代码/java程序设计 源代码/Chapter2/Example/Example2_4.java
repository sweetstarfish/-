package Chapter2;       
class Example2_4{
   public static void main(String args[]){
       int i1=10;
       short s1=2;
       int i2 = i1+s1;
       float f1=12.5F;
       float f2=f1+i2;
       long l=12L;
       float f3 = l;
       char c1= 'a';
       char c2= 'A';
       int i3 = c1+1;
       int i4= c2+1;
       //short、byte、char之间的运算结果都被自动转化为int类型
       short ss1=12;
       byte bb1= 1;
       char cc1='a';
       int ii1=ss1+bb1+cc1;
   }        
}



