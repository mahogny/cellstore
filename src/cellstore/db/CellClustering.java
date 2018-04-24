package cellstore.db;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;

/**
 * Clustering of cells; might involve more than one CellSet. Can involve a subset of a cellset
 * 
 * @author Johan Henriksson
 *
 */
public class CellClustering
	{
	public int owner;
	public int id;
	public String name;
	
	
	/**
	 * Name and color of clusters
	 */
	public String[] clusterNames;
	public Color clusterColor[];

	/**
	 * Which cluster each cell belongs to
	 */
	public int[] indexCellSet;
	public int[] indexCell;
	public int[] clusterID;

	
	
	public int getNumCell()
		{
		return indexCellSet.length;
		}

	
	public int getNumGroups()
		{
		return clusterNames.length;
		}

	
	/**
	 * Allocate random colors
	 */
	public void allocateColor()
		{
		clusterColor=new Color[clusterNames.length];
		for(int i=0;i<clusterColor.length;i++)
			{
			clusterColor[i]=new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
			}
		}
		
	
	
	public void set(
			ArrayList<Integer> cellsetIndex2,
			ArrayList<Integer> cellsetCell2, 
			ArrayList<String> cluster)
		{
		int numci=0;
		TreeMap<String, Integer> mapci=new TreeMap<String, Integer>();
		
		int len=cellsetIndex2.size();
		indexCellSet=new int[len];
		indexCell=new int[len];
		clusterID=new int[len];
		
		//Generate cluster indices and build a list of clusters
		for(int i=0;i<len;i++)
			{
			String thecluster=cluster.get(i);
			if(!mapci.containsKey(thecluster))
				{
				mapci.put(cluster.get(i), numci);
				numci++;
				}
			clusterID[i] = mapci.get(thecluster);
			indexCell[i] = cellsetCell2.get(i);
			indexCellSet[i] = cellsetIndex2.get(i);
			}
		
		//Make a list of cluster names from the previous keys
		clusterNames=new String[numci];
		for(String s:mapci.keySet())
			clusterNames[mapci.get(s)]=s;
		
		allocateColor();
		}

	
	
	
	/**
	 * Key for lookup table
	 */
	private static class SetCell
		{
		private int set;
		private int cell;
		
		public int hashCode()
			{
			return set+cell;
			}
		
		public boolean equals(Object obj)
			{
			if(obj instanceof SetCell)
				{
				SetCell b=(SetCell)obj;
				return set==b.set && cell==b.cell;
				}
			else
				return false;
			}

		public SetCell(int set, int cell)
			{
			this.set = set;
			this.cell = cell;
			}
		
		@Override
		public String toString()
			{
			return "("+set+","+cell+")";
			}
		}

	
	private transient HashMap<SetCell, Color> lookupColor;
	
	private void genLookup()
		{
		if(lookupColor==null)
			{
			lookupColor=new HashMap<>();
			for(int i=0;i<getNumCell();i++)
				lookupColor.put(new SetCell(indexCellSet[i], indexCell[i]), clusterColor[clusterID[i]]);
			}
		}
	
	public Color getColorFor(int setID, int cell)
		{
		genLookup();
		return lookupColor.get(new SetCell(setID, cell));
		}

	
	
	/**
	 * Read data from HDF file
	 * 
	 * @param fileh
	 * @param relatedTo
	 * @throws IOException 
	 */
	public void fromHdf(File fileh, int relatedTo, CellStoreDB db) throws IOException
		{
		
		try
			{
			H5File f=new H5File(fileh.getAbsolutePath());

			//Read names of clusters
			Dataset obExp=(Dataset)f.get("cluster_name");
			System.out.println(obExp);
			clusterNames=(String[])obExp.getData();

			//Read colors
			Dataset obColor=(Dataset)f.get("cluster_color");
			String[] colors=(String[])obColor.getData();
			clusterColor=new Color[colors.length];
			for(int i=0;i<colors.length;i++)
				clusterColor[i]=parseColor(colors[i]);
			
			
			
			Dataset obCells=(Dataset)f.get("cells");
			@SuppressWarnings("unchecked")
			Vector<Object> v=(Vector<Object>)obCells.getData();
			
			
			String[] cellnames=(String[])v.get(0);
			clusterID=(int[])v.get(1);
			
			//Map cellnames to ID
			CellSetFile csf=db.datasets.cellsets.get(relatedTo);
			CellSet cs=csf.getCellSet();
			indexCellSet=new int[cellnames.length];
			indexCell=new int[cellnames.length];
			
			for(int i=0;i<cellnames.length;i++)
				{
				//System.out.println("name "+cellnames[i]+ "   "+cs.getIndexOfCell(cellnames[i]));
				indexCellSet[i]=csf.id;
				indexCell[i] =cs.getIndexOfCell(cellnames[i]);
				}
			
			
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
	
	
	
	
	
	
	
	public static Color parseColor(String s)
		{
		int r = Integer.parseInt(s.substring(1,3), 16);
		int g = Integer.parseInt(s.substring(3,5), 16);
		int b = Integer.parseInt(s.substring(5,7), 16);
		return new Color(r, g, b);
		}



	}
