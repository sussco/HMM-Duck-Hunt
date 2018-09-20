
public class Gammas {
	
	public double[][][] digamma;
	public double[][] gamma;
	// digamma[i][j][timestep]
	
	public Gammas(Matrix A, Matrix B, int[] obsSequence, AlphaPass alphaP, BetaPass betaP) {
		digamma = new double[A.nline][A.ncol][obsSequence.length];
		gamma = new double[A.ncol][obsSequence.length];
		for(int timestep = 0; timestep < obsSequence.length- 1; timestep ++) {
			for(int i = 0; i < A.nline; i++) {
				gamma[i][timestep] = 0.0;
				for(int j = 0; j < A.ncol; j++) {
						digamma[i][j][timestep] = alphaP.alpha[i][timestep] * 
								A.mat[i][j] * betaP.beta[j][timestep + 1] * B.mat[j][obsSequence[timestep + 1]];
						gamma[i][timestep] += digamma[i][j][timestep];
				}
			}
		}
		for(int i = 0; i < A.nline; i++) {
			gamma[i][gamma[i].length - 1] = alphaP.alpha[i][gamma[i].length - 1];
		}
	}

	
	public void print() {
		System.out.print(this.digamma.length + " ");
		System.out.print(this.digamma[0].length + " ");
		for (int i = 0; i < this.digamma.length; i++) {
		    for (int j = 0; j < this.digamma[i].length; j++) {
		    	for(int k = 0; k < this.digamma[i][j].length; k++) {
		    		System.out.print(this.digamma[i][j][k]);
			        System.out.print(" ");
		    	}
		    	System.out.println();
		    }
		    
		}
	}
}
