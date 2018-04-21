package cellstore.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import cellstore.viewer.conn.CellStoreConnection;
import db.CellClustering;
import db.CellDimRed;
import db.CellSet;

/**
 * Frame showing a 2D clustering
 * 
 * @author Johan Henriksson
 *
 */
public class ClusterView2D extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener
	{
	private static final long serialVersionUID = 1L;

	double scalingGeneExp=100;

	
	/**
	 * If to show names. Only do when close enough
	 */
	public boolean showCellName=false;
	
	
	
	/**
	 * Some component to allow selection of genes
	 */
	
	/**
	 * What about multiple views you can zoom at the same time. Different genes in each but linked?
	 */
	

	CellDimRed dimred;
	CellStoreConnection conn;
	
	public ClusterView2D()
		{
		dimred=new CellDimRed();
		dimred.makeRandom();

		//adjustScale();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		}
	
	public void adjustScale()
		{
		double minX=Double.MAX_VALUE;
		double maxX=-Double.MAX_VALUE;
		
		double minY=Double.MAX_VALUE;
		double maxY=-Double.MAX_VALUE;
		
		if(dimred!=null)
			{
			for(int i=0;i<dimred.getNumCell();i++)
				{
				minX=Math.min(minX,dimred.x[i]);
				minY=Math.min(minY,dimred.y[i]);
				
				maxX=Math.max(maxX,dimred.x[i]);
				maxY=Math.max(maxY,dimred.y[i]);
				}
			}
		
		scaleX=getWidth() /(maxX - minX);
		scaleY=getHeight()/(maxY - minY);
		
		panX=-minX*scaleX;
		panY=-minY*scaleY;
		}
	
	public double scaleX=100;
	public double scaleY=100;
	public double panX=0;
	public double panY=0;
	
	
	public int toScreenX(double x)
		{
		return (int)(x*scaleX+panX);
		}

	public int toScreenY(double y)
		{
		return (int)(y*scaleY+panY);
		}
	
	public double fromScreenX(int x)
		{
		return (x-panX)/scaleX;
		}

	public double fromScreenY(int y)
		{
		return (y-panY)/scaleY;
		}
	
	
	public void scaleGeneExp()
		{
		double maxlevel=0.0001;
		if(dimred!=null && colorByGene!=null)
			{
			for(int i=0;i<dimred.getNumCell();i++)
				{
				CellSet cellset=dimred.cellsets.get(dimred.cellsetIndex[i]);
				double level=cellset.getExp(dimred.cellsetCell[i], colorByGene);
				maxlevel=Math.max(maxlevel,level);
				}
			}
		System.out.println("max level: "+maxlevel);
		scalingGeneExp=1/Math.log10(1+maxlevel);
		}
	
	
	@Override
	protected void paintComponent(Graphics g)
		{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		
		if(dimred!=null)
			{
			for(int i=0;i<dimred.getNumCell();i++)
				{
				if(colorByGene!=null)
					{
					//System.out.println("colorbygene "+colorByGene);
					
					CellSet cellset=dimred.cellsets.get(dimred.cellsetIndex[i]);
					
					double level=cellset.getExp(dimred.cellsetCell[i], colorByGene);
					
					level=Math.log10(1+level);
//					System.out.println(level);
					
					float rgb=(float)(level*scalingGeneExp);
					if(rgb>1)
						rgb=1;
					g.setColor(new Color(rgb,0,0));
					}
				else
					g.setColor(Color.BLACK);

				
				int x=toScreenX(dimred.x[i]);
				int y=toScreenY(dimred.y[i]);
				
				int r=5;
				g.fillOval(x-r, y-r, r*2, r*2);
				}
			
			
			}
		
		
		drawHistogram();
		
		}

	/**
	 * Draw a histogram of expression levels, and max/min level
	 */
	private void drawHistogram()
		{
		//For gabija
		}

	
	
	
	private int startPanX=0;
	private int startPanY=0;
	private boolean startDrag=false;


	public CellClustering colorByClustering;
	public String colorByGene;
	
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
