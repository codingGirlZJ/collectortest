package oj;

import java.util.Arrays;
import java.util.Scanner;

public class POJ1163 {
	
	static int n;
	static int[][] num;
	static int[][] cache;

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();	//输入三角形行数n
		num  = new int[n][n];
		cache = new int[n][n];
		
		for(int i = 0; i < n; i++){	//输入三角形保存在二维数组num中
			for(int j= 0; j <= i; j++){	//注意是三角形的
				num[i][j] = sc.nextInt();
			}
		}
		for(int i = 0; i < n; i++){	//初始化保存结果的二维数组
			Arrays.fill(cache[i], -1);
		}
		
		//递归方式
		System.out.println(MaxSum(0,0));
	}
	
	public static int MaxSum(int i, int j){
		if(cache[i][j] != -1){
			return cache[i][j];
		}
		if(i == (n-1)){
			cache[i][j] = num[i][j];
		}else{
			int x = MaxSum(i+1, j);
			int y = MaxSum(i+1, j+1);
			cache[i][j] = Math.max(x, y) + num[i][j];
		}
		return cache[i][j];
	}
}
