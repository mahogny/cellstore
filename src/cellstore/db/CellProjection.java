package cellstore.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;
import cellstore.r.RMatrixD;
import cellstore.r.RMatrixI;

/**
 * Clustering of cells; might involve more than one CellSet. Can involve a subset of a cellset
 * 
 * @author Johan Henriksson
 *
 */
public class CellProjection implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	//public ArrayList<CellSet> cellsets=new ArrayList<CellSet>();
	
	public String name="";
	public int ownerID;
	public int id;

	public int[] indexCellSet;
	public int[] indexCell;
	public double[] x;
	public double[] y;

	/////These only exist if hasVector is true, and is the velocity
	public boolean hasVector=false;
	public double[] dx;
	public double[] yy;


	
	
	public void allocate(int len)
		{
		indexCellSet=new int[len];
		indexCell=new int[len];
		x=new double[len];
		y=new double[len];
		}
	
	public void set(
			ArrayList<Integer> cellsetIndex2, ArrayList<Integer> cellsetCell2,
			ArrayList<Double> x2, ArrayList<Double> y2)
		{
		int len=cellsetIndex2.size();
		allocate(len);
		
		for(int i=0;i<len;i++)
			{
			indexCellSet[i]=cellsetIndex2.get(i);
			indexCell[i]=cellsetCell2.get(i);
			x[i]=x2.get(i);
			y[i]=y2.get(i);
			}
		
		}


	public void makeRandom()
		{
		int n=200;
		//CellSet cellset=new CellSet();
		//cellsets.add(cellset);
		
		allocate(n);
		for(int i=0;i<n;i++)
			{
			x[i]=Math.random();
			y[i]=Math.random();
			}
		
		}

	public int getNumCell()
		{
		return x.length;
		}

	
	/**
	 * Read from HDF. Best stored as a compressed matrix, for random I/O
	 * 
	 * @throws IOException 
	 */
	public void readFromHdf(File fileh, CellStoreDB db) throws IOException
		{
		
		try
			{
			H5File f=new H5File(fileh.getAbsolutePath());

			//Read cells referenced
			Dataset obCellID=(Dataset)f.get("cell_id");
			RMatrixI mIndex=new RMatrixI(obCellID);//(int[])obCellID.getData(), obCellID.getDims());
			allocate(mIndex.nrow);
			for(int i=0;i<mIndex.nrow;i++)
				{
				indexCellSet[i]=mIndex.get(i, 0);
				indexCell[i]   =mIndex.get(i, 1);
				}
			

			//Read coordinates
			Dataset obCoordinate=(Dataset)f.get("cell_pos");
			RMatrixD mCoordinate=new RMatrixD(obCoordinate);
			//RMatrixD mCoordinate=new RMatrixD((double[])obCoordinate.getData(), obCoordinate.getDims());
			for(int i=0;i<mCoordinate.nrow;i++)
				{
				x[i]=mCoordinate.get(i, 0);
				y[i]=mCoordinate.get(i, 1);
				}
			
			//HDF *dims* is in format column x row
			//might be doing it wrong

			}
		catch (OutOfMemoryError e)
			{
			throw new IOException(e.getMessage());
			}
		catch (Exception e)
			{
			System.out.println(fileh);
			System.out.println(e);
			e.printStackTrace();
			throw new IOException(e.getMessage());
			}			
		}

	

	/**
	 * 
	 * Read a dimensionality reduction from CSV, assuming it all refers to a single cellset
	 * 
	 * @param f
	 * @param cellset
	 * @return
	 * @throws IOException
	 */
	public static CellProjection readProjectFromCSV(CellProjection dimred, File f, CellSetFile cellset) throws IOException
		{
		BufferedReader bi=new BufferedReader(new FileReader(f));
		
		String xName="PC1_cd45minus";
		String yName="PC2_cd45minus";
		
		//CellDimRed dimred=new CellDimRed();
		//dimred.cellsets.add(cellset);
		
		ArrayList<Integer> cellsetIndex=new ArrayList<Integer>();
		ArrayList<Integer> cellsetCell=new ArrayList<Integer>();
		ArrayList<Double> x=new ArrayList<Double>();
		ArrayList<Double> y=new ArrayList<Double>();
	
		
		////// Read the header and figure out which columns are the x & y axis
		int xi=-1;
		int yi=-1;
		StringTokenizer stok=new StringTokenizer(bi.readLine(), ",");
		//stok.nextToken();  //bit scare that I skip it!
		for(int i=0;stok.hasMoreTokens();i++)
			{
			String s=stok.nextToken();
			//System.out.println("---"+s);
			if(s.equals(xName))
				xi=i;
			if(s.equals(yName))
				yi=i;
			}
		//System.out.println("Got index "+xi+"   "+yi);
		
		////// Read the positions
		String line;
		while((line=bi.readLine())!=null)
			{
			stok=new StringTokenizer(line, ",");
			String geneName=stok.nextToken();
	
			double theX=0;
			double theY=0;
			
			
			for(int i=0;stok.hasMoreTokens();i++)
				{
				String s=stok.nextToken();
				if(i==xi)
					theX=Double.parseDouble(s);
				if(i==yi)
					theY=Double.parseDouble(s);
				}
			
			cellsetIndex.add(cellset.id);
			cellsetCell.add(cellset.getCellSet().getIndexOfCell(geneName));
			x.add(theX);
			y.add(theY);
			}
		
		dimred.set(cellsetIndex,cellsetCell, x,y);
		
		
		bi.close();
		return dimred;
		}
	
	
	
	
	
	
	}
