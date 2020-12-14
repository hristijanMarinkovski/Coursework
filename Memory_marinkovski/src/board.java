import java.util.ArrayList;
import java.util.Random;

public class board {

	final public int[][] gameboard;
	int m,n;
	int card;
	Random rand = new Random();
	ArrayList<Integer> allCards = new ArrayList<Integer>();
	
	public board(int difficulty){
		
		n=difficulty+4;
		m=difficulty+4;
		gameboard = new int[n][m];
		
		for(int i = 0;i < n; i+=2) {
			for (int j = 0; j < m; j++) {
				card = rand.nextInt(100);
				while(allCards.contains(card)) {
					card = rand.nextInt(100);
				}
				allCards.add(card);
				gameboard[i][j] = card;
				gameboard[i+1][j] = card;
			}
		}
		
		shuffleBoard();
		
	}
	
	public int[][] getBoard(){
		return gameboard;
	}
	
	public void shuffleBoard() {
		for (int i = 0; i < gameboard.length; i++) {
			for (int j = 0; j < gameboard[0].length; j++) {
				int newI = rand.nextInt(gameboard.length);
				int newJ = rand.nextInt(gameboard[0].length);
				int temp = gameboard[i][j];
				gameboard[i][j] = gameboard[newI][newJ];
				gameboard[newI][newJ] = temp;
			}
		}
	}
	
}
