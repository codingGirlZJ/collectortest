package other;

import java.util.Scanner;

/**
 * 给定一个数x（x>5），找到该数与3、4之间的关系。
 * 关系如下：x=3*n+4*m，然后找到|n-m|的最小值。
 * 其中：6=3*2+4*0
        7=3*1+4*1
		8=3*0+4*2
		9=3*3+4*0
 * @author ZJ
 *
 */
public class ThreeAndFour {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = 1;
		System.out.println("请输入一个>5的整数：（输入-1结束）");
		while(true){
			num = sc.nextInt();
			if(num == -1)
				break;
			calRelation(num, 0, 0, Integer.MAX_VALUE);
		}
	}
	
	/**
	 * 计算num与3和4的关系，并计算|n-m|的最小值
	 * @param num	输入的数
	 * @param n		与3的关系
	 * @param m		与4的关系
	 * @param min	|n-m|的最小值
	 */
	public static void calRelation(int num, int n, int m, int min){
		//能除3除尽，说明出现一种可能结果
		if(num % 3 == 0){
			int n1 = num / 3;
			n += n1;
			min = Math.min(min, Math.abs(n-m));
			System.out.println(n + "," + m);
			//最初计算肯定是n>=m，当n<m后，求得差绝对值>min后，后面的结果的差值只会比min大，因此如果不是要计算所有结果，到此就可以计算出min的值了
			if(n < m && min < Math.abs(n-m)){
				System.out.println("----min:" + min);
				return;
			}
			//如果最后除3只剩1或不到1了，那么所有可能的结果已经计算完了，可以结束输出min了
			if(n1 <= 1){
				System.out.println("min:" + min);
			}else{		//否则，计算其他可能结果
				n -= n1;
				num -= 4;
				m ++;
				calRelation(num, n, m, min);
			}
		}else{	//除不尽3，递归计算m++后的可能结果
			num -= 4;
			if(num >= 3){
				m ++;
				calRelation(num, n, m, min);
			}else{
				System.out.println("min:" + min);
			}
		}
	}
}
