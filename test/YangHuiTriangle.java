package test;

import java.util.Arrays;
import java.util.Scanner;
/**
 * 杨辉三角形
 * @author ZJ
 *
 */
public class YangHuiTriangle {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		generator(n);
	}

	private static void generator(int n) {
		int[][] arr = new int[n][n];
		for(int i = 0; i < n; i++){
			Arrays.fill(arr[i], 0);
			for(int j = 0; j <= i; j++){
				if(j == 0 || i == 0){
					arr[i][j] = 1;
				}else{
					arr[i][j] = arr[i-1][j-1] + arr[i-1][j]; 
				}
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
}
