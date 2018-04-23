package cellstore.db;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Correlation between cells. This can really be several things: 
 * It can be the distance.
 * It can be correlation.
 * It can be clonality of T cells.
 * 
 * @author Johan Henriksson
 *
 */
public class CellCorrelation
	{
	public String name;
	
	public ArrayList<CellSet> cellsets=new ArrayList<CellSet>();	
	public int[] cellsetIndex;
	public int[] cellsetCell;
	
	//// TODO big matrix goes here
	
	
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
