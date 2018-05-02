package cellstore.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import util.EvSwingUtil;

public class AddUser extends JDialog implements ActionListener
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	JTextField userId=new JTextField();
	JTextField userUser=new JTextField();
	JTextField userFullName=new JTextField();
	JTextField userEmail=new JTextField();
	JButton bOk=new JButton("OK");
	JButton bCancel=new JButton("Cancel");
	
	public boolean isOk;
	public boolean iCancel;
	public String userID;
	public String user;
	public String FullName;
	public String Email;
	
	
	public AddUser()
	{
	add(EvSwingUtil.layoutCompactVertical(
			EvSwingUtil.layoutTableCompactWide(
					new JLabel("Id: "), userId,
					new JLabel("User: "),userUser,
					new JLabel("Full name: "),userFullName,
					new JLabel("Email: "), userEmail
					),
			EvSwingUtil.layoutCompactHorizontal(bOk, bCancel)));
	setTitle("Add new user");
	setSize(300,130);
	setVisible(true);
	
	bOk.addActionListener(this);
	bCancel.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
		{
		userID=userId.getText();
		user=userUser.getText();
		FullName=userFullName.getText();
		isOk = e.getSource()==bOk;
		dispose();
		}
	
	public static void main(String[] args)
	{
	new AddUser();
	}
		
	}
