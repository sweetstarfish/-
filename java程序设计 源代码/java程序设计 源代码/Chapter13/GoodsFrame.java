package chapter13;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GoodsFrame extends JFrame {
	private static final long serialVersionUID = 10L;           //序列号
	private GoodsService service = new GoodsService();              //关联业务层
	private Goods goods = new Goods();                                //实体类
	private GoodsFrame thisObj = this;                         //当前类对象
	private JLabel labGid = new JLabel("商品编号：");               //5个标签
	private JLabel labCid = new JLabel("类别编号：");
	private JLabel labName = new JLabel("名称：");
	private JLabel labPrice = new JLabel("售价：");
	private JLabel labUnit = new JLabel("计量单位：");
	private JTextField tfGid = new JTextField(8);              //5个只读文本框
	private JTextField tfCid = new JTextField(4);
	private JTextField tfName = new JTextField(10);
	private JTextField tfPrice = new JTextField(8);
	private JTextField tfUnit = new JTextField(4);
	private JButton butFirst = new JButton("首记录");          //按钮
	private JButton butPre = new JButton("上记录");
	private JButton butNext = new JButton("下记录");
	private JButton butLast = new JButton("尾记录");

	private JLabel labInputGid = new JLabel("请输入商品编号：");
	private JTextField tfInputGid = new JTextField(8);
	private JButton butSearch = new JButton("查找");          //按钮
	private JButton butAdd = new JButton("添加");
	private JButton butUpdate = new JButton("修改");
	private JButton butDelete = new JButton("删除");

	private JLabel labBrowse = new JLabel("========  浏 览 区  ========");
	private JLabel labTotal = new JLabel();                  //记录总数标签 
	private JLabel labTitle = new JLabel("――商品编号―类别编号―名称―售价―计量单位――");
	private JList<Goods> list;                                 //列表框
	private JScrollPane scrollPane;                          //含列表框的滚动窗格
	private JButton butBrowse = new JButton("浏览");        //按钮
	private JButton butRefresh = new JButton("刷新");
	private JButton butCancelBrowse = new JButton("取消浏览");
	private JPanel panUp = new JPanel();                   //上部面板
	private GridLayout gridLay = new GridLayout(6, 1);     //按6行1列布局
	private JPanel panUp1 = new JPanel();
	private JPanel panUp2 = new JPanel();
	private JPanel panUp3 = new JPanel();
	private JPanel panUp4 = new JPanel();
	private JPanel panUp5 = new JPanel();
	private JPanel panUp6 = new JPanel();
	
	private JPanel panDown = new JPanel();                //下部面板
	private BorderLayout borderLay = new BorderLayout();  //按边框布局
	private JPanel panDownSouth = new JPanel();
	public GoodsFrame(){                                 //构造方法
		this.setTitle("三层结构超市商品信息管理程序");
		this.setBounds(100, 100, 540, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{
			initialize();                                  //调用初始化方法
		}
		catch (Exception e){
			e.printStackTrace();                           //输出异常跟踪轨迹
		}
		this.setVisible(true);
	}

	private void initialize(){                           //初始化方法
		tfGid.setEditable(false);
		tfCid.setEditable(false);
		tfName.setEditable(false);
		tfPrice.setEditable(false);
		tfUnit.setEditable(false);
		panUp1.add(labGid);
		panUp1.add(tfGid);
		panUp2.add(labCid);
		panUp2.add(tfCid);
		panUp2.add(labName);
		panUp2.add(tfName);
		panUp3.add(labPrice);
		panUp3.add(tfPrice);
		panUp3.add(labUnit);
		panUp3.add(tfUnit);
		panUp4.setBackground(Color.LIGHT_GRAY);
		panUp4.add(butFirst);
		panUp4.add(butPre);
		panUp4.add(butNext);
		panUp4.add(butLast);
		panUp5.setBackground(Color.WHITE);
		panUp5.add(labInputGid);
		panUp5.add(tfInputGid);
		panUp5.add(butSearch);
		panUp5.add(butAdd);
		panUp5.add(butUpdate);
		panUp5.add(butDelete);
		labTitle.setVisible(false);
		labBrowse.setForeground(Color.BLACK);
		labTotal.setForeground(Color.BLUE);
		panUp6.setBackground(Color.ORANGE);
		panUp6.add(labBrowse);
		panUp6.add(labTotal);
		panUp.setLayout(gridLay);                         //上部面板设6行1列网格布局
		panUp.add(panUp1);
		panUp.add(panUp2);
		panUp.add(panUp3);
		panUp.add(panUp4);
		panUp.add(panUp5);
		panUp.add(panUp6);

		panDown.setLayout(borderLay);
		panDown.add(labTitle, BorderLayout.NORTH);
		panDownSouth.setBackground(Color.LIGHT_GRAY);
		panDownSouth.add(butBrowse);
		panDownSouth.add(butRefresh);
		panDownSouth.add(butCancelBrowse);
		panDown.add(panDownSouth, BorderLayout.SOUTH);
		goods = service.getFirstGoods();
		this.displayRecord(goods);                         //显示一个学生记录
		list = new JList<Goods>(GoodsService.getGoods()); //构建学生集列表
		scrollPane = new JScrollPane(list);              //构建滚动窗格
		panDown.add(scrollPane, BorderLayout.CENTER);   //下部面板放置滚动窗格
		scrollPane.setVisible(false);                   //暂不显示滚动窗格
		
		this.setLayout(new GridLayout(2, 1));           //窗框分上下两部分
		this.add(panUp);
		this.add(panDown);

		//按钮添加动作事件监听器：
		butFirst.addActionListener(new ActionHandler());
		butPre.addActionListener(new ActionHandler());
		butNext.addActionListener(new ActionHandler());
		butLast.addActionListener(new ActionHandler());
		butSearch.addActionListener(new ActionHandler());
		butAdd.addActionListener(new ActionHandler());
		butUpdate.addActionListener(new ActionHandler());
		butDelete.addActionListener(new ActionHandler());
		butBrowse.addActionListener(new ActionHandler());
		butRefresh.addActionListener(new ActionHandler());
		butCancelBrowse.addActionListener(new ActionHandler());
		butRefresh.setEnabled(false);
		butCancelBrowse.setEnabled(false);
	}
	//按钮动作事件监听处理类（私有内部类）：
		private class ActionHandler implements ActionListener{  
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==butFirst){              //“首记录”按钮
					goods = service.getFirstGoods();         //调用业务层方法获取首记录
					displayRecord(goods);                   //显示学生记录
				}
				else if(e.getSource()==butPre){          //“上记录”按钮
					goods = service.getPreviousGoods();
					displayRecord(goods);
				}
				else if(e.getSource()==butNext){         //“下记录”按钮
					goods = service.getNextGoods();
					displayRecord(goods);
				}
				else if(e.getSource()==butLast){         //“尾记录”按钮
					goods = service.getLastGoods();
					displayRecord(goods);
				}
				else if(e.getSource()==butSearch){       //按商品编号“查找”
					String gid = tfInputGid.getText().trim();
					if (gid.length()==0 || gid == null ){
						JOptionPane.showMessageDialog(null, "请输入商品编号再查找！");
						tfInputGid.requestFocus();
					}
					else{
						goods = service.searchGoods(gid);   //调用业务层查找方法
						if (goods == null){
							JOptionPane.showMessageDialog(null, 
									"商品编号为"+ gid + "的记录不存在！");
						}
						displayRecord(goods);
					}
				}
				else if(e.getSource()==butAdd){           //“添加”：先查找再添加
					String gid = tfInputGid.getText().trim();
					if (gid.length()==0 ){
						JOptionPane.showMessageDialog(null, "请输入要添加的商品编号！");
						tfInputGid.requestFocus();
					}
					else if (! gid.matches("[\\d]{8}") ){    //如果商品编号不是8位数字
						JOptionPane.showMessageDialog(null, "商品编号必须是8位数字！");
						tfInputGid.requestFocus();
					}
					else{
						goods = service.searchGoods(gid);      //调用业务层查找方法
						if (goods != null){
							JOptionPane.showMessageDialog(null, "该商品记录已存在！");
						}
						else{                    //显示添加记录模式对话框，添加记录
							AddGoodsDialog dialog = new AddGoodsDialog(thisObj, true, gid);
							goods = dialog.getGoods();          //获取添加的商品生对象
						}
						displayRecord(goods);
					}
				}
				else if(e.getSource()==butUpdate){         //“修改”:先查找再修改
					String gid = tfInputGid.getText().trim();
					if (gid.length()==0 || ! gid.matches("[\\d]{8}") ){
						JOptionPane.showMessageDialog(null, 
								"请输入要修改的商品编号！\n商品编号必须是8位数字！");
						tfInputGid.requestFocus();
					}
					else{
						goods = service.searchGoods(gid);      //调用业务层查找方法
						displayRecord(goods);
						if (goods == null){
							JOptionPane.showMessageDialog(null, 
									"该商品编号的记录不存在，无法修改！");
						}
						else{          //显示修改记录模式对话框，进行修改操作：
							UpdateGoodsDialog dialog = new UpdateGoodsDialog(thisObj, true, goods); 
							goods = dialog.getGoods();         //获取修改的商品对象
							displayRecord(goods);
						}
					}
				}
				else if(e.getSource()==butDelete){        //“删除”:先查找后删除
					String gid = tfInputGid.getText().trim();
					if (gid.length()==0 || ! gid.matches("[\\d]{8}") ){
						JOptionPane.showMessageDialog(null, 
								"请输入要删除的商品编号！\n商品编号必须是8位数字！");
						tfInputGid.requestFocus();
					}
					else{
						goods = service.searchGoods(gid);     //调用业务层查找方法
						displayRecord(goods);
						if (goods == null){
							JOptionPane.showMessageDialog(null, 
									"该商品编号的记录不存在！");
						}
						else{                             //显示确认框，选择是否删除
							int result =JOptionPane.showConfirmDialog(null, 
									"真的删除该记录吗？");
							if (result==JOptionPane.YES_OPTION){
								service.deleteGoods(goods);    //调用业务层删除方法
								goods = service.getCurrentGoods(); //获取删除后的当前商品
								displayRecord(goods);
							}
						}
					}
				}
					else if(e.getSource()==butBrowse){         //“浏览”所有记录
					butBrowse.setEnabled(false);
					butRefresh.setEnabled(true);
					butCancelBrowse.setEnabled(true);
					list = new JList<Goods>(GoodsService.getGoods()); //商品集所有元素
					scrollPane.setViewportView(list);       //设置滚动窗格视图
			        scrollPane.setVisible(true);
					labTitle.setVisible(true);
					labTotal.setText("记录总数：" + GoodsService.getTotal());
					thisObj.setVisible(true);
				}
				else if(e.getSource()==butRefresh){         //“刷新”浏览 
					butBrowse.setEnabled(false);
					butCancelBrowse.setEnabled(true);
					list = new JList<Goods>(GoodsService.reGetDBAllRecords());//重获库记录
					scrollPane.setViewportView(list);        //设置滚动窗格视图
					scrollPane.setVisible(true);
					labTitle.setVisible(true);
					labTotal.setText("记录总数：" + GoodsService.getTotal());
					thisObj.setVisible(true);
				}
				else if(e.getSource()==butCancelBrowse){   //“取消浏览”
					butCancelBrowse.setEnabled(false);
					butBrowse.setEnabled(true);
					butRefresh.setEnabled(true);
					scrollPane.setVisible(false);
					labTitle.setVisible(false);
					labTotal.setText("");
					thisObj.setVisible(true);
				}
			}
		}
				public void displayRecord(Goods goods){               //显示商品记录方法
					if (goods !=null ){                              //若有商品,则显示
						tfGid.setText(goods.getGid());
						tfCid.setText(goods.getCid());
						tfName.setText(goods.getName());
						tfPrice.setText(goods.getPrice().toString());
						tfUnit.setText(goods.getUnit());
					}
					else {                                        //否则清空文本框
						tfGid.setText(null);
						tfCid.setText(null);
						tfName.setText(null);
						tfPrice.setText(null);
						tfUnit.setText(null);
					}
				}
}
