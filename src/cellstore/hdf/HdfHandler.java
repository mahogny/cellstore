package cellstore.hdf;

import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;
import cellstore.r.CSRMatrixD;

public class HdfHandler
	{

	public static CSRMatrixD getCSR(H5File f, String base) throws OutOfMemoryError, Exception
		{
		Dataset obIndptr=(Dataset)f.get(base+"/indptr");
		int[] aIndptr=(int[])obIndptr.getData();

		Dataset obIndices=(Dataset)f.get(base+"/indices");
		int[] aIndices=(int[])obIndices.getData();

		Dataset obData=(Dataset)f.get(base+"/data");
		double[] aData=(double[])obData.getData();

		CSRMatrixD m=new CSRMatrixD(aIndptr, aIndices, aData);
		return m;
		}
	}
