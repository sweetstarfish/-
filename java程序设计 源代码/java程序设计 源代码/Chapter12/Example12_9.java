package chapter12;
import javax.swing.JOptionPane;
public class Example12_9 {

	public static void main(String[] args) {
		String str;
		str = JOptionPane.showInputDialog("请输入字符串：");       //输入框
		int option = JOptionPane.showConfirmDialog(null, 
				"确定要删除"+ str +"吗？");                           //确认框
		if (option == JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(null, "您选择了确定删除");  //消息框
			str = null;
		}else {
			JOptionPane.showOptionDialog(null, "你确定不删除吗", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, new String[] {"YES", "NO"}, "YES");
		}
	}

}
