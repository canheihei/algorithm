package algorithm14_UnionSet;

public class class03_NumberOfIslands {
	public static void main(String[] args) {

	}

	public static int numIslands(char[][] board) {
		int islands = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == '1') {
					islands++;
					infect(board, i, j);
				}
			}
		}
		return islands;
	}

	public static void infect(char[][] board, int i, int j) {
		if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != '1') {
			return;
		}
		board[i][j] = 0;
		infect(board, i, j + 1);
		infect(board, i, j - 1);
		infect(board, i - 1, j);
		infect(board, i + 1, j);
	}
}
