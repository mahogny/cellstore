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
import cellstore.server.CellStoreMain;
import cellstore.server.conn.CellStoreConnection;
import cellstore.server.conn.CellStoreConnectionLocal;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreViewer extends JFrame implements ActionListener, KeyListener
	{
	private static final long serialVersionUID = 1L;

	private JComboBox<ColorBy> comboColorMeta=new JComboBox<ColorBy>();
	private JRadioButton brColorMeta=new JRadioButton();
	private JRadioButton brColorGene=new JRadioButton("Gene: ");
	private ClusterView2D view=new ClusterView2D();
	private JTextField tfGene=new JTextField();


	GeneNameMapping gm=GeneNameMapping.getInstance();
	
	public CellStoreConnection conn;
	
	
	public static class ColorBy
		{
		CellClustering clustering;
		public int clusterID;
					
		@Override
		public String toString()
			{
			return clustering.name;
			}
		}

	
	public CellStoreViewer(CellStoreConnection conn)
		{
		this.conn=conn;

		
		setLayout(new BorderLayout());


//		ArrayList<String> listAC=new ArrayList<String>();
		

		brColorMeta.setSelected(true);
		brColorMeta.addActionListener(this);
		brColorGene.addActionListener(this);
		
		//AutoCompleteDecorator.decorate(tfGene, listAC, false); //new PlainDocument(), new TextComponentAdaptor(textComponent, items));

//		AutoCompleteSupport.install(comboBox, items);

		tfGene.addKeyListener(this);
		
		JPanel topp=new JPanel(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.HORIZONTAL;
		topp.add(brColorMeta, c);
		c.gridx++;
		topp.add(comboColorMeta);
		c.gridx++;
		topp.add(brColorGene, c);
		c.gridx++;
		c.weightx=1;
		topp.add(tfGene, c);
		
		if(conn!=null)
			{
			try
				{
				view.dimred=conn.getDimRed(0);
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}
		
		
		JPanel totalpanel=new JPanel(new BorderLayout());
		setContentPane(totalpanel);
		
		totalpanel.add(topp, BorderLayout.NORTH);
		totalpanel.add(view, BorderLayout.CENTER);
		
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(brColorGene);
		bg.add(brColorMeta);

		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		fillColorCombo();
		//updateViewInfo();
		view.adjustScale();
		}

	
	public void fillColorCombo()
		{
		try
			{
			comboColorMeta.removeAllItems();
			for(int clId:conn.getListClusterings())
//		for(CellClustering cl:conn.db.datasets.clusterings.values())
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
		
	


	
	void updateViewInfo()
		{
		ColorBy cb=(ColorBy)comboColorMeta.getSelectedItem();
		view.colorByClustering=null;
		view.colorByGene=null;
		
		if(cb!=null && brColorMeta.isSelected())
			view.colorByClustering=cb.clustering;
		
		if(brColorGene.isSelected())
			{
			String id=gm.getIDfor(tfGene.getText());
			view.colorByGene=id;
			}
		//if()
//		view.colorByGene=1;

		
		
		
		view.repaint();
		}

	@Override
	public void actionPerformed(ActionEvent e)
		{
		if(e.getSource()==brColorGene || e.getSource()==brColorMeta)
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
			brColorGene.setSelected(true);
			updateViewInfo();
			view.scaleGeneExp();
			}
		}
	
	
	
	
	}
