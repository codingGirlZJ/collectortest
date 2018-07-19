package campus_recruit;

import java.util.Scanner;

public class DungeonEscape {
	
	public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();		//长
        int m = sc.nextInt();		//宽
        String[][] arr = new String[n][m];	//地牢数组
        String tmp = "";
        for(int i = 0; i < n; i++){
        	tmp = sc.nextLine();
        	String[] tmps = tmp.split(" ");
            for(int j = 0; j < m; j++){
            	arr[i][j] = tmps[j];
            }
        }
        int x0 = sc.nextInt();	//x0
        int y0 = sc.nextInt();	//y0
        int k = sc.nextInt();	//有k种移动方式
        int[] dx = new int[k];	//每次能移动的行步长
        int[] dy = new int[k];	//每次能移动的列步长
        for(int i = 0; i < k; i++){
        	dx[i] = sc.nextInt();
        	dy[i] = sc.nextInt();
        }
        
        
        
    }
}
