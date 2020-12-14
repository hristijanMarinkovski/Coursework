import java.util.Random;
import java.util.concurrent.TimeUnit;

public class sequential {

	public sequential(int n , int m , int iterations) {

		long startTime = System.nanoTime();
		
		int[][] matrix = new int[n][m];
		int[][] matrixUpd = new int[n][m];
		Random rand = new Random();
		int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1},  {0,1}, {1,1},  {1,0},  {1,-1},  {0, -1}};
		int[][] counts = new int[n][m];
		boolean same = true,firstIter = true;
		GUI gui;
		
		// randomize matrix
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = rand.nextInt(2);
				counts[i][j] = 0;
				matrixUpd[i][j] = matrix[i][j];
			}
		}
		
		gui= new GUI(n,m,matrix);
		
		while(true) {
			
			if(!firstIter) {
			gui.updateGUI(matrixUpd);
			}
			
			// copy matrixUpd into matrix and reset counts
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if(matrix[i][j] != matrixUpd[i][j]) {
						same = false;
					}
					matrix[i][j] = matrixUpd[i][j];
					counts[i][j] = 0;
				}
			}
			
			// if the matrix stops updating, just stop the program since no changes are occuring
			if(same && !firstIter) {
				System.out.println("Matrix stopped updating");
				break;
			}
			same = true;
			
			// if the number of desired iterations is reached, just stop the program
			if(iterations == 0) {
				System.out.println("You've reached the desired amount of iterations");
				break;
			}
			
			// this gives us amount of elements surrounding each cell
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
				    for (int[] direction : directions) {
				        int ci = i + direction[0];
				        int cj = j + direction[1];
				        if(cj >=0 && cj < matrix.length) {
				            if(ci >= 0 && ci < matrix[cj].length) {
				                if(matrix[ci][cj] == 1 ) {
				                	counts[i][j]++;
				                }
				            }
				        } 
				    }
				}
				
			}
			
			
			// now we update the matrix in order to game rules
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if((counts[i][j]==2 || counts[i][j]==3) && matrix[i][j]==1) {
						matrixUpd[i][j] = 1; // cell survives
					}else if(counts[i][j]==3 && matrix[i][j]==0) {
						matrixUpd[i][j] = 1; // cell becomes alive
					}else {
						matrixUpd[i][j] = 0; // cell stays dead
					}
				}
			}
			
			// to avoid problems in updating for the first iteration and to keep track of the number of times we iterated
			if(firstIter) {
				firstIter = false;
			}
			iterations--;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        
        System.out.println("Computations took: ~"+ timeElapsed/1000000000 + " seconds");
		

	}
	
}
