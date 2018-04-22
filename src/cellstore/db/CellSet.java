package cellstore.db;

import java.util.HashMap;

public class CellSet
	{
	public String cellsetName="";
	
	/**
	 * 
	 * First a set of properties for each cell, 
	 * with at least one being geneID. should geneID get a special treatment?
	 * 
	 */
	
	public String[] cellpropNames;
	public Object[][] cellprop; //[prop][cell]
	
	
	
	public String[] cellnames;
	public String[] genename;
	
	public HashMap<String, Integer> genenameToIndex;
	public HashMap<String, Integer> cellnameToIndex;
	
	public int getNumCell()
		{
		return cellnames.length;
		}
	
	public int getNumGene()
		{
		return genename.length;
		}
	
	/**
	 * Counts for a gene. If null then the data has not been fetched
	 */
//	public GeneLinCounts[] geneCount;

	
	public interface DataRetriever
		{
		public double getExp(int cellIndex, int geneIndex);
		}
	
	public DataRetriever dataretriever;

	
	/**
	 * Build quick lookup of gene name to gene index
	 */
	private void buildGeneIndex()
		{
		if(genenameToIndex==null)
			{
			genenameToIndex=new HashMap<String, Integer>();
			for(int i=0;i<genename.length;i++)
				genenameToIndex.put(genename[i], i);
			//System.out.println(genenameToIndex);
			}
		}
	
	/**
	 * Get the index of a gene in this cellset
	 */
	public Integer getIndexOfGene(String geneName2)
		{
		buildGeneIndex();
		
		//System.out.println("lookup "+geneName2);
		return genenameToIndex.get(geneName2);
		}
	
	
	/**
	 * Get the index of a gene in this cellset
	 */
	public int getIndexOfCell(String cellName2)
		{
		//Build quick lookup of gene name to gene index
		if(cellnameToIndex==null)
			{
			cellnameToIndex=new HashMap<String, Integer>();
			for(int i=0;i<cellnames.length;i++)
				cellnameToIndex.put(cellnames[i], i);
			}
		
		return cellnameToIndex.get(cellName2);
		}

	public double getExp(int cellIndex, int geneIndex)
		{
		return dataretriever.getExp(cellIndex, geneIndex);
//		return geneCount[geneIndex].getForCell(cellIndex);
		}
	
	public double getExp(int cellIndex, String geneID)
		{
		buildGeneIndex();
		Integer geneIndex=getIndexOfGene(geneID);		
		if(geneIndex!=null)
			return dataretriever.getExp(cellIndex, geneIndex);
			//return geneCount[geneIndex].getForCell(cellIndex);
		else
			return 0;
		}
	
	
	
	}
