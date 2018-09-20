import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hmm0 {

	public static void main(String[] args) throws IOException{

		
		//--------------------  Get the matrices -------------------------------- //
		
		 BufferedReader lecteurAvecBuffer = null;
		    String ligne;

			lecteurAvecBuffer = new BufferedReader(new InputStreamReader(System.in));

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
		    for(int i = 0; i < elements.length ; i++) {
		    	initialStateProbabilityVector[i] = Double.parseDouble(elements[i]);
		    }
		    
		    //-----------------------------------------------------------------------//
		    
		    //---------------------------- Multiply Matrices ------------------------//

		    Matrix initStateProbMatrix = new Matrix(initialStateProbabilityVector);

		    Matrix nextStateVector = new Matrix(initStateProbMatrix, TransitionMatrix);

		    Matrix nextEmissionVector = new Matrix(nextStateVector, EmissionMatrix);

		    nextEmissionVector.print();
		    lecteurAvecBuffer.close();

		  }

}
