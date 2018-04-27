package cellstore.r;

import cellstore.hdf.object.Dataset;

/**
 * Easy access to an R matrix (integer)
 * 
 *[1,2;
 *[3,4]  becomes [1,2,3,4]
 */
public class RMatrixI
	{
	int[] matrix;
	public int nrow, ncol;
	public boolean isJava=true;
	

	public RMatrixI(int[] matrix, int[] matrixDim)
		{
		this.matrix=matrix;
		nrow=matrixDim[0];
		ncol=matrixDim[1];
		}
	
	public RMatrixI(Dataset ds) throws OutOfMemoryError, Exception
		{
		this.matrix=(int[])ds.getData();
		nrow=(int)ds.getDims()[1];
		ncol=(int)ds.getDims()[0];
		isJava=false;
		System.out.println("Created matrix from hdf, "+nrow+" x "+ncol);
		}

	public int get(int row, int col)
		{
		if(isJava)
			return matrix[row*ncol+col]; //For java
		else
			return matrix[col*nrow+row]; //For HDF
		}
	}