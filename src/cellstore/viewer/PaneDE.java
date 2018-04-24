package cellstore.viewer;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.DiffExp;
import cellstore.db.GeneNameMapping;
import cellstore.server.conn.CellStoreConnection;
import util.EvBrowserUtil;
import util.EvSwingUtil;

/**
 * A view of differentially expressed genes
 * 
 * @author Johan Henriksson
 *
 */
public class PaneDE extends JPanel implements ActionListener
	{
	private static final long serialVersionUID = 1L;

	public DiffExp de=new DiffExp();
	public PlotVolcano plotVolc;
	private JButton bPubmed=new JButton("Pubmed");
	private JButton bEnsembl=new JButton("Ensembl");
	private JButton bGenecards=new JButton("Genecards");

	private GeneNameMapping gm=GeneNameMapping.getInstance();


	JTable table;
	
	/**
	 * Constructor
	 * 
	 * @param conn
	 */
	public PaneDE(CellStoreConnection conn)
		{
		plotVolc=new PlotVolcano(conn);
		
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
					
		table = new JTable(data, columnNames);
		table.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		

		setLayout(new GridLayout(1, 1));
		add(EvSwingUtil.layoutEvenVertical(
				EvSwingUtil.layoutACB(
						null, 
						scrollPane, 
						EvSwingUtil.layoutEvenHorizontal(
								bPubmed,
								bEnsembl,
								bGenecards
								)), 
				plotVolc));
		
		bPubmed.addActionListener(this);
		bEnsembl.addActionListener(this);
		bGenecards.addActionListener(this);
		
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
	    		plotVolc.setSelectedGene(geneID);
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



	@Override
	public void actionPerformed(ActionEvent e)
		{
		int row=table.getSelectedRow();
		if(row>=0)
			{
  		String geneID=(String)table.getModel().getValueAt(row, 0);
			String genesym=gm.getOneSymFor(geneID);
			if(e.getSource()==bPubmed)
				{
				EvBrowserUtil.displayURL("https://www.ncbi.nlm.nih.gov/pubmed/?term="+genesym);
				}
			else if(e.getSource()==bGenecards)
				{
				EvBrowserUtil.displayURL("http://www.genecards.org/cgi-bin/carddisp.pl?gene="+genesym);
				}
			else if(e.getSource()==bEnsembl)
				{
				if(geneID.startsWith("ENSG"))
					EvBrowserUtil.displayURL("http://www.ensembl.org/Homo_sapiens/Gene/Summary?g="+geneID);
				else if(geneID.startsWith("ENSMUS"))
					EvBrowserUtil.displayURL("http://www.ensembl.org/Mus_musculus/Gene/Summary?g="+geneID);
				}
			}
		}


	
	
	}
