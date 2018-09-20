import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hmm3 {

public static void main(String[] args) throws IOException {
		
	
		//--------------------  Get the matrices -------------------------------- //

		BufferedReader lecteurAvecBuffer = null;
	    String ligne;
		lecteurAvecBuffer = new BufferedReader(new InputStreamReader(System.in));
	    //lecteurAvecBuffer = new BufferedReader(new FileReader(args[0]));
	    ligne = lecteurAvecBuffer.readLine();
	    String[] elements = ligne.split(" ");
	    double[] TransitionVector = new double[elements.length];
	    for(int i = 0; i < elements.length; i++) {
	    	TransitionVector[i] = Double.parseDouble(elements[i]);
	    }
	    Matrix TransitionMatrix = new Matrix(TransitionVector);
	    
	    ligne = lecteurAvecBuffer.readLine();
	    elements = ligne.split(" ");
	    double[] EmissionVector = new double[elements.length];
	    for(int i = 0; i < elements.length; i++) {
	    	EmissionVector[i] = Double.parseDouble(elements[i]);
	    }
	    Matrix EmissionMatrix = new Matrix(EmissionVector);
	    
	    ligne = lecteurAvecBuffer.readLine();
	    elements = ligne.split(" ");
	    double[] initialStateProbabilityVector = new double[elements.length];
	    for(int i = 0; i < elements.length; i++) {
	    	initialStateProbabilityVector[i] = Double.parseDouble(elements[i]);
	    }
	    
	    Matrix initStateProbMatrix = new Matrix(initialStateProbabilityVector);
	    
	    ligne = lecteurAvecBuffer.readLine();
	    elements = ligne.split(" ");
	    int[] obsSequenceVector = new int[elements.length-1];
	    for(int i = 0; i < elements.length-1; i++) {
	    	obsSequenceVector[i] = Integer.parseInt(elements[i+1]);
	    }
	    
	    // ---------------------------------------------------------------------------//
	    
	    
	    int iter = 0;
	    AlphaPass a = new AlphaPass(TransitionMatrix, EmissionMatrix, initStateProbMatrix, obsSequenceVector, true /* scaling*/);
	    // Likelyhood
	    double logProb = a.getLogProb();
	    double oldLogProb = -98465426;
	    BetaPass b; 
	    Gammas gam;
	    // While there isn't convergence
	    while(logProb - oldLogProb > 0.04) {
	    	iter++;
	    	b = new BetaPass(TransitionMatrix, EmissionMatrix, obsSequenceVector, a, true /*scaling*/);
	    	gam = new Gammas(TransitionMatrix, EmissionMatrix, obsSequenceVector, a, b);
	    	
	    	TransitionMatrix.updateTransitionMatrix(gam);
	    	EmissionMatrix.updateEmissionMatrix(gam, obsSequenceVector);
	    	initStateProbMatrix.updateInitialStateMatrix(gam);		    		
	    	
		    a = new AlphaPass(TransitionMatrix, EmissionMatrix, initStateProbMatrix, obsSequenceVector, true /*scaling*/);
		    oldLogProb = logProb;
		    logProb = a.getLogProb();

	    }
	    System.out.println("iter = " + iter);
	    System.out.println("LogProb : " + logProb);
	    TransitionMatrix.printHMM3();
	    EmissionMatrix.printHMM3();
	    lecteurAvecBuffer.close();
	    System.exit(0);

	  }
}
