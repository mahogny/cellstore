package cellstore.viewer.projection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import cellstore.db.CellClustering;
import cellstore.db.CellProjection;
import cellstore.db.CellSet;
import cellstore.db.CellSetFile;
import cellstore.server.conn.CellStoreConnection;

/**
 * Frame showing a 2D clustering
 * 
 * @author Johan Henriksson
 *
 */
public class PlotProjection extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener
	{
	private static final long serialVersionUID = 1L;

	double scalingGeneExp=100;

	
	/**
	 * If to show names. Only do when close enough
	 */
	public boolean showCellName=false;

	public double scaleX=100;
	public double scaleY=100;
	public double panX=0;
	public double panY=0;

	private int startPanX=0;
	private int startPanY=0;
	private boolean startDrag=false;


	public CellClustering colorByClustering;
	public String colorByGene;

	
	/**
	 * Some component to allow selection of genes
	 */
	
	/**
	 * What about multiple views you can zoom at the same time. Different genes in each but linked?
	 */
	

	public CellProjection projection;
	private CellStoreConnection conn;

	/**
	 * Constructor
	 */
	public PlotProjection(CellStoreConnection conn)
		{
		this.conn=conn;
		//dimred=new CellDimRed();
		//dimred.makeRandom();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		}
	
	/**
	 * Adjust the scale to fit everything into the view
	 */
	public void adjustScale()
		{
		double minX=Double.MAX_VALUE;
		double maxX=-Double.MAX_VALUE;
		
		double minY=Double.MAX_VALUE;
		double maxY=-Double.MAX_VALUE;
		
		if(projection!=null)
			{
			for(int i=0;i<projection.getNumCell();i++)
				{
				minX=Math.min(minX,projection.x[i]);
				minY=Math.min(minY,projection.y[i]);
				
				maxX=Math.max(maxX,projection.x[i]);
				maxY=Math.max(maxY,projection.y[i]);
				}
			}
		
		scaleX=getWidth() /(maxX - minX);
		scaleY=getHeight()/(maxY - minY);
		
		panX=-minX*scaleX;
		panY=-minY*scaleY;
		}
	
	
	/**
	 * Transform world to screen, X
	 */
	public int toScreenX(double x)
		{
		return (int)(x*scaleX+panX);
		}

	/**
	 * Transform world to screen, Y
	 */
	public int toScreenY(double y)
		{
		return (int)(y*scaleY+panY);
		}
	
	/**
	 * Transform to world from screen, X
	 */
	public double fromScreenX(int x)
		{
		return (x-panX)/scaleX;
		}

	/**
	 * Transform to world from screen, Y
	 */
	public double fromScreenY(int y)
		{
		return (y-panY)/scaleY;
		}
	

	/**
	 * Set the scaling of gene expression to let the maximum gene expression be the maximum color intensity
	 */
	public void scaleGeneExp()
		{
		double maxlevel=0.0001;
		if(projection!=null && colorByGene!=null && projection.getNumCell()>1)
			{
			for(int i=0;i<projection.getNumCell();i++)
				{
				//System.out.println("cell#: "+i);
				CellSet cellset=conn.getCellSetFile(projection.indexCellSet[i]).getCellSet(); //TODO Should not be repeated
//				CellSet cellset=dimred.cellsets.get(dimred.cellsetIndex[i]);
				double level=cellset.getExp(projection.indexCell[i], colorByGene);
				maxlevel=Math.max(maxlevel,level);
				}
			}
		System.out.println("max level: "+maxlevel);
		scalingGeneExp=1/Math.log10(1+maxlevel);
		}
	

	/**
	 * Draw the component
	 */
	protected void paintComponent(Graphics g)
		{
		//Set background color
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Only draw anything if a projection has been chosen
		if(projection!=null)
			{
			CellSetFile csf=null;
			
			for(int i=0;i<projection.getNumCell();i++)
				{
				if(colorByGene!=null)
					{
					//Set the color based on a gene
					//System.out.println("colorbygene "+colorByGene);
					
					//Get the data file - cache it as usually only one file ever used
					if(csf==null || csf.id!=projection.indexCellSet[i])
						csf=conn.getCellSetFile(projection.indexCellSet[i]);					
					CellSet cellset=csf.getCellSet(); //TODO Should not be repeated

					
					//Map intensity to color
					double level=cellset.getExp(projection.indexCell[i], colorByGene);
					level=Math.log10(1+level);
					float rgb=(float)(level*scalingGeneExp);
					if(rgb>1)
						rgb=1;
					g.setColor(new Color(rgb,0,0));
					}
				else if(colorByClustering!=null)
					{
					//Set the color based on clustering
					Color c=colorByClustering.getColorFor(projection.indexCellSet[i], projection.indexCell[i]);
					//System.out.println("got color "+c);
					if(c!=null)
						g.setColor(c);
					else
						{
						//System.out.println("Not part of clustering, cell# "+projection.cellsetIndex[i]+":"+projection.cellsetCell[i]);
						g.setColor(Color.RED);
						}
					}
				else
					g.setColor(Color.BLACK);


				
				//Draw point on screen
				int x=toScreenX(projection.x[i]);
				int y=toScreenY(projection.y[i]);
				//int r=5;
				//g.fillOval(x-r, y-r, r*2, r*2);
				g.drawLine(x, y, x, y);
				}
			
			if(colorByGene!=null)
				drawHistogram(g);
			
			if(colorByClustering!=null)
				drawClusterLegend(g);
			}
		
		}

	
	
	/**
	 * Draw colors and names of cluster groups
	 */
	private void drawClusterLegend(Graphics g)
		{
		CellClustering cc=colorByClustering;
		for(int i=0;i<cc.getNumGroups();i++)
			{
			int y=30+20*i;
			int x=getWidth()-100;
			g.setColor(cc.clusterColor[i]);
			g.fillRect(x, y, 10, 10);
			g.setColor(Color.BLACK);
			g.drawString(cc.clusterNames[i], x+15, y+10);
			}
		}

	/**
	 * Draw a histogram of expression levels, and max/min level
	 */
	private void drawHistogram(Graphics g)
		{
		//For gabija
		//loop and draw horizontal lines of different colors
		
		}

	
		
	@Override
	public void mouseClicked(MouseEvent e)
		{
		}

	@Override
	public void mouseEntered(MouseEvent e)
		{
		}

	@Override
	public void mouseExited(MouseEvent e)
		{
		}

	@Override
	public void mousePressed(MouseEvent e)
		{
		startPanX=e.getX();
		startPanY=e.getY();
		if(e.getButton()==MouseEvent.BUTTON1)
			startDrag=true;
		}

	@Override
	public void mouseReleased(MouseEvent e)
		{
		if(e.getButton()==MouseEvent.BUTTON1)
			startDrag=false;
		}

	/**
	 * Handle: Drag, mouse button was pressed while moving
	 */
	@Override
	public void mouseDragged(MouseEvent e)
		{
		if(startDrag)
			{
			int dx=e.getX()-startPanX;
			int dy=e.getY()-startPanY;
			startPanX=e.getX();
			startPanY=e.getY();

			panX+=dx;
			panY+=dy;
			repaint();
			}
		}

	@Override
	public void mouseMoved(MouseEvent e)
		{
		}

	/**
	 * Handle: Mouse wheel
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
		{
		int sx=e.getX();
		int sy=e.getY();
		
		double midx=fromScreenX(sx);
		double midy=fromScreenY(sy);
		
		double s=Math.exp(e.getWheelRotation()*0.2);
		scaleX*=s;
		scaleY*=s;

		panX=sx - midx*scaleX;
		panY=sy - midy*scaleY;
		repaint();
		}
	
	
	}
