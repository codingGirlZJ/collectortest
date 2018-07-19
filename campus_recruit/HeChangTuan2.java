package campus_recruit;

import java.util.Scanner;

/**
 * 题目描述
 * 		有 n 个学生站成一排，每个学生有一个能力值，牛牛想从这 n 个学生中按照顺序选取 k 名学生，要求相邻两个学生的位置编号的差不超过 d，
	使得这 k 个学生的能力值的乘积最大，你能返回最大的乘积吗？
 * 输入描述:
 * 		每个输入包含 1 个测试用例。每个测试数据的第一行包含一个整数 n (1 <= n <= 50)，表示学生的个数，接下来的一行，包含 n 个整数，
	按顺序表示每个学生的能力值 ai（-50 <= ai <= 50）。接下来的一行包含两个整数，k 和 d (1 <= k <= 10, 1 <= d <= 50)。
 * 输出描述:
 * 		输出一行表示最大的乘积。
 * 示例1
 * 输入
 * 		3
 * 		7 4 7
 * 		2 50
 * 输出
 *		49
 * 思路：
 * 	动态规划：
 * 		1. 给定n个元素，寻找k个元素使乘积最大，可以从这k个元素中最后一个元素所在位置入手，
 * 		2. 问题分解：当以第i个元素作为最后一个元素时，长度为j的最大乘积子序列的乘积值就等于：前i-1个元素中，长度为j-1的最大乘积子序列的乘积值*当前元素值
 * 		3. 用二维数组dp[n+1][k+1]来存储，那么要求的结果就是{dp[n][k]...dp[k][k]}中的最大值，即这n个元素中，长度为k的最大乘积子序列的乘积值
 * 		4. 注意约束条件：相邻位置编号差<=d，即在求前i-1个元素长度为j-1的最大乘积子序列的乘积值时，要求该子序列最后一个元素与i只差<=d
 * 		5. 注：结果及数组都需用long类型，否则容易溢出得出错误结果
 * @author ZJ
 *
 */
public class HeChangTuan2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[n];
		for(int i = 0; i < n; i++){
			a[i] = sc.nextInt();
		}
		int k = sc.nextInt();
		int d = sc.nextInt();
		long result = calProduct(n, a, k, d);
		System.out.println("result: " + result);
	}
	
	public static long calProduct(int n, int[] a, int k, int d){
		long result = Long.MIN_VALUE;
		
		long[][] dpMax = new long[n+1][k+1];							//方便理解，都额外加了1，dp[i][j]表示以数组中第i个元素即a[i-1]为结尾元素时，长度为j的最大乘积子序列的乘积值
		long[][] dpMin = new long[n+1][k+1];
		for(int i = 1; i < n+1; i++){								//初始化，当以第i个 元素为结尾，长度为1的最大乘积子序列的乘积值其实就是a[i]，即只有一个元素是就是i的能力值
			dpMax[i][1] = a[i-1];
			dpMin[i][1] = a[i-1];
		}
		
		for(int i = 2; i < n+1; i++){								//分别计算以第i个元素作为结尾元素时，长度为j=2...i-1且j <= k的最大乘积子序列的乘积值
			for(int j = 2; j <= i && j <= k; j++){
				long max = Long.MIN_VALUE;						//用来保存前面i-1个元素中，长度为j-1的最大乘积子序列的乘积最大值
				long min = Long.MAX_VALUE;						//用来保存前面i-1个元素中，长度为j-1的最大乘积子序列的乘积最小值，考虑到第i个元素可能为负数的情况
				for(int m = Math.max(j-1, i-d); m < i; m++){			//循环取前i-1个元素中，长度为j-1且与第i个元素的距离<=d的最大、最小乘积值
					if(max < dpMax[m][j-1]){
						max = dpMax[m][j-1];
					}
					if(min > dpMin[m][j-1]){
						min = dpMin[m][j-1];
					}
				}
				dpMax[i][j] = Math.max(max * a[i-1], min * a[i-1]);
				dpMin[i][j] = Math.min(max * a[i-1], min * a[i-1]);
				System.out.println("dpMax[" + i + "][" + j + "]:" + dpMax[i][j] + "..." + max * a[i-1] + "..." + min * a[i-1]);
			}
		}
		for(int i = k; i <= n; i++){
			result = Math.max(result, dpMax[i][k]);
		}
		
		return result;
	}
}
