
public class DeltaPass {

	double[][] delta;
	int[][] delta_index;
	// columns = timestep
	// lines = state
	
	
	// Computes the deltaPass
	public DeltaPass(Matrix A, Matrix B, Matrix pi, int[] obsSequence) {
		delta = new double[A.nline][obsSequence.length];
		delta_index = new int[A.nline][obsSequence.length];
		for(int state=0; state < A.nline; state++) {
			delta[state][0] = B.mat[state][0] * pi.mat[0][state];
		}
		for(int timestep = 1; timestep < obsSequence.length; timestep++) {
			for(int state = 0; state < A.nline; state++) {
				for(int j = 0; j < A.nline; j++) {
					double a = B.mat[state][obsSequence[timestep]] *
							A.mat[j][state] * delta[j][timestep - 1];
					if(delta[state][timestep] <= a) {
						delta[state][timestep] = a;
						delta_index[state][timestep] = j;
					}
				}
			}
		}
	}
	
	
	
	// Returns the most likely state sequence knowing the delta Pass (so the observation sequence)
	// A, B and Pi
	// To compute after creating the instance
	public Matrix getStateSequence(){
		Matrix sequence = new Matrix(1, delta[0].length);
		for(int i = 0; i < delta.length; i++) {
			if(delta[(int) sequence.mat[0][delta[0].length-1]][delta[0].length-1] < delta[i][delta[0].length-1]) {
				sequence.mat[0][delta[0].length-1] =  i;
			}
		}
		for(int timestep = delta[0].length -1; timestep > 0; timestep--) {
			sequence.mat[0][timestep-1] = delta_index[(int) sequence.mat[0][timestep]][timestep];
		}
		return sequence;
	}
}