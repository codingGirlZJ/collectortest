package niuke.jianzhioffer;

import java.util.ArrayList;

public class PrintMatrix {

	public static void main(String[] args) {
		int[][] matrix = {{1,2},{3,4}};
		ArrayList<Integer> list = printMatrix(matrix);
		for(int i = 0; i < list.size();i++){
			System.out.print(list.get(i) + " ");
		}
	}
	public static ArrayList<Integer> printMatrix(int [][] matrix) {
        if(matrix.length == 0)
            return null;
        int flag = 0;    //0,1,2,3分别表示右下左上
        int n = matrix.length;
        int m = matrix[0].length;
        boolean[][] f = new boolean[n][m];
        for(int i = 0; i < n; i++){
        	for(int j = 0; j < m; j++){
        		f[i][j] = true;
        	}
        }
        int k = 0, i = 0, j = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(k < m * n){
            k++;
            System.out.println(k + "-" + i + "-" + j);
            f[i][j] = false;
            list.add(matrix[i][j]);
            switch(flag){
                case 0:
                    if(j == m-1 || !f[i][j+1]){
                        flag = 1;
                        i++;
                    }else{
                        j++;
                    }
                    break;
                case 1:
                    if(i == n-1 || !f[i+1][j]){
                        flag = 2;
                        j--;
                    }else{
                        i++;
                    }
                    break;
                case 2:
                    if(j == 0 || !f[i][j-1]){
                        flag = 3;
                        i--;
                    }else{
                        j--;
                    }
                    break;
                case 3:
                    if(i == 0 || !f[i-1][j]){
                        flag = 0;
                        j++;
                    }else{
                        i--;
                    }
                    break;
            }
        }
        return list;
    }
}
