package collectortest.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		
		String str1 = "asdadas";
		String str2 = "da";
		String str3 = str1.substring(str1.indexOf(str2),str1.length()-(str1.length()-str2.length()-str1.indexOf(str2)));
//		System.out.println(str3);
		int[][] ma = new int[4][5];
		int m = ma.length;
		int n = ma[0].length;
		System.out.println(m + "--" + n);
		
		int i = 222;
		Integer j = 222;
		System.out.println(i == j);
	}
}