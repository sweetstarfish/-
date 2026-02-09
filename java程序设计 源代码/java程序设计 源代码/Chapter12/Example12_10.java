package chapter12;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Example12_10 extends JFrame{
	private JLabel lbl;
	   private JMenuBar mb;
	   private JMenu col,ext;
	   private JMenuItem red,yel,blu,clo;
	   public Example12_10()
	   {
	    super("Menu");
	    Container c = getContentPane();
	    mb=new JMenuBar();
	    col=new JMenu("color");
	    ext=new JMenu("exit");
	    red=new JMenuItem("red");
	    yel=new JMenuItem("yellow");
	    blu=new JMenuItem("blue");
	    clo=new JMenuItem("close window");
	    red.addActionListener(new Handler1());
	    yel.addActionListener(new Handler1());
	    blu.addActionListener(new Handler1());
	    clo.addActionListener(new Handler1());
	    mb.add(col);
	    mb.add(ext);
	    col.add(red);
	    col.add(yel);
	    col.add(blu);
	    ext.add(clo);
	    setJMenuBar(mb);
	    lbl=new JLabel("Menu Example");
	    add(lbl); 
	    setSize(200,160);
	    setVisible(true);
	   }
	public static void main(String[] args) {
		Example12_10 app=new Example12_10();

	}
	 class Handler1 implements ActionListener
     {     	
      public void actionPerformed(ActionEvent e )  
        { 
          JMenuItem mi=(JMenuItem) e.getSource();
          if (mi==red)
           lbl.setForeground(Color.red);
          if (mi==yel)
           lbl.setForeground(Color.yellow);
          if (mi==blu)
           lbl.setForeground(Color.blue);
          if (mi==clo)
           System.exit(0);
            } 
      } 
}
