package oj.leetcode.problem_037_hard_soduko_solver;

public class demo1 {

	public static void main(String[] args) {
		char[][] board = {{'.','.','9','7','4','8','.','.','.'},
						  {'7','.','.','.','.','.','.','.','.'},
		                  {'.','2','.','1','.','9','.','.','.'},
		                  {'.','.','7','.','.','.','2','4','.'},
		                  {'.','6','4','.','1','.','5','9','.'},
		                  {'.','9','8','.','.','.','3','.','.'},
		                  {'.','.','.','8','.','3','.','2','.'},
		                  {'.','.','.','.','.','.','.','.','6'},
		                  {'.','.','.','2','7','5','9','.','.'}};
		solveSudoku(board);
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				System.out.print(board[i][j] + ",");
			}
			System.out.println();
		}
	}
	
	public static void solveSudoku(char[][] board) {
		if(board == null || board.length == 0)
			return;
		solve(board);
    }
	
	public static boolean solve(char[][] board){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){ 
				if(board[i][j] == '.'){
					//对于每一个空格，将1-9都放进去检查横排、竖排以及宫是否合法
					for(char c = '1'; c <= '9'; c++){
						if(isValid(board, i, j, c)){	//这个位置放c合法
							board[i][j] = c;			//将c放到这个位置
							
							if(solve(board))			//递归继续填空，成功填满即返回true，否则则执行下面的回溯（重置）操作
								return true;
							else						//填不好，这个位置重置为.，接着放1-9中下一个试试
								board[i][j] = '.';
						}
					}
					return false;	//9个都试完了还没返回true，则说明无解，返回false
				}
			}
		}
		return true;
	}
	
	public static boolean isValid(char[][] board, int row, int col, char c){
		for(int i = 0; i < 9; i++){
			//横排
			if(board[row][i] != '.' && board[row][i] == c)
				return false;
			//竖排
			if(board[i][col] != '.' && board[i][col] == c)
				return false;
			//宫
			if(board[3*(row/3) + i/3][3*(col/3) + i%3] != '.' 
					&& board[3*(row/3) + i/3][3*(col/3) + i%3] == c)
				return false;
		}
		return true;
	}
	
	public static boolean isValid2(char[][] board, int row, int col, char c){
		//相对于isValid变化
		//1：定义宫的起始行列，减少在循环中的计算
		int sqrow = 3*(row/3);
		int sqcol = 3*(col/3);
		for(int i = 0; i < 9; i++){
			//2：不用判断 !='.'，因为.肯定不等于c
			//横排
			if(board[i][col] == c)
				return false;
			//竖排
			if(board[row][i] == c)
				return false;
			//宫
			if(board[sqrow + i/3][sqcol + i%3] != '.' 
					&& board[sqrow + i/3][sqcol + i%3] == c)
				return false;
		}
		return true;
	}
	
	/**
	 * [["5","1","9","7","4","8","6","3","2"],
	 *  ["7","8","3","6","5","2","4","1","9"],
	 *  ["4","2","6","1","3","9","8","7","5"],
	 *  ["3","5","7","9","8","6","2","4","1"],
	 *  ["2","6","4","3","1","7","5","9","8"],
	 *  ["1","9","8","5","2","4","3","6","7"],
	 *  ["9","7","5","8","6","3","1","2","4"],
	 *  ["8","3","2","4","9","1","7","5","6"],
	 *  ["6","4","1","2","7","5","9","8","3"]]
	 */
}
