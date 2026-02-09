public class TestTwo {
 	public static int[] number;
	public static void main(String[] args) {
		number=new int[5];
		Scanner input=new Scanner(System.in);
		System.out.println("请输入信息。");
		try {
			
			for(int i=0;i<6;i++) {
				input=new Scanner(System.in);
				if(i==5) 
					throw new Exception("请输入字符串。");
				number[i]=input.nextInt();				
			}
		} catch (InputMismatchException e) {
			System.err.println("请输入正确输入5个信息，分别有字母、数字过程的。");
		}catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}finally {
			input.close();
			System.out.println("感谢使用本系统。");
		}
	}
}
