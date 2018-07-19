package sfjsrmjd;

import java.util.Arrays;

/**
 * 排列（permutation）
 * 用1,2,3...9组成3个三位数abc，def和ghi，每个数字恰好使用一次
 * 要求abc:def:ghi = 1:2:3。输出所有解
 * @author ZJ
 *
 */
public class permutation {

	public static void main(String[] args) {
		int[] n = new int[10];	//用n[i]=0表示i这个数字未出现过，n[i]=1表示i这个数字已出现过了
		for(int i = 123; i < 329; i++){
			Arrays.fill(n, 0);
			int a = i/100, b = (i/10)%10, c = i%10;
			if(a != b && a != c && b != c){	//abc互相不相等
				n[a] = 1; n[b] = 1; n[c] = 1;
				int j = i*2;
				int d = j/100, e = (j/10)%10, f = j%10;
				if(d != e && d != f && e != f && 
						n[d]==0 && n[e]==0 && n[f]==0){	//def互相不等且未出现过
					n[d] = 1; n[e] = 1; n[f] = 1;
					int k = i*3;
					int g = k/100, h = (k/10)%10, l = k%10;
					if(g != h && g != l && h != l && 
							n[g]==0 && n[h]==0 && n[l]==0){	//ghl互相不等且未出现过
						System.out.println(i + "," + j + "," + k);
					}
				}
			}
		}
	}
}
