package cellstore.viewer.event;

/**
 * 
 * The user has selected a gene
 * 
 * @author Johan Henriksson
 *
 */
public class ViewerEventSelectedGene implements CellStoreEvent
	{
	public String geneID;
	
	public ViewerEventSelectedGene(String geneID)
		{
		this.geneID=geneID;
		}

	}
