package other;

/**
 * 数组每行求和
 * @author ZJ
 *
 */
public class ArrayRowSum {

	public static void main(String[] args){
		int[][] arr = {{1,2,3},{4,5,6},{7,8,9}};
		int m = arr.length;
		int n = arr[0].length;
		for(int i = 0; i < m; i++){
			int rowSum = 0;
			for(int j = 0; j < n; j++){
				rowSum += arr[i][j];
			}
			System.out.println("第" + (i + 1) + "行的和为：" + rowSum);
		}
	}
}
