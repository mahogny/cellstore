package cellstore.db;

import java.util.ArrayList;

/**
 * Clustering of cells; might involve more than one CellSet. Can involve a subset of a cellset
 * 
 * @author Johan Henriksson
 *
 */
public class CellDimRed
	{
	public ArrayList<CellSet> cellsets=new ArrayList<CellSet>();
	
	public int[] cellsetIndex;
	public int[] cellsetCell;
	public double[] x;
	public double[] y;

	public String name;
	
	public void allocate(int len)
		{
		cellsetIndex=new int[len];
		cellsetCell=new int[len];
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
			cellsetIndex[i]=cellsetIndex2.get(i);
			cellsetCell[i]=cellsetCell2.get(i);
			x[i]=x2.get(i);
			y[i]=y2.get(i);
			}
		
		}


	public void makeRandom()
		{
		int n=200;
		CellSet cellset=new CellSet();
		cellsets.add(cellset);
		
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
	}
