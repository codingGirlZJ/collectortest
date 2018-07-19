package niuke.jianzhioffer;

/**
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
 * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 
 * 例如 
 * 		a b c e s f c s a d e e 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，
 * 		因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
 * @author lllzj
 *
 */
public class PathInMatrix {
	
	public static void main(String[] args) {
		String matrixStr = "ABCEHJIGSFCSLOPQADEEMNOEADIDEJFMVCEIFGGS";
		char[] matrix = matrixStr.toCharArray();
		int rows = 5;
		int cols = 8;
		String strStr = "SGGFIECVAASABCEHJIGQEM";
		char[] str = strStr.toCharArray();
		
		boolean hasP = hasPath(matrix, rows, cols, str);
		System.out.println(hasP);
	}
	
    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        if(matrix.length == 0 || str.length == 0){
            return false;
        }
        //(i, j) = i*cols + j是第i行第j列的元素在数组matrix中的位置
        char[][] matrix1 = new char[rows][cols];
        int[][] dfs = new int[rows][cols];
        int k = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                matrix1[i][j] = matrix[k];
                dfs[i][j] = 0;
                k++;
            }
        }
        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(dfsPath(matrix1, i, j, str, 0, dfs)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean dfsPath(char[][] matrix, int curRow, int curCol, char[] str, int strIndex, int[][] dfs){
        if(matrix[curRow][curCol] == str[strIndex]){
        	System.out.println("curRow: " + curRow + ", curCol: " + curCol + ",strIndex: " + strIndex);
            int saveCurRow = curRow, saveCurCol = curCol;
            dfs[curRow][curCol] = 1;
            strIndex++;
            if(strIndex == str.length)        //如果当前比较的已经是最后一个字符了，那么即找到路径，返回true
                return true;
            //否则没找到，继续找下一个字符
            //遍历四个方向找
            for(int i = 0; i < 4; i++){
                switch(i){    //规定：0123代表上右下左
                    case 0:
                        curRow --;
                        break;
                    case 1:
                        curCol ++;
                        break;
                    case 2:
                        curRow ++;
                        break;
                    case 3:
                        curCol --;
                        break;
                }
                if(curRow >= 0 && curCol >= 0 && curRow < matrix.length && curCol < matrix[0].length && dfs[curRow][curCol] == 0){
                    boolean f = dfsPath(matrix, curRow, curCol, str, strIndex, dfs);
                    if(f){            //如果下一个找到了，那么就返回true
                        return true;
                    }else{            //否则，复原当前行列，找下一个方向
                    	System.out.println("curRow: " + curRow + ", saveCurRow: " + saveCurRow);
                    	System.out.println("curCol: " + curCol + ", saveCurCol: " + saveCurCol);
                        curRow = saveCurRow;
                        curCol = saveCurCol;
                    }
                }else{            //否则，复原当前行列，找下一个方向
                	System.out.println("curRow: " + curRow + ", saveCurRow: " + saveCurRow);
                	System.out.println("curCol: " + curCol + ", saveCurCol: " + saveCurCol);
                    curRow = saveCurRow;
                    curCol = saveCurCol;
                }
            }
            //四个方向都找了找不到就返回false
            //这里要记得将一开始设置为1的还原，因为在找开始节点时，如果没找到，这里又不还原的话，会影响找到真正开始节点后的遍历
            dfs[curRow][curCol] = 0;
            return false;
        }else{
            return false;
        }
        
    }


}