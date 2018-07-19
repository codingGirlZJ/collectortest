package sfjsrmjd;

import java.util.Arrays;

/**
 * 开灯问题
 * 有n盏灯，编号为1~n，第1个人把所有灯打开，第2个人按下所有编号为2的倍数的开关（这些灯将被关掉），
 * 第3个人按下所有编号为3的倍数的开关（其中关掉的灯将被打开，开着的灯将被关掉）
 * 以此类推。一共k个人，问：最后又哪些灯开着？
 * 输入：n和k。k<=n<=1000
 * 输出：开着的灯编号
 * 例如：输入7 3，输出：1 5 6 7
 * @author ZJ
 *
 */
public class KaiDengWenTi {

	public static int[] a;
	public static void main(String[] args) {
		int n = 7;
		int k = 3;
		int first = 1;	//第1个不输出空格只输出灯编号，其余都输出为空格+灯编号
		a = new int[n+1];
		if(n == 0 && k == 0){	//如果没有人没有灯则全为-1，即全关
			Arrays.fill(a, -1);
			return;
		}
		Arrays.fill(a, 1);	//第1个人奖所有灯都打开
		kaiguan(n, k);
		for(int i = 1; i < n+1; i++){
			if(a[i] == 1){	//最后为1的即为开着的灯，为-1的即为关闭的灯
				if(first == 1){
					first = 0;
				}else{
					System.out.print(" ");
				}
				System.out.print(i);
			}
		}
	}
	
	public static void kaiguan(int n, int k){
		for(int i = 2; i <= k; i++){	//从第2个人起
			for(int j = 1; j <= n; j++){
				if(j%i == 0){	//按掉是自己倍数的灯
					a[j] *= -1;
				}
			}
		}
	}
}
