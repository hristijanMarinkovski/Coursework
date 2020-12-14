
public class UpdaterThread implements Runnable {

	private int[][] matrix;
	private int[][] matrixUpd;
	private int[][] counts;
	private int row;
	
	public UpdaterThread(int[][] matrix,int[][] matrixUpd,int[][] counts,int row) {
		this.matrix = matrix;
		this.matrixUpd = matrixUpd;
		this.row = row;
		this.counts = counts;
	}
	
	@Override
	public void run() {
		// this updates the cell values according to the number of adjecent living cells
		for (int i = 0; i < matrixUpd[0].length; i++) {
				
			if((counts[row][i]==2 || counts[row][i]==3) && matrix[row][i]==1) {
 				matrixUpd[row][i] = 1; // cell survives
 			}else if(counts[row][i]==3 && matrix[row][i]==0) {
 				matrixUpd[row][i] = 1; // cell becomes alive
 			}else {
 				matrixUpd[row][i] = 0; // cell stays dead
 			}
			    
		}

	}
}