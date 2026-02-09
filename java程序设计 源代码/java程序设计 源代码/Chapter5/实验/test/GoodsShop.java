package chap5.test;

public class GoodsShop {
	private double money = 0;//卖出商品的收入
    public double sellGoods (Car car){//卖商品的行为方法，返回收入
        double price = car.getPrice();
        return money=money+price;}
        public double getmoney(){
         return money;
}
}
			System.out.print(courses[i]+"\t");			
		}
		System.out.println("平均分");
		
		for(int i=0; i<score.length; i++){
			//显示学生姓名
			System.out.print(students[i]+"\t");
			//显示该学生成绩
			for(int j=0; j<score[i].length; j++){
				System.out.print(score[i][j]+"\t");
			}
			//输出学生平均分
			System.out.printf("%.2f\n",studentAvg[i]);			
	    }
		//输出课程平均分
		System.out.print("平均分\t"); 
		for(int i=0; i<courseAvg.length; i++){
			System.out.printf("%.2f\t",courseAvg[i]);  
		}
		System.out.println();
	}
	
	/***
	 * 
	 * @param condition: 课程名或学生名
	 */
	private static void getMax(String condition){
		int i_index=-1,j_index=-1;
		
		//查找是否是学生姓名
		for(int i=0;i<students.length; i++){
			if (students[i].equalsIgnoreCase(condition)){
				i_index=i;
			}
		}
		if(i_index!=-1){//参数是学生姓名
			
			//求学生的最高分
			int max=0, max_col=0;			
			for(int j=0; j<score[i_index].length; j++){
				if(score[i_index][j]>max){
					max=score[i_index][j];
					max_col=j;
				}
			}
			System.out.printf("%s的%s课程分数最高：%d\n",condition,courses[max_col],max);
		}else{
			//查找参数是否是课程
			for(int j=0; j<courses.length; j++){
				if (courses[j].equalsIgnoreCase(condition)){
					j_index=j;
				}
			}
			if (j_index!=-1){  //参数是课程名
				//求课程的最高分
				int max=0, max_row=0;			
				for(int i=0; i<score.length; i++){
					if(score[i][j_index]>max){
						max=score[i][j_index];
						max_row=i;
					}
				}
				System.out.printf("%s的%s课程分数最高：%d\n",students[max_row],condition,max);
			}else{
				System.out.println("你输入的既不是课程名，也不是学生名");
			}
		}
	}
	/***
	 * 
	 * sort_condition:排序课程名称
	 */
	public static void sortByScore(String sort_condition){
		int i, j;
		int j_index=-1;

		for(j=0; j<courses.length; j++){
			if (courses[j].equalsIgnoreCase(sort_condition)){
				j_index=j;
			}
		}
		if(j_index==-1){
			System.out.println("没有这门课程");
			return;
		}
		
		//将成绩数据导到临时数组中		
		int[] score_temp= new int[STUDENT_NUM]; 
		for(i=0; i<score.length; i++){
			score_temp[i]=score[i][j_index];
		}
		//将姓名导入到临时数组中
		String[] students_temp=new String[STUDENT_NUM];
		for(i=0; i<students.length; i++){
			students_temp[i]=students[i];
		}
					
		//对score_temp数组冒泡法排序，同时所student_temp数据进行同步交换
		for(int k=0; k<score_temp.length-1; k++){
			for (int kk=0; kk<score_temp.length-k-1; kk++){
				if (score_temp[kk]>score_temp[kk+1]){
					//交换成绩
					int temp=score_temp[kk];
					score_temp[kk]=score_temp[kk+1];
					score_temp[kk+1]=temp;
					
					//交换成绩的同时交换姓名
					String tmp_str=students_temp[kk];  
					students_temp[kk]=students_temp[kk+1];
					students_temp[kk+1]=tmp_str;
				}
			}
		}
		
		//输出排序结果
		//1.输出抬头
		System.out.print("名次\t");
		System.out.print("姓名\t");
		System.out.print(courses[j_index]+"\t");
		System.out.println();
		
		//2.输出数据
		for(int k=0; k<score_temp.length; k++){
			System.out.print((k+1)+"\t");
			System.out.print(students_temp[k]+"\t");  //学生姓名
			System.out.println(score_temp[k]);  //学生成绩
		}
	}
}
