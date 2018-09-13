
public class alphaPass {

	double[][] alpha; // columns = timestep
						// lines = state
	double[] scale; // index = timestep
	
	
	public alphaPass(matrix A, matrix B, matrix pi, int[] obsSequence) {
		alpha = new double[A.nline][obsSequence.length];
		for(int state=0; state < A.nline; state++) {
			alpha[state][0] = B.mat[state][obsSequence[0]] * pi.mat[0][state];
		}
		for(int timestep = 1; timestep < obsSequence.length; timestep++) {
			for(int state = 0; state < A.nline; state++) {
				for(int j = 0; j < A.nline; j++) {
					alpha[state][timestep] += 
							B.mat[state][obsSequence[timestep]] 
							* A.mat[j][state] * alpha[j][timestep - 1];
				}
			}
		}
	}
	
	public alphaPass(matrix A, matrix B, matrix pi, int[] obsSequence, boolean scaling) {
		alpha = new double[A.nline][obsSequence.length];
		scale = new double[obsSequence.length];
		// compute alphas..[0]
		for(int state=0; state < A.nline; state++) {
			alpha[state][0] = B.mat[state][obsSequence[0]] * pi.mat[0][state];
			scale[0] += alpha[state][0];
		}
		// scale alpha..[0]
		scale[0] = 1.0/scale[0];
		for(int state=0; state < A.nline; state++) {
			alpha[state][0] *= scale[0];
		}
		// compute alphas
		for(int timestep = 1; timestep < obsSequence.length; timestep++) {
			scale[timestep] = 0.0;
			for(int state = 0; state < A.nline; state++) {
				alpha[state][timestep] = 0.0;
				for(int j = 0; j < A.nline; j++) {
					alpha[state][timestep] += A.mat[j][state] * alpha[j][timestep - 1];
				}
				alpha[state][timestep] *= B.mat[state][obsSequence[timestep]];
				scale[timestep] += alpha[state][timestep];
			}
			// scale alpha[state][timestep]
			scale[timestep] = 1.0 / scale[timestep];
			for(int state = 0; state < A.nline; state++) {
				alpha[state][timestep] *= scale[timestep];
			}
		}
	}
	
	public double getProbability() {
		double sum = 0.0;
	    for(int state = 0; state < this.alpha.length; state++) {
	    	sum += this.alpha[state][this.alpha[0].length-1];
	    }
	    return sum;
	}
	
	public double getLogProb() {
		double logProb = 0.0;
	    for(int i = 0; i < scale.length; i++) {
	    	logProb += Math.log(this.scale[i]);
	    }
	    return -logProb;
	}
	
	public void print() {
		System.out.print(this.alpha.length + " ");
		System.out.print(this.alpha[0].length + " ");
		for (int i = 0; i < this.alpha.length; i++) {
		    for (int j = 0; j < this.alpha[i].length; j++) {
		        System.out.print(this.alpha[i][j]);
		        System.out.print(" ");
		    }
		    System.out.println();
		}
	}
}
