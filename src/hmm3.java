import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class hmm3 {

public static void main(String[] args) throws IOException {
		
		BufferedReader lecteurAvecBuffer = null;
		    String ligne;

			//lecteurAvecBuffer = new BufferedReader(new InputStreamReader(System.in));
		    lecteurAvecBuffer = new BufferedReader(new FileReader(args[0]));
		    ligne = lecteurAvecBuffer.readLine();
		    String[] elements = ligne.split(" ");
		    double[] TransitionVector = new double[elements.length];
		    for(int i = 0; i < elements.length; i++) {
		    	TransitionVector[i] = Double.parseDouble(elements[i]);
		    }
		    matrix TransitionMatrix = new matrix(TransitionVector);
		    
		    ligne = lecteurAvecBuffer.readLine();
		    elements = ligne.split(" ");
		    double[] EmissionVector = new double[elements.length];
		    for(int i = 0; i < elements.length; i++) {
		    	EmissionVector[i] = Double.parseDouble(elements[i]);
		    }
		    matrix EmissionMatrix = new matrix(EmissionVector);
		    
		    ligne = lecteurAvecBuffer.readLine();
		    elements = ligne.split(" ");
		    double[] initialStateProbabilityVector = new double[elements.length-1];
		    for(int i = 0; i < elements.length-1; i++) {
		    	initialStateProbabilityVector[i] = Double.parseDouble(elements[i+1]);
		    }
		    
		    matrix initStateProbMatrix = new matrix(initialStateProbabilityVector);
		    
		    ligne = lecteurAvecBuffer.readLine();
		    elements = ligne.split(" ");
		    int[] obsSequenceVector = new int[elements.length];
		    for(int i = 0; i < elements.length; i++) {
		    	obsSequenceVector[i] = Integer.parseInt(elements[i]);
		    }
		    
		    
		    int iter = 0;
		    alphaPass a = new alphaPass(TransitionMatrix, EmissionMatrix, initStateProbMatrix, obsSequenceVector, true /* scaling*/);
		    double logProb = a.getLogProb();
		    double oldLogProb = -98465426;
		    betaPass b; 
		    gammas gam;
		    while(logProb > oldLogProb) {
		    	iter++;
		    	b = new betaPass(TransitionMatrix, EmissionMatrix, obsSequenceVector, a, true /*scaling*/);
		    	gam = new gammas(TransitionMatrix, EmissionMatrix, obsSequenceVector, a, b);
		    	
		    	TransitionMatrix.updateTransitionMatrix(gam);
		    	EmissionMatrix.updateEmissionMatrix(gam, obsSequenceVector);
		    	initStateProbMatrix.updateInitialStateMatrix(gam);		    		
		    	
			    a = new alphaPass(TransitionMatrix, EmissionMatrix, initStateProbMatrix, obsSequenceVector, true /*scaling*/);
			    oldLogProb = logProb;
			    logProb = a.getLogProb();

		    }
		    
		    TransitionMatrix.printHMM3();
		    EmissionMatrix.printHMM3();
		    lecteurAvecBuffer.close();
		    System.exit(0);

		  }
}
