package cellstore.db;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Clustering of cells; might involve more than one CellSet. Can involve a subset of a cellset
 * 
 * @author Johan Henriksson
 *
 */
public class CellClustering
	{
	public ArrayList<CellSet> cellsets=new ArrayList<CellSet>();
	public String[] clusterNames;

	public int[] cellsetIndex;
	public int[] cellsetCell;
	public int[] clusterID;
	public String name;
	
	
	public Color color[];
	
	public void allocateColor()
		{
		color=new Color[clusterNames.length];
		for(int i=0;i<color.length;i++)
			{
			color[i]=new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
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
		}
	}
