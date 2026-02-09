package chap3.test;

import java.util.Scanner;

/***
 * 
 * 学生成绩查询系统
 *
 */
public class ScoreMISTest {
	static final int STUDENT_NUM=6;
	static final int COURSE_NUM=5;
		
	static String[] students={"zhang","wang","li","zhao","liu","song"};
	static String[] courses={"C","Java","mySQL","Linux","HTML"};
	static int[][] score= new int[STUDENT_NUM][COURSE_NUM];
	
	public static void main(String[] args)	{		
		initScore();	//初始化学生成绩
	 	show();         //显示课程、学生及成绩
	 	run();          //开始接收控制台命令
	}
	
	//响应控制台命令
	public static void run(){
		Scanner scn=new Scanner(System.in);		
		
		while(true){
			System.out.print("请输入命令:");	
			String command=scn.next();
			
			if(command.equalsIgnoreCase("avg")){//"avg"命令需要一个参数
				String parameter=scn.next();
				avg(parameter);
				
			}
			if(command.equalsIgnoreCase("get")){//"get"命令需要两个参数
				String parameter1=scn.next();
				String parameter2=scn.next();
				get(parameter1,parameter2);
			}
			if(command.equalsIgnoreCase("sort")){//"sort"命令需要一个参数
				String parameter=scn.next();		
				sortByScore(parameter);
			}
			if(command.equalsIgnoreCase("max")){//"max"命令需要一个参数
				String parameter=scn.next();		
				getMax(parameter);
			}
			if (command.equalsIgnoreCase("exit")){//退出查询系统
				System.out.println("退出查询系统！byebye！");
				System.exit(0);
			}
		}		
	}
	
	public static void initScore(){  //用随机数初始化成绩		
		for(int i=0; i<score.length; i++)
			for(int j=0; j<score[i].length; j++)
				score[i][j]=(int)(Math.random()*100);
	}

	public static void show(){  //显示成绩		
		System.out.print("\t");  //留出显示姓名的位置
		//输出课程名称
		for(int i=0; i<courses.length; i++){
			System.out.print(courses[i]+"\t");
		}
		System.out.println();
		
		for(int i=0; i<score.length; i++){
			//显示学生姓名
			System.out.print(students[i]+"\t");
			//显示该学生成绩
			for(int j=0; j<score[i].length; j++){
				System.out.print(score[i][j]+"\t");
			}
			System.out.println();
	  }
	}
	/***
	 * condition1:学生姓名
	 * condition2:课程名称
	 */
	public static void get(String condition1, String condition2){
		
		int i_index=-1,j_index=-1;

		//查找是否存在该学生i_index
		for(int i=0; i<students.length; i++){
			if (students[i].equalsIgnoreCase(condition1)){
				i_index=i;
			}
		}
		if(i_index!=-1){  //有此人，继续查找是否有此课程j_index
			for(int j=0; j<courses.length; j++)
				if (courses[j].equalsIgnoreCase(condition2)){
					j_index=j;					
				}
		}else{
			System.out.println("没有 "+ condition1+ " 这个人");
			return;
		}
	
		if(j_index!=-1){
			System.out.println(condition1+"的"+condition2+"的成绩是："+score[i_index][j_index]);
		}else{
			System.out.println(condition1+"没有 "+condition2+" 这门课程");
			return;
		}
	}
	/***
	 * 
	 * condition:求平均分的参数（学生姓名/课程名称）
	 */
	public static void avg(String condition){
		int i_index=-1,j_index=-1;
		
		//查找是否是学生姓名
		for(int i=0;i<students.length; i++){
			if (students[i].equalsIgnoreCase(condition)){
				i_index=i;
			}
		}
		if(i_index!=-1){//参数是学生姓名
			double sum=0;
			
			//求学生的平均分
			for(int j=0; j<score[i_index].length; j++){
				sum=sum+score[i_index][j];
			}
			System.out.printf("%s的平均分是：%.2f\n",condition,sum/COURSE_NUM);
		}else{
			//查找参数是否是课程
			for(int j=0; j<courses.length; j++){
				if (courses[j].equalsIgnoreCase(condition)){
					j_index=j;
				}
			}
			if (j_index!=-1){  //参数是课程名
				double sum=0;
			    for(int i=0; i<score.length; i++){
					 sum=sum+score[i][j_index];
				}
				System.out.printf("%s的平均分是：%.2f\n",condition,sum/STUDENT_NUM);
			}else if(condition.equalsIgnoreCase("all")){  //查询所有学生，所有课程的平均分
				double[] courseAvg= new double[COURSE_NUM];  //保存课程平均分的数组
				double[] studentAvg = new double[STUDENT_NUM];  //保存学生平均成绩的数组
				
				//统计学生（每行）的平均分
				for(int i=0; i<score.length; i++){
					double sum=0; 
					for(int j=0; j<score[i].length; j++){
						sum+=score[i][j];
					}
					studentAvg[i]=sum*1.0/COURSE_NUM;
				}
				
				//统计学生（每行）的平均分
				for(int j=0; j<score[0].length; j++){
					double sum=0; 
					for(int i=0; i<score.length; i++){
						sum+=score[i][j];
					}
					courseAvg[j]=sum*1.0/STUDENT_NUM;
				}
				
				//打印输出总成绩表
				showAvgAll(studentAvg,courseAvg);
				
			}else{
				System.out.println("你输入的既不是课程名，也不是学生名，也不是all");
			}			
		}
	}
	
	//打印输出带平均成绩的总成绩表
	private static void showAvgAll(double[] studentAvg, double[] courseAvg){
		System.out.print("\t");  //留出显示姓名的位置
		
		//输出课程名称
		for(int i=0; i<courses.length; i++){
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
