package cellstore.viewer.event;

/**
 * 
 * The user has selected a gene
 * 
 * @author Johan Henriksson
 *
 */
public class EventViewerSelectedGene implements CellStoreEvent
	{
	public String geneID;
	
	public EventViewerSelectedGene(String geneID)
		{
		this.geneID=geneID;
		}

	}
