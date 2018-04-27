package cellstore.db;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;

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
	public double score;


	public static class OneConn
		{
		int from, to;
		
		double score;
		
		/*
		public OneConn getOneConn()
			{
			OneConn c=new OneConn();
			c.fromCellset=cellsets.get(indexCellset[from]);
			c.toCellset=cellsets.get(indexCellset[to]);
			
			c.
			
			}*/
		}
	
	
	/**
	 * Get the connections between all cells
	 */
	public Collection<OneConn> getAllConnections()
		{
		return listConnections;
		}
	
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
	public void readFromHdf(File fileh, CellStoreDB db) throws IOException
		{
		
		try
			{
			H5File f=new H5File(fileh.getAbsolutePath());

			//Read cells referenced
			Dataset obCellID=(Dataset)f.get("cell_id");
			@SuppressWarnings("unchecked")
			Vector<Object> v=(Vector<Object>)obCellID.getData();
			int[] hdfIndexCellset=(int[])v.get(0);
			int[] hdfIndexCell   =(int[])v.get(1);
			for(int i=0;i<hdfIndexCellset.length;i++)
				{
				indexCellset[i]=hdfIndexCellset[i];
				indexCell[i]   =hdfIndexCell[i];
				}
			

			//TODO
			
			
			//Read coordinates
			/*
			Dataset obCoordinate=(Dataset)f.get("cell_id");
			Vector<Object> v2=(Vector<Object>)obCoordinate.getData();
			double[] hdfX=(double[])v2.get(0);
			double[] hdfY=(double[])v2.get(1);
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
			System.out.println(e);
			throw new IOException(e.getMessage());
			}			
		}

	
	
	
	
	
	public static void readFromCSV(CellConnectivity clust, File filecsv,CellSetFile cellset)
		{
		// TODO Auto-generated method stub
		
		}

	
	/*	
	public void set(
			ArrayList<Integer> cellsetIndex2,
			ArrayList<Integer> cellsetCell2, 
			ArrayList<String> cluster)
		{
		int numci=0;
		TreeMap<String, Integer> mapci=new TreeMap<String, Integer>();
		
		int len=cellsetIndex2.size();
		cellsetIndex=new int[len];
		cellsetCell=new int[len];
		clusterID=new int[len];
		
		for(int i=0;i<len;i++)
			{
			String thecluster=cluster.get(i);
			if(!mapci.containsKey(thecluster))
				{
				mapci.put(cluster.get(i), numci);
				numci++;
				}
			clusterID[i] = mapci.get(thecluster);
			}
		
		clusterNames=new String[numci];
		for(String s:mapci.keySet())
			clusterNames[mapci.get(s)]=s;
		
		allocateColor();
		}*/
	}
