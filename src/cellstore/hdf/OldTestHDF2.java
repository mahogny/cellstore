package cellstore.hdf;

import java.util.Vector;

import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;
import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;

public class OldTestHDF2
	{

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception
		{
		
		H5File f=new H5File("fig1.h5");
		
		//HObject ob=f.get("X");
		Dataset obExp=(Dataset)f.get("X");
		Object expdata=(Object)obExp.getData();
		float[] expdataArr=(float[])expdata;
		//System.out.println(expdata.getClass());
		
		Dataset obCells=(Dataset)f.get("var");
		Vector<String[]> v=(Vector<String[]>)obCells.getData();
		String[] genenames=v.get(0);
		
		Dataset obGenes=(Dataset)f.get("obs");
		Vector<String[]> cellnamesa=(Vector<String[]>)obGenes.getData();
		String[] cellnames=cellnamesa.get(0);
		
		System.out.println(genenames[0]);
		System.out.println(cellnames[1]);

		int numgene=genenames.length;
		int numcell=cellnames.length;
		System.out.println(numgene);
		System.out.println(numcell);
		System.out.println(expdataArr.length);

		
//		System.out.println(ob.getMetadata());
		}
	
	
	public static void main2(String[] args) throws NullPointerException, HDF5Exception
		{
    //long[] dims2D = { 20, 10 };
		
		long file_id = -1;
    long dataset_id = -1;

    /*
    // create the file and add groups and dataset into the file
    createFile();
*/
    
    // X  obs val
    
    String fname="fig1.h5";  //had a problem with it in a different location
    
    // Open file using the default properties.
    file_id = H5.H5Fopen(fname, HDF5Constants.H5F_ACC_RDWR, HDF5Constants.H5P_DEFAULT);
    //file_id = H5.H5Fopen(fname, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);
    
    //Varible snameV = ncfile.findVariable("sname");
    //long nrows = snameV.getSize();
    
    
    // Open dataset using the default properties.
    dataset_id = H5.H5Dopen(file_id, "X", HDF5Constants.H5P_DEFAULT);
    
    // Counts
    int[][] dataRead = new int[20][10];
    H5.H5Dread(dataset_id, HDF5Constants.H5T_NATIVE_INT,
            HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
            HDF5Constants.H5P_DEFAULT, dataRead);

    
    
    /*
    long s2_tid =  H5.H5Tcreate(HDF5Constants.H5T_COMPOUND, 5);

    
    
    H5Tinsert(s2_tid, "index", HOFFSET(s2_t, c), H5T_NATIVE_DOUBLE);
//    H5Tinsert(s2_tid, "a_name", HOFFSET(s2_t, a), H5T_NATIVE_INT);

    */
    // Observations
    dataset_id = H5.H5Dopen(file_id, "obs", HDF5Constants.H5P_DEFAULT);
    String[] dataRead2 = new String[20];
    
    System.out.println(H5.H5Tget_nmembers(dataset_id));
    
    H5.H5Dread(dataset_id, HDF5Constants.H5T_NATIVE_CHAR,
            HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
            HDF5Constants.H5P_DEFAULT, dataRead2);
    /*
    for (int i = 0; i < 20; i++) {
        System.out.print("\n" + dataRead[i][0]);
        for (int j = 1; j < 10; j++) {
            System.out.print(", " + dataRead[i][j]);
        }
    }*/

    /*
    // change data value and write it to file.
    for (int i = 0; i < 20; i++) {
        for (int j = 0; j < 10; j++) {
            dataRead[i][j]++;
        }
    }

    // Write the data to the dataset.
    try {
        if (dataset_id >= 0)
            H5.H5Dwrite(dataset_id, HDF5Constants.H5T_NATIVE_INT,
                    HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, 
                    dataRead);
    }
    catch (Exception e) {
        e.printStackTrace();
    }

    // reload the data value
    int[][] dataModified = new int[(int) dims2D[0]][(int) (dims2D[1])];

    try {
        if (dataset_id >= 0)
            H5.H5Dread(dataset_id, HDF5Constants.H5T_NATIVE_INT,
                    HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, dataModified);
    }
    catch (Exception e) {
        e.printStackTrace();
    }

    // print out the modified data values
    System.out.println("\n\nModified Data Values");
    for (int i = 0; i < 20; i++) {
        System.out.print("\n" + dataModified[i][0]);
        for (int j = 1; j < 10; j++) {
            System.out.print(", " + dataModified[i][j]);
        }
    }

    // Close the dataset.
    try {
        if (dataset_id >= 0)
            H5.H5Dclose(dataset_id);
    }
    catch (Exception e) {
        e.printStackTrace();
    }
*/
    // Close the file.
    H5.H5Fclose(file_id);
		
		
		//-Djava.library.path="./hdf"
		}
	}
