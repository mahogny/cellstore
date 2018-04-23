package cellstore.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import util.EvSwingUtil;

/**
 * Dialog: Connect to a server
 * 
 * @author Johan Henriksson
 *
 */
public class DialogConnect extends JDialog implements ActionListener
	{
	private static final long serialVersionUID = 1L;
	
	private JTextField tfAddress=new JTextField();
	private JTextField tfUser=new JTextField();
	private JTextField tfPassword=new JTextField();
	private JButton bOk=new JButton("OK");
	private JButton bCancel=new JButton("Cancel");
	
	
	/// Return data
	public boolean isOk;
	public String address;
	public String user;
	public String password;
	
	
	public DialogConnect()
		{
		add(EvSwingUtil.layoutCompactVertical(
				EvSwingUtil.layoutTableCompactWide(
						new JLabel("Address: "), tfAddress,
						new JLabel("User: "),tfUser,
						new JLabel("Password: "),tfPassword
						),
				EvSwingUtil.layoutCompactHorizontal(bOk, bCancel)));
		pack();
		setVisible(true);
		
		bOk.addActionListener(this);
		bCancel.addActionListener(this);
		}
	
	@Override
	public void actionPerformed(ActionEvent e)
		{
		address=tfAddress.getText();
		user=tfUser.getText();
		password=tfPassword.getText();
		isOk = e.getSource()==bOk;
		dispose();
		}
	
	public static void main(String[] args)
		{
		new DialogConnect();
		}
	}
