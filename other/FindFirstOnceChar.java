package other;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，找出其中第一个只出现了一次的字符
 * @author ZJ
 *
 */
public class FindFirstOnceChar {

	public static void main(String[] args){
		find("abaccdaedf");
	}
	
	public static void find(String s){
		if(s.length() > 0){
			//注意：Map的key可以为null，所以不能是基本类型，HashMap的key、value都可以为null，因此都不能是基本类型，而HashTable的key、value都不能为null
			//为基本类型会报错Syntax error, insert "Dimensions" to complete TypeArgument
			Map<String, Integer> map = new HashMap<String, Integer>();
			for(int i = 0; i < s.length(); i++){
				if(map.containsKey(String.valueOf(s.charAt(i)))){
					map.put(String.valueOf(s.charAt(i)), map.get(String.valueOf(s.charAt(i))) + 1);
				}else{
					map.put(String.valueOf(s.charAt(i)), 1);
				}
			}
			for(int j = 0; j < s.length(); j++){
				if(map.get(String.valueOf(s.charAt(j))) == 1){
					System.out.println(s.charAt(j));
					break;
				}
			}
		}
	}
}
