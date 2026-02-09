package EXception;

class Exc0 extends Exception {	
}
class Exc1 extends Exc0 {
}
public class exer4 {
	public static void main(String[] args) {
		try {
			throw new Exc1();
		} catch (Exception e) {
			System.out.println("Exception");
		} catch (Exc0 e) {
			System.out.println("Exc0");
		}
	}
}
