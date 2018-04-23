package cellstore.db;

import java.io.IOException;
import java.util.TreeMap;

import cellstore.hdf.GeneexpStoreHdf;


/**
 * 
 * A cellset *file* - should maybe not be a separate entity from CellSet?
 * 
 * @author Johan Henriksson
 *
 */
public class CellSetFile
	{
	public int ownerID;
	public String name;
	public int databaseIndex;

	public GeneexpStoreHdf file;

	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	public TreeMap<String, String> meta=new TreeMap<String, String>();
	
	
	
	
	private CellSet cellset;
	
	
	/**
	 * Get the cellset
	 * 
	 * Currently reads from HDF if not done yet
	 */
	public CellSet getCellSet()
		{
		if(cellset==null)
			{
			try
				{
				cellset=new CellSet();
				
				cellset.cellnames=file.getCellNames();
				cellset.genename=file.getGeneNames();
				float[] arr=file.getExpArr();

				int numcell=file.getNumCells();
				int numgene=file.getNumGenes();

				//For all genes, check which cells express it
				final GeneLinCounts[] allcount=new GeneLinCounts[numgene];
				for(int curgene=0;curgene<numgene;curgene++)
					{
					//Extract non-zero expression levels and only store these
					double[] count=new double[numcell];
					int[] id=new int[numcell];
					int numnonzero=0;
					for(int curcell=0;curcell<numcell;curcell++)
						{
						double e=arr[curgene*numcell+curcell];
						if(e>0)
							{
							count[curcell] = e;
							id[curcell] = curcell;
							numnonzero++;
							}
						}
					//Get rid of extra space allocated
					double[] newcount=new double[numnonzero];
					int[] newid=new int[numnonzero];
					System.arraycopy(count, 0, newcount, 0, numnonzero);
					System.arraycopy(id, 0, newid, 0, numnonzero);
					
					//Generate the new object
					GeneLinCounts c=new GeneLinCounts();
					c.cellid=newid;
					c.count=newcount;					
					allcount[curgene]=c;
					}
				
				
				cellset.dataretriever=new CellSetDataRetriever()
					{
					//Counts for a gene. If null then the data has not been fetched
					//public GeneLinCounts[] geneCount;
					
					public double getExp(int cellIndex, int geneIndex)
						{
						return allcount[geneIndex].getForCell(cellIndex);
						}
					};
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}
		return cellset;
		}


	/**
	 * Override the cellset data with another source e.g. a CSV file.
	 * Otherwise it will attempt to use Hdf file
	 */
	public void setCellSet(CellSet cellset)
		{
		this.cellset=cellset;
		}
		
	
	}
