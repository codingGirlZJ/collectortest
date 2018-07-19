package niuke.easy.走楼梯_一阶两阶;

import java.util.Scanner;

/**
 * 有一楼梯共m级，刚开始时你在第一级，若每次只能跨上一级或者二级，要走上m级，共有多少走法？
 * 注：规定从一级到一级有0种走法。
 * 给定一个正整数int n，请返回一个数，代表上楼的方式数。
 * 保证n小于等于100。为了防止溢出，请返回结果Mod 1000000007的值。
 * 测试样例：
 * 3
 * 返回：
 * 2
 * @author zj
 *
 */
public class GoUpstairs {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int result = countWays(n);
		System.out.println(result);
	}
	
	/**
	 * 用数组dp保存走到第i-1级的走法数
	 * 有dp[i] = dp[i-1] + dp[i-2] (0<=i<n-1,0代表第一级楼梯，n-1代表最后一级楼梯)
	 * @param n
	 * @return
	 */
    public static int countWays(int n) {
        // write code here
        int[] dp = new int[n];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i < n; i++){
            long tmp = (dp[i-1] + dp[i-2])%1000000007;
            dp[i] = (int)tmp;
        }
        return dp[n-1];
    }
}