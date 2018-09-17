import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class hmm {

	public static void main(String[] args) throws IOException{

		 BufferedReader lecteurAvecBuffer = null;
		    String ligne;

			lecteurAvecBuffer = new BufferedReader(new InputStreamReader(System.in));

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
		    double[] initialStateProbabilityVector = new double[elements.length];
		    for(int i = 0; i < elements.length ; i++) {
		    	initialStateProbabilityVector[i] = Double.parseDouble(elements[i]);
		    }

		    matrix initStateProbMatrix = new matrix(initialStateProbabilityVector);

		    matrix nextStateVector = new matrix(initStateProbMatrix, TransitionMatrix);

		    matrix nextEmissionVector = new matrix(nextStateVector, EmissionMatrix);

		    nextEmissionVector.print();
		    lecteurAvecBuffer.close();

		  }

}
