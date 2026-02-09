package chapter13;

/** 中间的业务层 */
import java.util.*;
public class GoodsService {                  //业务类
	private static GoodsDataAccess dataAccess = new GoodsDataAccess();
    //与数据层关联
private static Vector<Goods> sgoods = new Vector<Goods>();   //商品集
private static int total ;                             //商品集元素总数
private static int goodsIndex ;                         //商品集当前元素索引

public GoodsService(){                              //无参数构造方法
	sgoods.clear();
	sgoods = dataAccess.getAllRecords();                   //调用数据层方法
total = sgoods.size();
goodsIndex = 0;
}
public static int getTotal(){                         //获取记录总数
	return total;
}

public static Vector<Goods> getGoods(){                   //获取所有商品记录
	if (sgoods.isEmpty()){
		sgoods.clear();
		sgoods = dataAccess.getAllRecords();               //调用数据层方法
		total = sgoods.size();
	}
	return sgoods;
}

public static Vector<Goods> reGetDBAllRecords(){        //重获所有库记录(刷新)
	sgoods.clear();
	sgoods = dataAccess.getAllRecords();                  //调用数据层方法
	total = sgoods.size();
	return sgoods;
}
public Goods getFirstGoods(){                              //获取商品集首记录
	if (sgoods.size()>0 ){
		goodsIndex = 0;
		return sgoods.get(goodsIndex);
	}
	return null;
}

public Goods getPreviousGoods(){                           //获取商品集上一记录
	if (sgoods.size()>0 ){
		goodsIndex--;
		if(goodsIndex <= -1) {
			goodsIndex = total - 1;
		}
		return sgoods.get(goodsIndex);
	}
	return null;
}

public Goods getNextGoods(){                               //获取商品集下一记录
	if (sgoods.size()>0 ){
		goodsIndex++;
		if(goodsIndex >= total) {
			goodsIndex = 0;
		}
		return sgoods.get(goodsIndex);
	}
	return null;
}

public Goods getLastGoods(){                               //获取商品集尾记录
	if (sgoods.size()>0 ){
		goodsIndex = total - 1;
		return sgoods.get(goodsIndex);
	}
	return null;
}

public Goods getCurrentGoods(){                            //获取商品集当前记录
	if(goodsIndex>=0 && goodsIndex < total) {
		return sgoods.get(goodsIndex);
	}
	else{
		return null;
	}
}
//在商品集中查找对象，返回元素索引。如果对象为空，或不在商品集，则索引为-1
	public int getIndex(Goods goods){                          //查找商品集元素
		int index = -1;
		if (goods != null){
			for(int i=0; i<total; i++){
				if(goods.getGid().equals(sgoods.get(i).getGid())){
					index = i;
					break;
				}
			}
		}
		return index;
	}

	//在商品集中添加一个元素(非空的商品对象)：
	public void addGoodsObj(Goods goods){                      //添加商品集元素
		if (goods != null){
			sgoods.add(goods);                                 //添加元素
			Collections.sort(sgoods);                        //商品集元素按商品编号排序
			goodsIndex = getIndex(goods);                     //改变商品集元素索引
			total ++;
		}
	}
	//在商品集中修改元素(商品对象)：
		public void updateGoodsObj(Goods goods){                   //修改商品集元素
			int index = getIndex(goods);
			if (index>=0 && index<total){                     //如果对象存在
				sgoods.set(index, goods);                         //用给定元素替换原元素
			}
		}

		//在商品集中删除元素(商品对象)：
		public void deleteGoodsObj(Goods goods){                  //删除商品集元素
			int index = getIndex(goods);
			if (index>=0 && index<total){                    //如果对象存在
				sgoods.remove(index);                          //则删除该元素
				total --;
			}
		}
		//按商品编号查找商品库记录，找不到，返回null
		public Goods searchGoods(String gid){                   //按商品编号查找商品库记录
			if (gid.length()==0 || gid == null ){
				return null;
			}
			else{
				Goods goods = dataAccess.searchRecord(gid);
				goodsIndex = getIndex(goods);                   //改变商品集元素索引
				return goods;
			}
		}

		//添加商品库记录：成功返回1；记录已存在，返回0；无法添加(如空记录)，返回-1
		public int addGoods(Goods goods){                         //添加商品库记录
			int result = 0;
			if (goods == null){
				result = -1;
			}
			result = dataAccess.addRecord(goods);              //调用数据层方法添加记录
			if(result==1){                                   //如果成功添加
				addGoodsObj(goods);                              //则把对象添加到商品集
			}
			return result;
		}
		//修改商品库记录：成功返回1；不成功，返回0；无法修改(如空记录)，返回-1
		public int updateGoods(Goods goods){                      //修改商品库记录
			int result = 0;
			if (goods == null){
				result = -1;
			}
			result = dataAccess.updateRecord(goods);           //调用数据层方法修改记录
			if (result == 1){                                //如果成功修改库记录
				updateGoodsObj(goods);                           //则修改商品集相应元素
			} 
			return result;
		}

		//删除商品库记录：成功返回1；不成功，返回0；无法删除(如空记录)，返回-1
		public int deleteGoods(Goods goods){                      //删除商品库记录
			int result = 0;
			if (goods == null){
				result = -1;
			}
			result = dataAccess.deleteRecord(goods);           //调用数据层方法删除记录
			if(result==1){                                   //如果成功删除
				deleteGoodsObj(goods);                           //则删除商品集相应元素
			} 
			return result;
		}
		
}
