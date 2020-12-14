
public class CounterThread implements Runnable {

	private int[][] matrix;
	private int[][] counts;
	private int row;
	private int[][] directions;

	
	public CounterThread(int[][] matrix,int[][] counts,int row,int[][] directions) {
		this.matrix = matrix;
		this.row = row;
		this.counts = counts;
		this.directions = directions;
	}
	
	@Override
	public void run() {
		// this gives us amount of elements surrounding each cell
		for (int i = 0; i < matrix[0].length; i++) {
			for (int[] direction : directions) {
				int ci = row + direction[0];
				int cj = i + direction[1];
				if(cj >=0 && cj < matrix.length) {
						if(ci >= 0 && ci < matrix[cj].length) {
					        if(matrix[ci][cj] == 1 ) {
					        	counts[row][i]++;
					            }
					        }
					    } 
			}
			
		}

	}
}