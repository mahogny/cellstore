package cellstore.r;

/**
 * Easy access to an R matrix
 * 
 *[1,2;
 *[3,4]  becomes [1,2,3,4]
 */
public class RMatrix
	{
	double[] matrix;
	public int nrow, ncol;
	
	public RMatrix(double[] matrix, int[] matrixDim)
		{
		this.matrix=matrix;
		nrow=matrixDim[0];
		ncol=matrixDim[1];
		}
	
	public double get(int row, int col)
		{
		return matrix[row*col+col];
		}
	}