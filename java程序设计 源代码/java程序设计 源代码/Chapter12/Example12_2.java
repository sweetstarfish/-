package chapter12;
import java.awt.*;
public class Example12_2 extends Frame{
	public Example12_2() {
		super("FlowLayout example");
	    setSize(200,150);
	    setLayout(new FlowLayout());
	    add(new Button("Button 1"));
	    add(new Button("Button 2"));
	    add(new Button("Button 3"));
	    add(new Button("Button 4"));  
	    add(new Button("Button 5"));
	    setVisible(true);
	}
	public static void main(String[] args) {
		Example12_2 fl = new Example12_2();
	}
}
