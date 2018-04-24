package cellstore.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cellstore.db.CellClustering;
import cellstore.db.GeneNameMapping;
import cellstore.server.conn.CellStoreConnection;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class ViewerProjection extends JFrame implements ActionListener, KeyListener
	{
	private static final long serialVersionUID = 1L;

	private JComboBox<ColorBy> comboColorMeta=new JComboBox<ColorBy>();
	private JRadioButton rbColorMeta=new JRadioButton();
	private JRadioButton rbColorGene=new JRadioButton("Gene: ");
	private PlotProjection view;
	private JTextField tfGene=new JTextField();


	private GeneNameMapping gm=GeneNameMapping.getInstance();
	
	public CellStoreConnection conn;
	
	
	/**
	 * One option as for what you can color by. For now only clusterings
	 */
	public static class ColorBy
		{
		public CellClustering clustering;
		public int clusterID;
					
		@Override
		public String toString()
			{
			return clustering.name;
			}
		}


	/**
	 * Constructor
	 */
	public ViewerProjection(CellStoreConnection conn)
		{
		this.conn=conn;
		view=new PlotProjection(conn);
		
		setLayout(new BorderLayout());

		rbColorMeta.setSelected(true);
		
		rbColorMeta.addActionListener(this);
		rbColorGene.addActionListener(this);
		tfGene.addKeyListener(this);
		comboColorMeta.addActionListener(this);
		
		
		JPanel topp=new JPanel(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.HORIZONTAL;
		topp.add(rbColorMeta, c);
		c.gridx++;
		topp.add(comboColorMeta);
		c.gridx++;
		topp.add(rbColorGene, c);
		c.gridx++;
		c.weightx=1;
		topp.add(tfGene, c);
		
		
		JPanel totalpanel=new JPanel(new BorderLayout());
		setContentPane(totalpanel);
		
		totalpanel.add(topp, BorderLayout.NORTH);
		totalpanel.add(view, BorderLayout.CENTER);
		
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(rbColorGene);
		bg.add(rbColorMeta);

		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		fillComboColorBy();
		view.adjustScale();
		setTitle("Projection view");
		}


	/**
	 * Fill in options for the combobox, what you can color by
	 */
	public void fillComboColorBy()
		{
		comboColorMeta.removeAllItems();
		if(conn!=null)
			{
			try
				{
				for(int clId:conn.getListClusterings())
					{
					ColorBy cb=new ColorBy();
					cb.clusterID=clId;
					cb.clustering=conn.getClustering(clId);
					comboColorMeta.addItem(cb);
					}
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}
		}
		
	


	
	void updateViewInfo()
		{
		//Default: No color
		view.colorByClustering=null;
		view.colorByGene=null;

		//Color by clustering?
		ColorBy cb=(ColorBy)comboColorMeta.getSelectedItem();
		if(cb!=null && rbColorMeta.isSelected())
			view.colorByClustering=cb.clustering;
		
		//Color by gene?
		if(rbColorGene.isSelected())
			{
			String id=gm.getIDfor(tfGene.getText());
			view.colorByGene=id;
			}
		
		
		view.repaint();
		}

	@Override
	public void actionPerformed(ActionEvent e)
		{
		if(e.getSource()==comboColorMeta && comboColorMeta.getItemCount()!=0)
			rbColorMeta.setSelected(true);
		
		if(e.getSource()==rbColorGene || e.getSource()==rbColorMeta || e.getSource()==comboColorMeta)
			{
			updateViewInfo();
			}
		
		
		}


	@Override
	public void keyPressed(KeyEvent arg0)
		{
		}


	@Override
	public void keyReleased(KeyEvent arg0)
		{
		}


	@Override
	public void keyTyped(KeyEvent arg0)
		{
		tfGene.setBackground(Color.yellow);
		if(arg0.getKeyChar()=='\n')
			{
			tfGene.setBackground(Color.white);
			
			String id=gm.getIDfor(tfGene.getText());
			if(id==null)
				{
				tfGene.setText("");
				}
			else
				{
				/*
				view.colorByClustering=null;
				view.colorByGene=id;
				*/
				}
			rbColorGene.setSelected(true);
			updateViewInfo();
			view.scaleGeneExp();
			}
		}


	/**
	 * Set which DimRed to display
	 * 
	 * @param id
	 */
	public void setDimRed(int id)
		{
		try
			{
			view.dimred=conn.getDimRed(id);
			fillComboColorBy();
			view.adjustScale();
			updateViewInfo();
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}
	
	
	
	
	}
