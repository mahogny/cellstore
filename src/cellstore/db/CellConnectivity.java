package cellstore.db;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import cellstore.hdf.HdfHandler;
import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;
import cellstore.hdf.object.h5.H5Group;
import cellstore.r.CSRMatrixD;

/**
 * Correlation between cells. This can really be several things: 
 * It can be the distance.
 * It can be correlation.
 * It can be clonality of T cells.
 * 
 * Clonality is actually a clustering thing... but this system would work too
 * 
 * @author Johan Henriksson
 *
 */
public class CellConnectivity
	{
	public int id;
	public String name;
	public int owner;

	/**
	 * Cells the connectivity refers to
	 */
	public ArrayList<CellSet> cellsets=new ArrayList<CellSet>();	
	public int[] indexCellset;
	public int[] indexCell;

	private ArrayList<OneConn> listConnections=new ArrayList<>();
	
	//In the future: possibly have gene1-gene2, to support cell communication.
	//may also need P-value


	/**
	 * One connection
	 */
	public static class OneConn
		{
		public int from, to;
		public double score;
		}
	
	
	/**
	 * Get the connections between all cells
	 */
	public Collection<OneConn> getAllConnections()
		{
		return listConnections;
		}
	
	/**
	 * Get only the connections related to a particular cell
	 * 
	 * @param cs
	 * @param cellIndex
	 * @return
	 */
	public Collection<OneConn> getConnectionsRelatedTo(CellSet cs, int cellIndex)
		{
		ArrayList<OneConn> arr=new ArrayList<>();
		
		int csIndex=cellsets.indexOf(cs);
		for(OneConn c:listConnections)
			if((indexCellset[c.from]==csIndex && indexCell[c.from]==cellIndex) ||
					(indexCellset[c.to]==csIndex && indexCell[c.to]==cellIndex))
				arr.add(c);
		return arr;
		}

	
	
	/**
	 * Read from HDF. Best stored as a compressed matrix, for random I/O
	 * @throws IOException 
	 */
	public void fromHdf(File fileh, int relatedto, CellStoreDB db) throws IOException
		{
		
		try
			{
			H5File f=new H5File(fileh.getAbsolutePath());

			//For querying out of memory, is a compressed matrix better?
			
			//Read matrix and convert into object list
			CSRMatrixD m=HdfHandler.getCSR(f, "connectivity");
			m.iterate(new CSRMatrixD.Iterator()
				{
				public void iterate(int row, int col, double value)
					{
					OneConn c=new OneConn();
					c.from=row;
					c.to=col;
					c.score=value;
					if(c.score<0.01)
						listConnections.add(c);
					}
				});

			//Read cells referenced
			Dataset obCellID=(Dataset)f.get("cell_id");
			System.out.println(obCellID.getData().getClass());
			int[] hdfid=(int[])obCellID.getData();
			allocateCells(hdfid.length/2);
			for(int i=0;i<hdfid.length/2;i++)
				{
				indexCellset[i]=hdfid[i*2];
				indexCell[i]   =hdfid[i*2+1]; //hopefully right
				}			
			/*
			@SuppressWarnings("unchecked")
			Vector<Object> v=(Vector<Object>)obCellID.getData();
			int[] hdfIndexCellset=(int[])v.get(0);
			int[] hdfIndexCell   =(int[])v.get(1);
			for(int i=0;i<hdfIndexCellset.length;i++)
				{
				indexCellset[i]=hdfIndexCellset[i];
				indexCell[i]   =hdfIndexCell[i];
				}
			*/
			}
		catch (OutOfMemoryError e)
			{
			throw new IOException(e.getMessage());
			}
		catch (Exception e)
			{
			System.out.println(fileh);
			e.printStackTrace();
			throw new IOException(e.getMessage());
			}			
		}

	
	/**
	 * Allocate space for a given number of cells (but not the matrix)
	 * 
	 * @param numcell
	 */
	private void allocateCells(int numcell)
		{
		indexCell=new int[numcell];
		indexCellset=new int[numcell];
		}
	
	}
