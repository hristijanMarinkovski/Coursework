import java.util.concurrent.atomic.AtomicBoolean;

public class CopyThread implements Runnable {

	private int[][] matrix;
	private int[][] matrixUpd;
	private int[][] counts;
	private int row;
	private AtomicBoolean same;
	
	public CopyThread(int[][] matrix,int[][] matrixUpd,int[][] counts,int row,AtomicBoolean same) {
		this.matrix = matrix;
		this.matrixUpd = matrixUpd;
		this.row = row;
		this.counts = counts;
		this.same = same;
	}
	
	@Override
	public void run() {
		// copies matrixUpd into matrix and updates counts
		for (int i = 0; i < matrixUpd[0].length; i++) {
			if(matrix[row][i] != matrixUpd[row][i]) {
				same.set(false);
			}
			matrix[row][i] = matrixUpd[row][i];
			counts[row][i] = 0; 
			    
		}

	}
}
