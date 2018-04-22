package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class CellStoreDB
	{
	public CellStoreDatasets datasets=new CellStoreDatasets();
	public HashMap<Integer, CellStoreUser> user=new HashMap<>();

	
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

	}
