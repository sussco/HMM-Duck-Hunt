import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hmm2 {

public static void main(String[] args) throws IOException {
		
	
	//--------------------  Get the matrices -------------------------------- //

	
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
	    double[] initialStateProbabilityVector = new double[elements.length-1];
	    for(int i = 0; i < elements.length-1; i++) {
	    	initialStateProbabilityVector[i] = Double.parseDouble(elements[i]);
	    }
	    
	    Matrix initStateProbMatrix = new Matrix(initialStateProbabilityVector);
	    
	    ligne = lecteurAvecBuffer.readLine();
	    elements = ligne.split(" ");
	    int[] obsSequenceVector = new int[elements.length-1];
	    for(int i = 0; i < elements.length-1; i++) {
	    	obsSequenceVector[i] = Integer.parseInt(elements[i+1]);
	    	System.out.println(obsSequenceVector[i]);
	    }
	    //------------------------------------------------------------------------------//
	    
	    TransitionMatrix.print();
	    EmissionMatrix.print();
	    initStateProbMatrix.print();
	    
	    // ------------------------- Compute Delta Pass ------------------------------//
	    DeltaPass d = new DeltaPass(TransitionMatrix, EmissionMatrix, initStateProbMatrix, obsSequenceVector);
	    
	    // -------------------------- Get the State sequence -------------------------//
	    d.getStateSequence().printHMM2();
	    lecteurAvecBuffer.close();
	  }


}
