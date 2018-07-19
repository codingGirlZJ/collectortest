package oj.leetcode.problem_003_medium_LongestSubstringWithoutRepeatingCharacters;

import java.util.Arrays;

/**
 * 3. Longest Substring Without Repeating Characters
 * Given a string, find the length of the longest substring without repeating characters.
 * Examples:
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * Given "bbbbb", the answer is "b", with the length of 1.
 * Given "pwwkew", the answer is "wke", with the length of 3. 
 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * @author ZJ
 *
 */
public class demo1 {
	public static int lengthOfLongestSubstring(String s) {
        int a[] = new int[256];                      //用一个int数组表示该字符是否出现过，记录它上次出现的索引
        Arrays.fill(a, -1);                          //用-1初始化a数组
        int len = 0, gLen = 0;                       //len是当前长度，gLen是全局最长
        if(s.length() == 0)
            return 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(a[(int)c] != -1){                     //该字符之前出现过了
                i = a[(int)c];                       //从该字符上一次出现的位置继续扫描
                len = 0;                             //清空len长度
                Arrays.fill(a, -1);                  //重置a数组
            }else{                                   //该字符是第一次出现
                a[(int)c] = i;                       //将其出现位置的索引存入a数组相应位置中
                len++;                               //len加1
                gLen = Math.max(gLen, len);          //全局最长=当前长度和上次记录的全局最长中的较大值
                // System.out.println(a[(int)c] + "-" + c + "-" + (int)c);
            }
        }
        return gLen;
    }
	
	public static void main(String[] args) {
		int len = lengthOfLongestSubstring("abcabcbb");
		System.out.println(len);
	}
}
