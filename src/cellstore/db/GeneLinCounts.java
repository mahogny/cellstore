package cellstore.db;

import java.util.ArrayList;


/**
 * Counts for one gene across cells
 * 
 * @author Johan Henriksson
 *
 */
public class GeneLinCounts
	{
	public int[] cellid;
	public double[] count;
	
	public void set(ArrayList<Integer> cellid2, ArrayList<Double> counts)
		{
		cellid=new int[cellid2.size()];
		for(int i=0;i<cellid2.size();i++)
			cellid[i]=cellid2.get(i);
		
		count=new double[counts.size()];
		for(int i=0;i<counts.size();i++)
			count[i]=counts.get(i);
		
		}

	
	
	public double getForCell(int cellIndex)
		{
		//Could be made a binary search, or expand it
		for(int i=0;i<cellid.length;i++)
			{
			if(cellid[i]==cellIndex)
				return count[i];
			}
		return 0;
		}

	}
