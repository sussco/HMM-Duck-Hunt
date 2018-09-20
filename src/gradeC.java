import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GradeC {

	public static void main(String[] args) throws IOException {
		
		// ------------------ Differents initialisations for matrices ------------------//
		
		// Initialization from the assignement sheet
		
//		double[][] tMatrix = {{0.54, 0.26, 0.20}, {0.19, 0.53, 0.28}, {0.22 , 0.18, 0.6}};
//		matrix A = new matrix(3, 3, tMatrix);
//		
//		double[][] eMatrix = {{0.5, 0.2, 0.11, 0.19}, {0.22, 0.28, 0.23, 0.27}, {0.19, 0.21, 0.15 , 0.45}};
//		matrix B = new matrix(3, 4, eMatrix);
//		
//		double[][] piMat = {{0.3, 0.2, 0.5}};
//		matrix pi = new matrix(1, 3, piMat); 
		
		
		
		// My init
		
//		double[][] tMatrix = {{0.5, 0.2, 0.3}, {0.05, 0.53, 0.42}, {0.3 , 0.2, 0.5}};
//		matrix A = new matrix(3, 3, tMatrix);
//		
//		double[][] eMatrix = {{0.1, 0.25, 0.19, 0.05}, {0.12, 0.28, 0.33, 0.27}, {0.09, 0.15, 0.21 , 0.55}};
//		matrix B = new matrix(3, 4, eMatrix);
//		
//		double[][] piMat = {{0.8, 0.1, 0.1}};
//		matrix pi = new matrix(1, 3, piMat); 
		
		
	
		// Almost uniform initialization
		
//		double[][] tMatrix = {{0.34, 0.325, 0.335}, {0.31, 0.35, 0.34}, {0.33 , 0.338, 0.342}};
//		matrix A = new matrix(3, 3, tMatrix);
//		
//		double[][] eMatrix = {{0.2, 0.22, 0.26, 0.32}, {0.251, 0.245, 0.265, 0.249}, {0.265, 0.235, 0.245 , 0.255}};
//		matrix B = new matrix(3, 4, eMatrix);
//		
//		double[][] piMat = {{0.36, 0.33, 0.31}};
//		matrix pi = new matrix(1, 3, piMat);
//		
		
		
		// Uniform initialization
		
//		double[][] tMatrix = {{1.0/4, 1.0/4, 1.0/4, 1.0/4}, {1.0/4, 1.0/4, 1.0/4, 1.0/4}, {1.0/4, 1.0/4, 1.0/4, 1.0/4}, {1.0/4, 1.0/4, 1.0/4, 1.0/4}};
//		matrix A = new matrix(4, 4, tMatrix);
//		
//		double[][] eMatrix = {{1.0/4, 1.0/4, 1.0/4, 1.0/4}, {1.0/4, 1.0/4, 1.0/4, 1.0/4}, {1.0/4, 1.0/4, 1.0/4, 1.0/4}, {1.0/4, 1.0/4, 1.0/4, 1.0/4}};
//		matrix B = new matrix(4, 4, eMatrix);
//		
//		double[][] piMat = {{1.0/4, 1.0/4, 1.0/4, 1.0/4}};
//		matrix pi = new matrix(1, 4, piMat);

		
		
		// Diagonal initialization

//		double[][] tMatrix = {{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0 , 0.0, 1.0}};
//		matrix A = new matrix(3, 3, tMatrix);
//		
//		double[][] eMatrix = {{0.2, 0.22, 0.26, 0.32}, {0.251, 0.245, 0.265, 0.249}, {0.265, 0.235, 0.245 , 0.255}};
//		matrix B = new matrix(3, 4, eMatrix);
//		
//		double[][] piMat = {{0.0, 0.0, 1.0}};
//		matrix pi = new matrix(1, 3, piMat); 
		
		
		
		//  Near the results initialization
		       
		double[][] tMatrix = {{0.67, 0.1, 0.23}, {0.12, 0.77, 0.11}, {0.22 , 0.28, 0.5}};
		Matrix A = new Matrix(3, 3, tMatrix);
		

		double[][] eMatrix = {{0.72, 0.18, 0.09, 0.01}, {0.12, 0.36, 0.33, 0.19}, {0.05, 0.05, 0.25 , 0.65}};
		Matrix B = new Matrix(3, 4, eMatrix);
		
		double[][] piMat = {{0.98, 0.01, 0.01}};
		Matrix pi = new Matrix(1, 3, piMat); 
		
		
		// Uniform initialization
		
//		matrix A = new matrix(3,3);
//		matrix B = new matrix(3,4);
//		matrix pi = new matrix(1,3);
		
		
		// Less hidden states
		
//		matrix A = new matrix(2,2);
//		A.print();
//		matrix B = new matrix(2,4);
//		matrix pi = new matrix(1,2);
		
		
		// More hidden states
//		matrix A = new matrix(5,5);
//		A.print();
//		matrix B = new matrix(5,4);
//		matrix pi = new matrix(1,5);
		// More hidden states
		BufferedReader lecteurAvecBuffer = null;
	    String ligne;
		lecteurAvecBuffer = new BufferedReader(new FileReader(args[0]));
	    ligne = lecteurAvecBuffer.readLine();
	    String[] elements = ligne.split(" ");
	    int N_Observations = elements.length-1;
	    //int[] obsSequenceVector = new int[elements.length];
	    int[] obsSequenceVector = new int[N_Observations];

	    for(int i = 0; i < N_Observations; i++) {
	    	obsSequenceVector[i] = Integer.parseInt(elements[i+1]);
	    }
	    
		//Learning algorithm
		int iter = 0;
	    AlphaPass a = new AlphaPass(A, B, pi, obsSequenceVector, true /* scaling*/);
	    double logProb = a.getLogProb();
	    double oldLogProb = -98465426;
	    BetaPass b; 
	    Gammas gam;
	    while(logProb > oldLogProb) {
	    	iter++;
	    	b = new BetaPass(A, B, obsSequenceVector, a, true /*scaling*/);
	    	gam = new Gammas(A, B, obsSequenceVector, a, b);
	    	
	    	A.updateTransitionMatrix(gam);
	    	B.updateEmissionMatrix(gam, obsSequenceVector);
	    	pi.updateInitialStateMatrix(gam);		    		
	    	
		    a = new AlphaPass(A, B, pi, obsSequenceVector, true /*scaling*/);
		    oldLogProb = logProb;
		    logProb = a.getLogProb();

	    }
	    System.out.println("iterations : " + iter);
	    System.out.println("LogProb : " +(logProb));
	    A.printHMM3();
	    B.printHMM3();
	    pi.printHMM3();
	    lecteurAvecBuffer.close();
	    System.exit(0);
		
	}

}
