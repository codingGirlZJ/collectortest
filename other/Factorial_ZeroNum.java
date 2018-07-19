package other;

import java.util.Scanner;

/**
 * 求一个数的阶乘末尾0的个数
 * @author ZJ
 *
 */
public class Factorial_ZeroNum {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = 1;
		System.out.println("请输入一个整数：（输入-1结束）");
		while(true){
			n = sc.nextInt();
			if(n == -1)
				break;
			//
			cal(n);
			cal2(n);
		}
	}
	
	/**
	 * 方法一：
	 * 求输入数n的阶乘中的所有因式分解中5的指数
	 * 即：求1,2,3...,n-1,n中所有因式分解后含5的个数，如其中25=5*5含2个5,35=7*5含1个5...
	 * 		因为最终是求整个阶乘中5的个数，因为2的个数肯定比5多
	 * @param n
	 */
	public static void cal(int n){
		int num = 0;
		for(int i = 5; i <= n; i += 5){
			int j = i;
			while(j % 5 == 0){	//能除尽5说明最终因式中含5，除5循环计算求出因式中共多少5
				num ++;
				j /= 5;
			}
		}
		System.out.println(num);
	}
	
	/**
	 * 方法二：
	 * Z = N/5 + N /(5*5) + N/(5*5*5).....直到N/(5的K次方)等于0
	 * 公式中 N/5表示不大于N的数中能被5整除的数贡献一个5，
	 * N/(5*5)表示不大于N的数中能被25整除的数再贡献一个5.......
	 * @param n
	 */
	public static void cal2(int n){
		int num = 0;
		while(n > 0){
			num += n / 5;
			n = n / 5;
		}
		System.out.println(num);
	}
}
