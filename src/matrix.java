
public class matrix {

	public int ncol;
	public int nline;
	public double[][] mat;
	
	// almost uniform initialization
	public matrix(int nline, int ncol) {
		this.ncol = ncol;
		this.nline = nline;
		this.mat = new double[nline][ncol];
		double sum = 0;
		for(int i = 0; i < nline; i++) {
			sum = 0;
			for(int j = 0; j < ncol-1; j++) {
				this.mat[i][j] = (1/(ncol + (Math.random() - 0.5)*0.01));
				sum += this.mat[i][j];
			}
			this.mat[i][ncol - 1] = 1 - sum;
		}
	}
	
	public matrix(int nline, int ncol, double[][] mat) {
		this.ncol = ncol;
		this.nline = nline;
		this.mat = mat;
	}
	
	public matrix(double[] sample) {
		this.nline = (int) sample[0];
		this.ncol = (int) sample[1];
		//System.out.println(nline);
		//System.out.println(ncol);
		this.mat = new double[nline][ncol];
		for(int i=0; i < sample.length - 2 ; i++) {
			this.mat[i/ncol][i% ncol] = sample[i + 2];
		}		
	}
	
	public matrix(matrix A, matrix B) {
		if(A.ncol != B.nline) {
			System.out.println("Error, incorrect dimensions");
			throw new IllegalArgumentException();
		}
		this.nline = A.nline;
		this.ncol = B.ncol;
		this.mat = new double[nline][ncol];
		for(int row=0; row < A.nline; row++) {
			for(int col=0; col < B.ncol; col++) {
				for(int i=0; i< B.nline ; i++) {
					this.mat[row][col] += A.mat[row][i]*B.mat[i][col];
				}
			}
		}
	}
	
	
	public void print() {
		System.out.print(this.mat.length + " ");
		System.out.print(this.mat[0].length + " ");
		for (int i = 0; i < this.mat.length; i++) {
		    for (int j = 0; j < this.mat[i].length; j++) {
		        System.out.print(this.mat[i][j]);
		        System.out.print(" ");
		    }
		    System.out.println();
		}
	}
	
	
	public void printHMM2() {
		for (int i = 0; i < this.mat.length; i++) {
		    for (int j = 0; j < this.mat[i].length; j++) {
		        System.out.print((int) this.mat[i][j]);
		        System.out.print(" ");
		    }
		}
	}
	
	public void printHMM3() {
		System.out.print(this.mat.length + " ");
		System.out.print(this.mat[0].length + " ");
		for (int i = 0; i < this.mat.length; i++) {
		    for (int j = 0; j < this.mat[i].length; j++) {
		        System.out.print(this.mat[i][j]);
		        System.out.print(" ");
		    }
		}
		System.out.println(" ");
	}
	
	
	public void updateTransitionMatrix(gammas gam) {
		double numerator = 0.0;
		double denumerator = 0.0;
		for(int i = 0; i < this.nline; i++) {
			for(int j = 0; j < this.ncol; j++) {
				numerator = 0;
				denumerator = 0.0;
				for(int timestep = 0; timestep < gam.digamma[0][0].length-1	; timestep++) {
					numerator += gam.digamma[i][j][timestep];
					denumerator += gam.gamma[i][timestep];
				}
				this.mat[i][j] = numerator / denumerator;
			}
		}
	}
	
	public void updateEmissionMatrix(gammas gam, int[] obsSequence) {
		double numerator = 0.0;
		double denumerator = 0.0;
		for(int j = 0; j < this.nline; j++) {
			for(int k = 0; k < this.ncol; k++) {
				numerator = 0;
				denumerator = 0.0;
				for(int timestep = 0; timestep < gam.gamma[0].length; timestep++) {
					if(obsSequence[timestep] == k) {
						numerator += gam.gamma[j][timestep];
					}
					denumerator += gam.gamma[j][timestep];
				}
				this.mat[j][k] = numerator / denumerator;
			}
		}
	}
	
	
	public void updateInitialStateMatrix(gammas gam) {
		for(int i = 0; i < this.mat[0].length; i++) {
			this.mat[0][i] = gam.gamma[i][0];
		}
	}	
}
