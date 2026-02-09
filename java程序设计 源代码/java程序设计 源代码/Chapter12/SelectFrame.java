package chapter12;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*; 
public class SelectFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel pan1 = new JPanel();
	private JPanel pan2 = new JPanel();
	private JPanel pan3 = new JPanel();
	private JLabel lab1 = new JLabel("请选择年级和姓名（可选多人）：");
	private String[] classes = {"一年级", "二年级"};       //数组
	private String[] names1 = {"张三","李四","李大"};
	private String[] names2 = {"王五","赵六","钱七"};
	private JComboBox<String> cbb = new JComboBox<>(classes); //组合框
	private JList<String> list = new JList<>(names1);//列表框
	private JScrollPane sp1 = new JScrollPane(list);       //含列表滚动窗格
	private JLabel lab2 = new JLabel("选人结果：");
	private JTextArea ta = new JTextArea(4, 10);
	private JScrollPane sp2 = new JScrollPane(ta);         //含文本区滚动窗格

	public SelectFrame(){                                  //构造方法
		this.setTitle("SelectFrame");
		this.setBounds(100, 200, 300, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();                                     //调用初始化方法
		this.setVisible(true);
	}

	public void initialize(){                            //初始化方法
		pan1.add(lab1);
		this.add(pan1, BorderLayout.NORTH);
		pan2.add(cbb);                                    //面板2添加组合框
		list.setFixedCellWidth(50);                       //列表框设固定单元宽度
		list.setVisibleRowCount(3);                       //列表框设可见行数
		pan2.add(sp1);                                    //面板2添加列表滚动窗格
		pan2.setBackground(Color.LIGHT_GRAY);           //设置面板2为浅灰色
		this.add(pan2, BorderLayout.CENTER);
		pan3.add(lab2);
		pan3.add(sp2);                                    //面板3添加文本区滚动窗格
		this.add(pan3, BorderLayout.SOUTH);
		//下拉组合框添加选项事件监听器：
		cbb.addItemListener(new ItemHandler());
		//列表框添加选择事件监听器：
		list.addListSelectionListener(new ListSelectionHandler());
	}
	
	//组合框选项事件监听类（内部类）：
	private class ItemHandler implements ItemListener {
		public void itemStateChanged(ItemEvent e){
			if(cbb.getSelectedIndex() == 0){
				list.setListData(names1);                 //列表框设置列表数据
			}
			if(cbb.getSelectedIndex() == 1){
				list.setListData(names2);
			}
			ta.setText(null);                            //清空文本区
		}
	}
	
	//列表框选择事件监听类（内部类）：
	private class ListSelectionHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e){  //列表值改变方法
			java.util.List<String> items = list.getSelectedValuesList();
					//使用java.util包的泛型列表集List存放列表框选中的数据项
			StringBuffer sb = new StringBuffer();         //字符串缓冲对象
			sb.append(cbb.getSelectedItem());             //追加组合框所选项
			for (int i=0; i<items.size(); i++){
				sb.append("\n" + items.get(i));           //追加列表项
			}
			ta.setText(sb.toString());                    //显示在文本区
		}
	}
}
