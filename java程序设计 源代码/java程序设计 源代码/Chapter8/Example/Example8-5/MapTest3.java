package Chapter8;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class MapTest3 {
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
		System.out.println("请输入一句英语，单词间用空格隔开：");
		String sentence = sc.nextLine();
		String[] arr = sentence.split(" ");
		// 键代表着单词，值代表着次数
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < arr.length; i++) {
			if (!map.containsKey(arr[i])) {
				map.put(arr[i], 1);
			} else {
				// 说明map中，存在该元素
				int num = map.get(arr[i]);
				map.put(arr[i], ++num);
			}
		}
		System.out.println("统计单词出现的个数，结果如下：");
		Set<String> set = map.keySet();
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Integer value = map.get(key);
			System.out.println(key + "=" + value);
		}
	}
}