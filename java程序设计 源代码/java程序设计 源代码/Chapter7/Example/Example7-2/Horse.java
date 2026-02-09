class Horse extends Animal{
	String str;
	Horse(String s){
		super(s);		//调用父类的构造方法
	}
	void eat() {		//重写父类的抽象方法
		System.out.println("马吃草料！");
	}
}
