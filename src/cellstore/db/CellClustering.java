package cellstore.db;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import cellstore.hdf.object.Dataset;
import cellstore.hdf.object.h5.H5File;
import cellstore.r.RMatrixI;

/**
 * Clustering of cells; might involve more than one CellSet. Can involve a subset of a cellset
 * 
 * @author Johan Henriksson
 *
 */
public class CellClustering implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public int owner;
	public int id;
	public String name;
	
	
	/**
	 * Name and color of clusters
	 */
	public String[] clusterNames;
	public Color clusterColor[];

	/**
	 * Marker genes for each cluster
	 */
	public HashMap<Integer, DiffExp> clusterMarkergenes=new HashMap<>();

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

	
	public void allocate(int len)
		{
		indexCellSet=new int[len];
		indexCell=new int[len];
		clusterID=new int[len];
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

	
	
	
	private transient HashMap<CellRef, Color> lookupColor;
	
	
	/**
	 * Generate quick lookup table
	 */
	private void genLookup()
		{
		if(lookupColor==null)
			{
			lookupColor=new HashMap<>();
			for(int i=0;i<getNumCell();i++)
				lookupColor.put(new CellRef(indexCellSet[i], indexCell[i]), clusterColor[clusterID[i]]);
			//System.out.println(lookupColor.keySet());
			}
		}
	
	public Color getColorForCell(int setID, int cell)
		{
		genLookup();
		CellRef ref=new CellRef(setID, cell);
		if(!lookupColor.containsKey(ref) && ref.set==2)
			{
			//System.out.println("Not part of hash "+ref);
			//System.out.println(lookupColor.keySet());
			}
		return lookupColor.get(ref);
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
			
			//Read cells referenced
			Dataset obCellID=(Dataset)f.get("cell_id");
			RMatrixI mIndex=new RMatrixI(obCellID);//(int[])obCellID.getData(), obCellID.getDims());
			allocate(mIndex.nrow);
			for(int i=0;i<mIndex.nrow;i++)
				{
				indexCellSet[i]=mIndex.get(i, 0);
				indexCell[i]   =mIndex.get(i, 1);
				clusterID[i]   =mIndex.get(i, 2);
				}
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
	 * 
	 * Read a clustering from CSV, assuming it all refers to a single cellset
	 * 
	 * @param f
	 * @param cellset
	 * @return
	 * @throws IOException
	 */
	public static CellClustering readClusteringFromCSV(CellClustering clustering, File f, CellSetFile cellset) throws IOException
		{
		BufferedReader bi=new BufferedReader(new FileReader(f));
		
		String xName="cluster_cd45minus";
		
		//clustering.cellsets.add(cellset);
		
		ArrayList<Integer> cellsetIndex=new ArrayList<Integer>();
		ArrayList<Integer> cellsetCell=new ArrayList<Integer>();
		ArrayList<String> cluster=new ArrayList<String>();
	
		
		////// Read the header and figure out which columns are the x & y axis
		int xi=-1;
		StringTokenizer stok=new StringTokenizer(bi.readLine(), ",");
		stok.nextToken();  //bit scary that I skip it!
		for(int i=0;stok.hasMoreTokens();i++)
			{
			String s=stok.nextToken();
			//System.out.println("---"+s);
			if(s.equals(xName))
				xi=i;
			}
		//System.out.println("Got index "+xi+"   "+yi);
		
		////// Read the positions
		String line;
		while((line=bi.readLine())!=null)
			{
			stok=new StringTokenizer(line, ",");
			String geneName=stok.nextToken();
	
			String thecluster="";
			
			for(int i=0;stok.hasMoreTokens();i++)
				{
				String s=stok.nextToken();
				if(i==xi)
					thecluster=s;
				}
			
			cellsetIndex.add(cellset.id);
			cellsetCell.add(cellset.getCellSet().getIndexOfCell(geneName));
			cluster.add(thecluster);
			}
		
		clustering.set(cellsetIndex,cellsetCell, cluster);
		
		bi.close();
		return clustering;
		}


	/**
	 * Create a color from a string, #FFFFFF
	 */
	public static Color parseColor(String s)
		{
		int r = Integer.parseInt(s.substring(1,3), 16);
		int g = Integer.parseInt(s.substring(3,5), 16);
		int b = Integer.parseInt(s.substring(5,7), 16);
		Color c=new Color(r, g, b);
		return c;
		}





	}
