
public class medicman implements Attack,medic{
 	@Override
	public void medictest() {
		// TODO Auto-generated method stub
		System.out.println("医务兵既可以射击");
	}
 	@Override
	public void attack() {
		// TODO Auto-generated method stub
		System.out.println("医务兵也可以治疗他人");
	}
 }
