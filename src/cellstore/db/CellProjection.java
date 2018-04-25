package cellstore.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

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

	public int[] cellsetIndex;
	public int[] cellsetCell;
	public double[] x;
	public double[] y;

	/////These only exist if hasVector is true, and is the velocity
	public boolean hasVector=false;
	public double[] dx;
	public double[] yy;


	
	
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

	public void readFromHdf(File fileh, CellStoreDB db)
		{
		// TODO Auto-generated method stub
		
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
