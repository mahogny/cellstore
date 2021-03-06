package cellstore.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * All data for CellStore
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreDB
	{
	/**
	 * All datasets
	 */
	public CellStoreDatasets datasets=new CellStoreDatasets();
	
	/**
	 * Users in the system
	 */
	public HashMap<Integer, CellStoreUser> user=new HashMap<>();

	/**
	 * Groupings of datasets. These will be used to tie papers together and make
	 * them easier to browse. Can also be used for reporting to split up
	 * cells into different categories
	 */
	public HashMap<Integer, CellStoreDatasets> grouping=new HashMap<>();
	
	
	/**
	 * Get an unused ID for a user
	 * 
	 * @return
	 */
	public int getFreeUserID()
		{
		return getFreeID(user.keySet());
		}

	/**
	 * Get the next free integer ID, for any set
	 * 
	 * @param existingIDs
	 * @return
	 */
	private static int getFreeID(Collection<Integer> existingIDs)
		{
		int i=1;
		for(;;)
			{
			if(!existingIDs.contains(i))
				return i;
			i++;
			}
		}
	
	/**
	 * Read user database into memory
	 * 
	 * @throws IOException
	 */
	public void readUsers() throws IOException
		{
		FileInputStream is=new FileInputStream(new File("data/users.json"));
		JsonReader rdr = Json.createReader(is);
		JsonArray results = rdr.readArray();
		for (JsonObject result : results.getValuesAs(JsonObject.class)) 
			{
			CellStoreUser u=new CellStoreUser();
			u.id=result.getInt("id");
			u.username=result.getString("user");
			u.name=result.getString("name");
			u.email=result.getString("email");
			
			user.put(u.id, u);
			}
		is.close();
		}
	
	
	/**
	 * Write user database to disk
	 * 
	 * @throws IOException
	 */
	public void writeUsers() throws IOException
		{
		//For gabija
		}


	/**
	 * Get user by username
	 * 
	 * @param username
	 * @return Returns null if user does not exist
	 */
	public CellStoreUser getUser(String username)
		{
		for(CellStoreUser u:user.values())
			{
			if(u.username.equals(username))
				return u;
			}
		return null;
		}

	/**
	 * Store a new cell projection
	 * 
	 * @param projection
	 * @return
	 */
	public int putNewCellProjection(CellProjection projection)
		{
		int newid=getFreeID(datasets.projections.keySet());
		datasets.projections.put(newid, projection);
		return newid;
		}

	/**
	 * Store a new user
	 * 
	 * @param u
	 * @return
	 * @throws IOException 
	 */
	public Integer putNewUser(CellStoreUser u) throws IOException
		{
		int newid=getFreeID(datasets.projections.keySet());
		user.put(newid, u);
		writeUsers();
		return newid;
		}

	/**
	 * Remove a projection
	 * 
	 * @param id
	 * @return 
	 */
	public boolean removeProjection(int id) throws IOException
		{
		datasets.projections.remove(id);
		// TODO also remove from disk
		return true;
		}
	


	}
