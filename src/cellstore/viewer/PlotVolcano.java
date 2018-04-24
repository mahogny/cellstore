package cellstore.viewer;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import cellstore.db.DiffExp;
import cellstore.db.GeneNameMapping;
import cellstore.server.conn.CellStoreConnection;

/**
 * A view of differentially expressed genes
 * 
 * @author Johan Henriksson
 *
 */
public class PlotVolcano extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public DiffExp de=new DiffExp();
	public String selectedGene="";
	private GeneNameMapping gm=GeneNameMapping.getInstance();

	double scaleFC=1;
	double scaleP=1;
	
	/**
	 * Constructor
	 */
	public PlotVolcano(CellStoreConnection conn)
		{
		autoscale();
		}

	
	/**
	 * Transform to screen, FC
	 */
	private int toScreenFC(double x)
		{
		int w=getWidth();
		return (int)(w/2 + x*scaleFC*w/2);
		}
	
	/**
	 * Transform to screen, P
	 */
	private int toScreenP(double x)
		{
		int h=getHeight();
		return (int)(h*0.9 + x*scaleP*h);
		}

	
	/**
	 * Set the scaling to fit all points
	 */
	public void autoscale()
		{
		double maxFC=1;
		double minP=-1;
		
		for(int i=0;i<de.getNumGenes();i++)
			{
			double f=Math.abs(de.fc[i]);
			double p=de.logP[i];
			if(f>maxFC)
				maxFC=f;
			if(p<minP)
				minP=p;
			}
		maxFC*=1.2;
		minP*=1.2;
		scaleFC=1/maxFC;
		scaleP=-1/minP;
		}

	
	/**
	 * Draw everything
	 */
	protected void paintComponent(Graphics g)
		{
		//Set background color
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		//Draw coordinate system
		g.setColor(Color.GRAY);
		int basex=getWidth()/2;
		int basey=toScreenP(0);
		g.drawLine(basex, 5, basex, basey);
		g.drawLine(5, basey, getWidth()-5, basey);
		
		//Draw some numbers for the axis
		for(int i=-3;i<=3;i++)
			g.drawString(""+i, toScreenFC(i), basey+20);
		
		//Plot the genes
		for(int i=0;i<de.getNumGenes();i++)
			{
			int x=toScreenFC(de.fc[i]);
			int y=toScreenP(de.logP[i]);
			
			if(de.geneID[i].equals(selectedGene))
				g.setColor(Color.RED);
			else
				g.setColor(Color.GREEN);

			String genesym=gm.getOneSymFor(de.geneID[i]);
			g.drawString(genesym, x+5, y+5);
			
//			System.out.println("c "+x+"  "+y);
			//TODO color currently selected gene in plot
			int diam=6;
			g.fillOval(x-diam/2, y-diam/2, diam, diam);
			
			//TODO draw names of interesting genes. use some sensible cut-off criteria to not display too many
			}
		}

	
	
	/**
	 * Set list of DE genes
	 * @param de
	 */
	public void setDE(DiffExp de)
		{
		this.de=de;
		autoscale();
		repaint();
		}

	/**
	 * Set the currently selected gene
	 * @param geneID
	 */
	public void setSelectedGene(String geneID)
		{
		selectedGene=geneID;
		System.out.println(selectedGene);
		repaint();
		}

	
	}
