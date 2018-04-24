package cellstore.viewer.projection;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.EvSwingUtil;
import util.rangeslide.JRangeSlider;

public class PaneConnectivity extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public static class ComboConnectivity
		{
		
		}
		
	public JComboBox<ComboConnectivity> comboConnectivity=new JComboBox<>();
	JCheckBox cbInvertSelection=new JCheckBox("Invert");
	JCheckBox cbShowAll=new JCheckBox("Show all");

	
	public PaneConnectivity()
		{
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
		
		
		}
	
	}
