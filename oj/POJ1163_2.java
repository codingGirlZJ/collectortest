package oj;

import java.util.*;

public class POJ1163_2 {
	static int n;
	static int[][] num;	//用一个二维数组存三角形
	static int[] cache;	//用一个一维数组来存结果和
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		num = new int[n][n];
		cache = new int[n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j <= i; j++){
				num[i][j] = sc.nextInt();
			}
		}
		for(int i = 0; i < n; i++){
			cache[i] = -1;
		}
		//非递归方式：从底层倒推
		for(int i = 0; i < n; i++){
			cache[i] = num[n-1][i];
		}
		for(int i = n-2; i >= 0; i--){
			for(int j = 0; j <= i; j++){
				cache[j] = Math.max(cache[j], cache[j+1]) + num[i][j];
			}
		}
		System.out.println(cache[0]);
	}
}
