package cellstore.viewer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @author Johan Henriksson
 *
 */
public class MenuFavouriteGenes extends JMenu
	{
	private static final long serialVersionUID = 1L;

	
	/**
	 * Create the favourite gene menu
	 * 
	 * @return
	 */
	public MenuFavouriteGenes()
		{
		super("FavoGenes");
		
		JMenuItem miGene=new JMenuItem("GATA3");
		JMenuItem miAddGene=new JMenuItem("Add current gene");
		add(miGene);
		addSeparator();
		add(miAddGene);
		}
	
	
	}
