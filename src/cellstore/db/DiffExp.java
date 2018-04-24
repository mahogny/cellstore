package cellstore.db;

/**
 * Differential expression data
 * 
 * could be nice to allow this to be stored as an associated object?
 * but then the gene table is more general
 * 
 * @author Johan Henriksson
 *
 */
public class DiffExp
	{
	//int id; //If stored??
	//String name="";
	
	
	
	public String[] geneID;
	public double[] fc;
	public double[] logP; //log10

	public int getNumGenes()
		{
		return geneID.length;
		}
	
	
	public DiffExp()
		{
		geneID=new String[]{"ENSG00000107485","ENSG00000113520"};
		fc=new double[]{1,2};
		logP=new double[]{-5,-3};
		}
	}
