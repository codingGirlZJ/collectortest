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
 * 结果错误：因为int类型小了，结果溢出了，所以错误，改成long后应该就正确了
 * @author ZJ
 *
 */
public class HeChangTuan {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[n];
		for(int i = 0; i < n; i++){
			a[i] = sc.nextInt();
		}
		int k = sc.nextInt();
		int d = sc.nextInt();
		int result = calProduct(n, a, k, d);
		System.out.println("result: " + result);
	}
	
	public static int calProduct(int n, int[] a, int k, int d){
		int result = Integer.MIN_VALUE;
		
		int[][] dpMax = new int[n][k];		//dp[i][j]表示以数组中a[i]为结尾元素时，长度为j+1的最大乘积子序列的乘积值
		int[][] dpMin = new int[n][k];
		for(int i = 0; i < n; i++){
			dpMax[i][0] = a[i];
			dpMin[i][0] = a[i];
		}
		for(int i = 0; i < n; i++){
			for(int j = 1; j < k && j <= i; j++){
				int max = Integer.MIN_VALUE;
				int min = Integer.MAX_VALUE;
				for(int m = j-1; m < i; m++){				//求前面i-1个元素中长度为j的乘积值的最大值
//					max = Math.max(max, dpMax[m][j-1]);
					if(max < dpMax[m][j-1] && (i-m) <= d){	//仅当前一个元素的序号m与当前最后元素的序号i的差不超过d时取最大
						max = dpMax[m][j-1];
					}
					if(min > dpMin[m][j-1] && (i-m) <= d){
						min = dpMin[m][j-1];
					}
				}
				dpMax[i][j] = Math.max(max * a[i], min * a[i]);		//第i个元素结尾，长度为j+1的最大乘积子序列的乘积值=前i-1个元素中长度为j的乘积值的最大值乘以a[i]
				dpMin[i][j] = Math.min(max * a[i], min * a[i]);
				System.out.println("dpMax[" + i + "][" + j + "]:" + dpMax[i][j] + "..." + max * a[i] + "..." + min * a[i]);
			}
			result = Math.max(result, dpMax[i][k-1]);
			System.out.println("result-" + i + ":" + result);
		}
		return result;
	}
}
