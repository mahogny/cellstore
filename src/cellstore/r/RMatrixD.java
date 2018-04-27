package cellstore.r;

import cellstore.hdf.object.Dataset;

/**
 * Easy access to an R matrix
 * 
 *[1,2;
 *[3,4]  becomes [1,2,3,4]
 */
public class RMatrixD
	{
	double[] matrix;
	public int nrow, ncol;
	public boolean isJava=true;
	
	public RMatrixD(double[] matrix, int[] matrixDim)
		{
		this.matrix=matrix;
		nrow=matrixDim[0];
		ncol=matrixDim[1];
		}
	
	public RMatrixD(Dataset ds) throws OutOfMemoryError, Exception//double[] matrix, long[] matrixDim)
		{
		this.matrix=(double[])ds.getData();
		nrow=(int)ds.getDims()[1];
		ncol=(int)ds.getDims()[0];
		isJava=false;
		}

	public double get(int row, int col)
		{
		if(isJava)
			return matrix[row*ncol+col]; //For java
		else
			return matrix[col*nrow+row]; //For HDF
		}
	}