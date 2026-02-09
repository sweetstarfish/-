package Chapter2;
import java.util.Scanner;
public class Game {
	static final int FGVALUE = 20;		//定义仙女草的售价
	static final int GSVALUE = 16;		//定义银河梭的售价
	static final int FGLIFE = 30;		//定义仙女草增加的生命力
	static final int GSLIFE = 20;		//定义银河梭增加的生命力
	public static void main(String[] args) {
	      int goldcoin = 100;	//定义金币的数量
	      int fg_num = 0, gs_num = 0; 	//定义仙女草与银河梭的数量
	      int max_life = 0; 		//定义最大生命力值
	      System.out.println("请输入金币的数量为：");
	      Scanner in = new Scanner(System.in); 
	      goldcoin = in.nextInt(); 
	      for (int fg_loop = 0; fg_loop <= (goldcoin / FGVALUE); fg_loop++)
		for (int gs_loop = 0; gs_loop <= (goldcoin / GSVALUE); gs_loop++)
		   if (((fg_loop * FGVALUE + gs_loop * GSVALUE) <= goldcoin)
                                            && ((FGLIFE * fg_loop + GSLIFE * gs_loop) > max_life)) {
	                                      fg_num = fg_loop;		//记录仙女草的数量
			   gs_num = gs_loop;		//记录银河梭的数量
		                     max_life = FGLIFE * fg_loop + GSLIFE * gs_loop;
	      }
	      System.out.println("购买的宝物最多能增加你" + max_life + "个生命力！");
	      System.out.println("购买仙女草的数量为" + fg_num);
	      System.out.println("购买银河梭的数量为" + gs_num);
	}
}
