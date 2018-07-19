package niuke.jianzhioffer;

public class RobotRunRange {
	
	public static void main(String[] args) {
		int k = 5;
		int rows = 10;
		int cols = 10;
		int result = movingCount(k, rows, cols);
		System.out.println(result);
	}
	
    public static int movingCount(int threshold, int rows, int cols)
    {
        if(rows == 0 || cols == 0)
            return 0;
        int[][] f = new int[rows][cols];    //记录该格子是否已经过
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if( !canEnter(i, j, threshold)){
                    f[i][j] = 1;
                }
            }
        }
        
        int result = calEnterCount(0, 0, f, 0);
        return result;
    }
    
    public static int calEnterCount(int x, int y, int[][] f, int count){
        if(f[x][y] == 0){
        	System.out.println("x: " + x + ", y: " + y);
            f[x][y] = 1;
            count ++;
        }else{
            return count;
        }
        //找下一个
        int x1 = x, y1 = y;
        for(int i = 0; i < 4; i++){
            switch(i){    //规定：0123代表上右下左
                case 0:
                    x --;
                    break;
                case 1:
                    y ++;
                    break;
                case 2:
                    x ++;
                    break;
                case 3:
                    y --;
                    break;
            }
            if(x >= 0 && x < f.length && y >= 0 && y < f[0].length && f[x][y] == 0){
            	System.out.println("进来了:" + x + "," + y);
                count = calEnterCount(x, y, f, count);
            }else{
            	System.out.println("没进:" + x + "," + y);
            }
            x = x1;
            y = y1;
        }
        //如果四个方向都没找到
        return count;
    }
    
    public static int calSum(int x, int y){
        int sum = 0;
        while(x != 0){
            sum += x % 10;
            x /= 10;
        }
        while(y != 0){
            sum += y % 10;
            y /= 10;
        }
        return sum;
    }
    
    public static boolean canEnter(int x, int y, int k){
        int sum = calSum(x, y);
        if(sum <= k)
            return true;
        else
            return false;
    }
}