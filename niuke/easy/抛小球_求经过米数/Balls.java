package niuke.easy.抛小球_求经过米数;

import java.util.Scanner;

/**
 * 两种解法：
 * 1.极限求和：3*(A+B+C+D)-->每次都是前一次的一半，最后求a1+2a1(1/2+...+(1/2)的n次方)，等比数列求极限得1
 * 2.递归求：注意不能用int取整否则结果会小，用double，但这样时间复杂度高
 * （递归求最后结果转int之前一定要四舍五入，否则得出结果比正确结果小1）
 * @author zj
 *
 */
public class Balls {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int A = sc.nextInt();
		int B = sc.nextInt();
		int C = sc.nextInt();
		int D = sc.nextInt();
		
		//结果一定记得要四舍五入，要求是整数，若不四舍五入直接转int会比结果小
		int result = (int)Math.round(calcDistance(A, B, C, D));
		System.out.println(result);
	}
	
    public static double calcDistance(int A, int B, int C, int D) {
        // write code here
        return calSingleDistance(A) + calSingleDistance(B) + calSingleDistance(C) + calSingleDistance(D);
    }
    
    public static double calSingleDistance(int n1){
    	double n = n1;		//必须用double，否则计算出来的结果会小
        double sum = n;
        while(n > 0){
            n = n/2.0;
            sum += n * 2;
        }
        return sum;
    }
}