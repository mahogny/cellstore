package cellstore.r;

/**
 * Double matrix in Compresed Sparse Row format
 * @author mahogny
 *
 */
public class CSRMatrixD
	{
	public double[] data; //The data
	public int[] indices; //Column indices
	public int[] indptr; //points to row starts in indices and data


	public CSRMatrixD(int[] indptr, int[] indices, double[] data)
		{
		this.indices=indices;
		this.indptr=indptr;
		this.data=data;
		}

	
	/*
	indices is array of column indices
	data is array of corresponding nonzero values
	indptr 
	*/
	
	
	public static interface Iterator
		{
		public void iterate(int row, int col, double value);
		}
	
	public void iterate(Iterator iterator)
		{
		for(int row=0;row<indptr.length;row++)
			{
			//Range of column indices for this row
			int startcol=indptr[row];
			int endcol=indices.length;
			if(row<indptr.length-1)
				endcol=indptr[row+1];
			//Iterate over columns
			for(int colptr=startcol;colptr<endcol;colptr++)
				iterator.iterate(indices[colptr], row, data[colptr]);
			}
		}
	}
