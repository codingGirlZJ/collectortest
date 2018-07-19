package niuke.jianzhioffer;

import java.util.Scanner;

/**
 * 把只包含因子2、3和5的数称作丑数（Ugly Number）。
 * 例如6、8都是丑数，但14不是，因为它包含因子7。 
 * 习惯上我们把1当做是第一个丑数。
 * 求按从小到大的顺序的第N个丑数。
 * @author zj
 *
 */
public class GetUglyNumber {

	/**
	 * 每求一个丑数，其实就是求在已求出的丑数中*2最小的和*3最小的和*5最小的三者中最小的那个
	 * 	用t1，t2，t3记录已与2,3,5相乘的丑数数组的下标，因为dp[t1]是丑数，那么dp[t1]*2也一定是丑数，
	 * 	同理dp[t2],dp[t3]是丑数，dp[t2]*3，dp[t3]*5也是丑数，t1,t2,t3都从0开始，也就是dp[0]*2,*3,*5中选最小的，
	 * 	然后再比较dp[1]*2和dp[0]*3,dp[0]*5中最小的，然后再比较dp[1]*2,dp[1]*3和dp[0]*5中最小的……
	 * @param index
	 * @return
	 */
	public static int GetUglyNumber_Solution(int index) {
		if(index == 0)
            return 0;
		int[] dp = new int[index];	//dp[i]表示第i+1个丑数
		int t1 = 0, t2 = 0, t3 = 0;	//分别表示应该与2,3,5相乘的dp丑数数组的下标
		dp[0] = 1;					//1是第1个丑数
		for(int i = 1; i < index; i++){
			/*
			 * 第i个丑数应是：
			 * 当前已有丑数中与2相乘的最小数，
			 * 当前已有丑数中与3相乘的最小数，
			 * 当前已有丑数中与5相乘的最小数
			 * 三者中最小的那个，并且之后要将与相应的2,3,5相乘的丑数下标更新
			*/
			dp[i] = Math.min(dp[t1]*2, Math.min(dp[t2]*3, dp[t3]*5));
			if(dp[i] == dp[t1] * 2)	//如果dp[i]是第t1个丑数乘2，那么就将第t1+1个丑数乘2加入到下一次比较中
				t1++;
			if(dp[i] == dp[t2] * 3)	//如果dp[i]是第t2个丑数乘3，那么就将第t2+1个丑数乘3加入到下一次比较中
				t2++;
			if(dp[i] == dp[t3] * 5)	//如果dp[i]是第t3个丑数乘5，那么就将第t3+1个丑数乘5加入到下一次比较中
				t3++;
		}
        return dp[index-1];
    }
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextInt()){
			int n = sc.nextInt();
			int r = GetUglyNumber_Solution(n);
			System.out.println("第" + n + "个丑数位：" + r);
		}
	}
}
