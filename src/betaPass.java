
public class BetaPass {
	
	public double[][] beta;
	// column = timestep
	// line = state
	
	// Computes the BetaPass
	public BetaPass(Matrix A, Matrix B, int[] obsSequence) {
		beta = new double[A.nline][obsSequence.length];
		for(int state=0; state < A.nline; state++) {
			beta[state][beta[0].length - 1] = 1;
		}
		for(int timestep = obsSequence.length- 2; timestep >= 0; timestep--) {
			for(int state = 0; state < A.nline; state++) {
				for(int j = 0; j < A.nline; j++) {
					beta[state][timestep] += 
							B.mat[j][obsSequence[timestep + 1]] 
							* A.mat[state][j] * beta[j][timestep + 1];
				}
			}
		}
	}
	
	// Compute the beta pass with scaling
	public BetaPass(Matrix A, Matrix B, int[] obsSequence, AlphaPass a, boolean scaling) {
		beta = new double[A.nline][obsSequence.length];
		for(int state=0; state < A.nline; state++) {
			beta[state][obsSequence.length-1] = a.scale[obsSequence.length-1];
		}
		for(int timestep = obsSequence.length - 2; timestep >= 0; timestep--) {
			for(int state = 0; state < A.nline; state++) {
				beta[state][timestep]  = 0;
				for(int j = 0; j < A.nline; j++) {
					beta[state][timestep] += 
							B.mat[j][obsSequence[timestep + 1]] 
							* A.mat[state][j] * beta[j][timestep + 1];
				}
				beta[state][timestep] *= a.scale[timestep];
			}
		}
	}
	
	

	
	public void print() {
		System.out.print(this.beta.length + " ");
		System.out.print(this.beta[0].length + " ");
		for (int i = 0; i < this.beta.length; i++) {
		    for (int j = 0; j < this.beta[i].length; j++) {
		        System.out.print(this.beta[i][j]);
		        System.out.print(" ");
		    }
		    System.out.println();
		}
	}
}

