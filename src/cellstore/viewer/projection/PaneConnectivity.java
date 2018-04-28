package cellstore.viewer.projection;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cellstore.db.CellConnectivity;
import cellstore.server.conn.CellStoreConnection;
import util.EvSwingUtil;
import util.rangeslide.JRangeSlider;


/**
 * 
 * Pane with settings for displaying a connectivity object
 * 
 * @author Johan Henriksson
 *
 */
public abstract class PaneConnectivity extends JPanel implements ActionListener, ChangeListener
	{
	private static final long serialVersionUID = 1L;

	public static class ComboConnectivity
		{
		
		}
		
	public JComboBox<ComboOptionConnectivity> comboConnectivity=new JComboBox<>();
	private JCheckBox cbInvertSelection=new JCheckBox("Invert");
	private JCheckBox cbShowAll=new JCheckBox("Show all");
	private CellStoreConnection conn;

	
	/**
	 * Constructor
	 */
	public PaneConnectivity(CellStoreConnection conn)
		{
		this.conn=conn;
		
		JRangeSlider slCutoff=new JRangeSlider(0, 100, 50, 10);
		slCutoff.setOrientation(SwingConstants.HORIZONTAL);

		setLayout(new GridLayout(1, 1));
		add(EvSwingUtil.layoutEvenVertical(
				EvSwingUtil.layoutLCR(
						new JLabel("Connectivity: "),
						comboConnectivity,
						cbShowAll),
				EvSwingUtil.layoutLCR(
						new JLabel("Show with values: "),
						slCutoff,
						cbInvertSelection)
				
				));

		fillCombo();

		comboConnectivity.addActionListener(this);
		cbShowAll.addActionListener(this);
		slCutoff.addChangeListener(this);
		}
	
	
	
	/**
	 * Fill in options for the combobox, what you can color by
	 */
	public void fillCombo()
		{
		comboConnectivity.removeAllItems();
		
		ComboOptionConnectivity cbEmpty=new ComboOptionConnectivity();
		comboConnectivity.addItem(cbEmpty);

		if(conn!=null)
			{
			try
				{
				for(int clId:conn.getListConnectivity())
					{
					ComboOptionConnectivity cb=new ComboOptionConnectivity();
					cb.connectivity=conn.getConnectivity(clId);
					comboConnectivity.addItem(cb);
					}
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}
		}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
		{
		if(e.getSource()==comboConnectivity)
			{
			changed();
			}
		}
	
	@Override
	public void stateChanged(ChangeEvent e)
		{
		changed();
		}

	/**
	 * Signal upon change of settings
	 */
	public abstract void changed();

	

	/**
	 * Get the currently selected connectivity object
	 */
	public CellConnectivity getCurrentConnectivity()
		{
		ComboOptionConnectivity opt=(ComboOptionConnectivity)comboConnectivity.getSelectedItem();
		if(opt!=null)
			return opt.connectivity;
		else
			return null;
		}
	
	}
