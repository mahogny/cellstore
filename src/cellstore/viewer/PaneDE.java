package cellstore.viewer;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.DiffExp;
import cellstore.db.GeneNameMapping;
import cellstore.server.conn.CellStoreConnection;
import util.EvSwingUtil;

/**
 * A view of differentially expressed genes
 * 
 * @author Johan Henriksson
 *
 */
public class PaneDE extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public DiffExp de=new DiffExp();
	
	public PlotVolcano volc;
	
	private GeneNameMapping gm=GeneNameMapping.getInstance();

	
	public PaneDE(CellStoreConnection conn)
		{
		volc=new PlotVolcano(conn);
		
		String[] columnNames = {
				"GeneID",
        "Symbol",
        "FC",
        "Log10 P"};
		

		
		Object[][] data = new Object[de.getNumGenes()][];
		for(int i=0;i<de.getNumGenes();i++)
			{
			String sym=gm.getOneSymFor(de.geneID[i]);
			
			Object[] dat={
					de.geneID[i],
					sym,
					new Double(de.fc[i]),
					new Double(de.logP[i])};
			data[i]=dat;
			}
					
		JTable table = new JTable(data, columnNames);
		table.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		setLayout(new GridLayout(1, 1));
		add(EvSwingUtil.layoutEvenVertical(scrollPane, volc));
		//add(scrollPane);
		
		
		table.addMouseListener(new MouseAdapter() 
			{
	    public void mousePressed(MouseEvent mouseEvent) 
	    	{
	    	JTable table =(JTable) mouseEvent.getSource();
	    	Point point = mouseEvent.getPoint();
	    	int row = table.rowAtPoint(point);
	    	if (row>=0) 
	    		{
	    		System.out.println("row "+row);
	    		String geneID=(String)table.getModel().getValueAt(row, 0);
	    		volc.setSelectedGene(geneID);
		    	}
	    	}
			});
		}

	/**
	 * Set list of DE genes
	 * @param de
	 */
	public void setDE(DiffExp de)
		{
		this.de=de;
		
		//TODO update table
		}


	}
