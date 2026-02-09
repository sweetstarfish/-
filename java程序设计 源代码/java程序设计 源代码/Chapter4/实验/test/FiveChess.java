package chap3.test;

import java.util.Scanner;

public class FiveChess {
	final static int WIDTH=16;
	static char[][] chess=new char[WIDTH][WIDTH];
	static boolean isBlack=true;
	
	
	public static void main(String[] args){
		initChessRoom();
		printChess();
		run();
	}
	
	public static void initChessRoom(){
		for(int i=0;i<chess.length;i++){
			for(int j=0;j<chess[i].length;j++){
				chess[i][j]='*';
			}
		}
	}
	
	public static void run(){
		Scanner s=new Scanner(System.in);
		
		while(true){
			System.out.println("请"+(isBlack?"黑":"白")+"方落子：");
			String str=s.next();
			int r=fromCharToInt(str.charAt(0));
			int c=fromCharToInt(str.charAt(2));
			if(chess[r][c]!='*'){
				System.out.println("该位置已经有棋子，请重新输入！");
				continue;
			}else{
				chess[r][c]=isBlack?'@':'O';
				printChess();
				if(isWin(r,c)){
					System.out.println((isBlack?"黑":"白")+"方获胜！");
					break;
				}
			}			
			isBlack=!isBlack;
		}
	}
	
	public static void printChess(){
		for(int i=0;i<chess.length;i++){
			if(i<10){
				System.out.print("  "+i);
			}else{
				System.out.print("  "+(char)('a'+i-10));
			}
		}
		System.out.println();
		for(int i=0;i<chess.length;i++){
			if(i<10){
				System.out.print(i+" ");
			}else{
				System.out.print((char)('a'+i-10)+" ");
			}
			
			for(int j=0;j<chess[i].length;j++){
				System.out.print(chess[i][j]+"  ");
			}
			System.out.println();
		}
	}
	
	public static int fromCharToInt(char c){
		if(c>='0' && c<='9'){
			return c-'0';
		}else{
			return c-'a'+10;
		}
	}
	
	
	/*判断输赢*/
	public  static boolean isWin( int r, int c){
		if(toLeftRight(r,c) || toUpperDown(r,c) || toLeftUpper(r,c) || toRightUpper(r,c)){
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean toLeftRight(int r, int c){  //水平		
		//找左边第一个不是ch的字符，或者到了边界。退出时，找到边界或者遇到不是ch的字符
		char ch=isBlack?'@':'O';
		int num=0,j;
		if (c>0){
			for(j=c-1; j>=0 && chess[r][j]==ch; j--);  
			//	从该位置向右统计ch的个数num
			
			for(int k=j+1; k<WIDTH && chess[r][k]==ch; k++){
				num++;
			}
		}
		return num>=5;			
	}
	
	private  static boolean toUpperDown(int r, int c){  //垂直
		char ch=isBlack?'@':'O';
		int i;
		int num=0,j;
		if (r>0){
			for(i=r-1; i>=0 && chess[i][c]==ch; i--);  
			//从该位置向右统计ch的个数num
			for(j=i+1;j<WIDTH && chess[j][c]==ch; j++){
				num++;
			}
		}
		return num>=5;	
	}
	private static boolean toLeftUpper(int r, int c){  //左斜
		char ch=isBlack?'@':'O';
		int i,j,k,kk;
		int num=0;
		if(r>0 && c>0){			
			for(i=r-1,j=c-1; i>=0 && j>=0 && chess[i][j]==ch; i--,j--);  
			//从该位置向右统计ch的个数num
			for(k=i+1,kk=j+1;k<WIDTH && kk<WIDTH && chess[k][kk]==ch; k++,kk++)
				num++;
		}
		return num>=5;	
	}
	
	private  static boolean toRightUpper(int r, int c){  //右斜
		char ch=isBlack?'@':'O';
		int i,j,k,kk;
		int num=0;
		if(r>0 && c<WIDTH-1){  
			for(i=r-1,j=c+1; i>=0 && j<WIDTH-1 && chess[i][j]==ch; i--,j++);  
			//从该位置向右统计ch的个数num
		
			for(k=i+1,kk=j-1; k<WIDTH && kk>=0 && chess[k][kk]==ch; k++,kk--){
				num++;
			}
		}
		return num>=5;	
	}

}
