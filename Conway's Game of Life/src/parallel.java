import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class parallel {

	public parallel(int n,int m,int iterations) {
		
		long startTime = System.nanoTime();

		int[][] matrix = new int[n][m];
		int[][] matrixUpd = new int[n][m];
		Random rand = new Random();
		int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1},  {0,1}, {1,1},  {1,0},  {1,-1},  {0, -1}};
		int[][] counts = new int[n][m];
		//boolean same = true;
		AtomicBoolean same = new AtomicBoolean(true);
		boolean firstIter = true;
		GUI gui;
		
		// randomize matrix, could be pararelized easily, but barely affects the runtime since its only run once
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = rand.nextInt(2);
				counts[i][j] = 0;
				matrixUpd[i][j] = matrix[i][j];
			}
		}
		
		
		gui= new GUI(n,m,matrix);
		
		while(true) { // im only paralelizing this loop since most of the operations are repeated here
			
			if(!firstIter) {
				gui.updateGUI(matrixUpd);
			}
			
			// copy matrixUpd into matrix and reset counts  ||| PARALLEL ||| 
			ExecutorService executor = Executors.newFixedThreadPool(n);// im passing each row to be computed by a tread,so n are requred
	        for (int i = 0; i < n; i++) {  
	        	CopyThread worker =  new CopyThread(matrix,matrixUpd,counts,i,same);  
	            executor.execute(worker);;
	          }  
	        executor.shutdown();  
	        while (!executor.isTerminated()) {   }   
	        
	        
			
	        // if the matrix stops updating, just stop the program since no changes are occuring
	        if(same.get() && !firstIter) {
	        	System.out.println("Matrix stopped updating");
				break;
			}
			same.set(true);;
			
			
			
			// if the number of desired iterations is reached, just stop the program
			if(iterations == 0) {
				System.out.println("You've reached the desired amount of iterations");
				break;
			}
			
			
			
			// this gives us amount of elements surrounding each cell
	        ExecutorService executor2 = Executors.newFixedThreadPool(n);
			for (int i = 0; i < n; i++) {  
				CounterThread worker =  new CounterThread(matrix,counts,i,directions);  
	            executor2.execute(worker);;
	          }  
	        executor2.shutdown();  
	        while (!executor2.isTerminated()) {   }  
			
	       
	        
	        // now we update the matrix in order to game rules
	     
	     	ExecutorService executor3 = Executors.newFixedThreadPool(n);
	     	for (int i = 0; i < n; i++) {  
				UpdaterThread worker =  new UpdaterThread(matrix,matrixUpd,counts,i);  
		        executor3.execute(worker);;
		    }  
		    executor3.shutdown();  
		    while (!executor3.isTerminated()) {   }  
				

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
