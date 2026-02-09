class Dog extends Animal{
	String str;
	Dog(String s){
		super(s);
	}
	void eat() {
		System.out.println("¹·³Ô¹ÇÍ·£¡");
	}
}
public class Test {
	public static void main(String[] args) {
		Animal h=new Horse("Âí");
		Animal d=new Dog("¹·");
		h.eat();
		d.eat();
	}