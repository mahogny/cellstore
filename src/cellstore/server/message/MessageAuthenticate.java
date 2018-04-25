package cellstore.server.message;

import java.io.Serializable;
import cellstore.db.CellStoreUser;
import cellstore.server.CellStoreMain;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseAuthChallenge;
import cellstore.server.response.ResponsePassFail;

/**
 * 
 * Message to server: Authenticate given challenge
 * 
 * @author Johan Henriksson
 *
 */
public class MessageAuthenticate extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public String username;
	public String password; //encrypted somehow
	public ResponseAuthChallenge challenge;
	
	
	public void setPass(String pass, ResponseAuthChallenge challenge)
		{
		String passenc=CellStoreUser.hashpass(pass, challenge.salt);
		password=CellStoreUser.hashpass(passenc, challenge.moresalt);
				
		//Need to get the salt from the server. 
		//In that case, might as well also have server provide the extra salt too
		}
		
	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		ResponsePassFail resp=new ResponsePassFail();
		
		CellStoreUser u=client.db.getUser(username);
		if(u!=null)
			{
			resp.passed=true;
			client.userID=u.id;
			}
		else
			{
			resp.passed=false;
			}
		return resp;
		}

	}
