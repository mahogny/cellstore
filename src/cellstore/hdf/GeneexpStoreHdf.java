package cellstore.hdf;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import cellstore.db.CellSetFile;
import cellstore.db.CellStoreDB;
import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;

/**
 * Gene expression stored in an HDF5 file, the anndata format
 * 
 * @author Johan Henriksson
 *
 */
public class GeneexpStoreHdf
	{
	File fname;
	boolean isLoaded=false;
	
	
	String[] cellnames;
	String[] genenames;
	float[] expdataArr;
	
	
	public String[] getCellNames() throws IOException
		{
		ensureLoaded();
		return cellnames;
		}

	public String[] getGeneNames() throws IOException
		{
		ensureLoaded();
		return genenames;
		}

	public float[] getExpArr() throws IOException
		{
		ensureLoaded();
		return expdataArr;
		}

	public int getNumCells() throws IOException
		{
		return getCellNames().length;
		}

	public int getNumGenes() throws IOException
		{
		return getGeneNames().length;
		}
	
	@SuppressWarnings("unchecked")
	private void ensureLoaded() throws IOException
		{
		if(!isLoaded)
			{
			try
				{
				H5File f=new H5File(fname.getAbsolutePath());

				
				Dataset obExp=(Dataset)f.get("X");
				Object expdata=(Object)obExp.getData();
				if(expdata instanceof float[])
					expdataArr=(float[])expdata;
				else if(expdata instanceof double[])
					{
					//If double[], convert
					double[] v=(double[])expdata;
					expdataArr=new float[v.length];
					for(int i=0;i<v.length;i++)
						expdataArr[i]=(float)v[i];
					}
				else
					throw new IOException("wrong type of exp data, "+expdata.getClass());
				
				
				
				Dataset obCells=(Dataset)f.get("var");
				Vector<String[]> v=(Vector<String[]>)obCells.getData();
				genenames=v.get(0);
				
				Dataset obGenes=(Dataset)f.get("obs");
				Vector<String[]> cellnamesa=(Vector<String[]>)obGenes.getData();
				cellnames=cellnamesa.get(0);
				
				isLoaded=true;
				}
			catch (OutOfMemoryError e)
				{
				throw new IOException(e.getMessage());
				}
			catch (Exception e)
				{
				System.out.println(fname);
				throw new IOException(e.getMessage());
				}			
			}
		}
		
	public GeneexpStoreHdf(File fname) throws IOException
		{
		this.fname=fname;
		if(!fname.exists())
			throw new IOException("no such file "+fname);
		}
	
	
	public static void scanExpdata(CellStoreDB db) throws IOException
		{
		File fexpdir=new File("data/cellset");
		for(File f:fexpdir.listFiles())
			if(f.isDirectory())
				{
				int id=Integer.parseInt(f.getName());
				System.out.println(id);
				GeneexpStoreHdf h=new GeneexpStoreHdf(new File(f,"expdata.h5ad"));
				CellSetFile csf=new CellSetFile();
				csf.databaseIndex=id;
				csf.file=h;
				db.datasets.cellsets.put(id, csf);
				}
		}
		

	public static void main(String[] args) throws Exception
		{
		scanExpdata(new CellStoreDB());
		}

	}
