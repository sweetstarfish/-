package chapter13;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;
public class UpdateGoodsDialog extends JDialog {      //修改学生记录对话框
	private static final long serialVersionUID = 10L; //序列号
	private GoodsService service = new GoodsService();        //关联业务层
	private Goods goods = null;    
	private Goods updgoods = null; 
	private UpdateGoodsDialog thisObj = this;                     //当前对象
	private JLabel labGid = new JLabel("商品编号：");
	private JLabel labCid = new JLabel("类别编号：");
	private JLabel labName = new JLabel("名称：");
	private JLabel labPrice = new JLabel("售价：");
	private JLabel labUnit = new JLabel("计量单位：");
	private JTextField tfGid = new JTextField(8);
	private JTextField tfCid = new JTextField(4);
	private JTextField tfName = new JTextField(10);
	private JTextField tfPrice = new JTextField(8);
	private JTextField tfUnit = new JTextField("个", 4);  //文本框显示"个"
	private JPanel pan1 = new JPanel();
	private JPanel pan2 = new JPanel();
	private JPanel pan3 = new JPanel();
	private JPanel pan4 = new JPanel();
	private JButton butOk = new JButton("确定");
	private JButton butCancel = new JButton("取消");
	public UpdateGoodsDialog(GoodsFrame frame, boolean modal, Goods goods) {
		super(frame, modal);
		this.goods=goods;
		this.setTitle("修改商品记录");
		this.setLocation(frame.getX() + 80, frame.getY( )+ 200);
		this.setSize(480, 300);
		initialize();                                   //调用初始化方法
		this.setVisible(true);
	}
	private void initialize(){                        //初始化方法
		tfGid.setText(goods.getGid());                            
		tfGid.setEditable(false);                       //商品编号不能修改
		tfCid.setText(goods.getCid());
		tfName.setText(goods.getName());
		tfPrice.setText(String.valueOf(goods.getPrice()));
		tfUnit.setText(goods.getUnit());
		pan1.add(labGid);
		pan1.add(tfGid);
		pan2.add(labCid);
		pan2.add(tfCid);
		pan2.add(labName);
		pan2.add(tfName);
		pan3.add(labPrice);
		pan3.add(tfPrice);
		pan3.add(labUnit);
		pan3.add(tfUnit);
		pan4.add(butOk);
		pan4.add(butCancel);
		this.setLayout(new GridLayout(4,1));
		this.add(pan1);
		this.add(pan2);
		this.add(pan3);
		this.add(pan4);
		butOk.addActionListener(new ActionHandler());  //按钮添加动作事件监听器
		butCancel.addActionListener(new ActionHandler());
	}
	//按钮动作事件监听处理类(内部类)：
		private class ActionHandler implements ActionListener{  
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==butOk){                 //“确定”按钮
					try{
						updgoods = new Goods();
						updgoods.setGid(tfGid.getText());
						updgoods.setCid(tfCid.getText().trim());
						updgoods.setName(tfName.getText().trim());
						String strPrice = tfPrice.getText().trim(); 
						updgoods.setPrice(new BigDecimal(strPrice));
						String strUnit = tfUnit.getText().trim(); 
						if (strUnit.length()==0) {updgoods.setUnit("个"); }
						else {	updgoods.setUnit(tfUnit.getText().trim()); }
						int recCount = service.updateGoods(updgoods); //业务层添加记录方法
						thisObj.setVisible(false);           //隐藏对话框
						if (recCount == 1){
							JOptionPane.showMessageDialog(null, "成功修改一条记录！");
							goods=updgoods;
						}
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "异常：" + ex.getMessage());
						tfCid.requestFocus();
					}
				}
				else if(e.getSource()==butCancel){           //“取消”按钮
					goods = null;
					thisObj.setVisible(false);               //隐藏对话框
				}
			}
		}
		
		public Goods getGoods(){                                 //获取商品对象方法
			return this.goods;
		}	
	
	
	
}
