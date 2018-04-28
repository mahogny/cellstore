package cellstore.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import cellstore.db.CellClustering;
import cellstore.db.CellConnectivity;
import cellstore.db.CellProjection;
import cellstore.db.CellSet;
import cellstore.db.CellSetFile;
import cellstore.db.CellStoreDB;
import cellstore.hdf.GeneexpStoreHdf;
import cellstore.r.RCellStore;

/**
 * Running of the database only
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreMain
	{
	public CellStoreDB db=new CellStoreDB();

	HashSet<CellStorePortListener> listeners=new HashSet<>();
	
	public CellStoreMain() throws IOException
		{
		db.readUsers();
		
		scanDataCellSet();
		scanDataClusterings();
		scanDataProjections();
		scanDataConnectivity();
		}
	

	/**
	 * Scan for count matrices
	 * 
	 * @throws IOException
	 */
	public void scanDataCellSet() throws IOException
		{
		File fexpdir=new File("data/cellset");
		for(File f:fexpdir.listFiles())
			if(f.isDirectory())
				{
				int id=Integer.parseInt(f.getName());
				System.out.println(id);
				
				CellSetFile csf=new CellSetFile();

				//// Read the metadata
				FileInputStream is=new FileInputStream(new File(f,"info.json"));
				JsonReader rdr = Json.createReader(is);
				JsonObject result = rdr.readObject();
				csf.name=result.getString("name");
				csf.ownerID=result.getInt("owner");
				csf.id=id;
				is.close();
				
				//// Attach the data
				File fileh=new File(f,"expdata.h5ad");
				File filecsv=new File(f,"expdata.csv");
				if(fileh.exists())
					{
					GeneexpStoreHdf h=new GeneexpStoreHdf(fileh);
					csf.file=h;
					}
				else if(filecsv.exists())
					{
					CellSet cellset=CellSetFile.readCountTableFromCSV(filecsv);
					csf.setCellSet(cellset);
					}
				else
					throw new IOException("Invalid cellset");
				db.datasets.cellsets.put(id, csf);
				}
		}
	
	
	/**
	 * Scan for projections
	 * 
	 * @throws IOException
	 */
	public void scanDataProjections() throws IOException
		{
		File fexpdir=new File("data/dimred");
		for(File f:fexpdir.listFiles())
			if(f.isDirectory())
				{
				int id=Integer.parseInt(f.getName());
				System.out.println(id);
				
				CellProjection dimred=new CellProjection();
				
				//// Read the metadata
				FileInputStream is=new FileInputStream(new File(f,"info.json"));
				JsonReader rdr = Json.createReader(is);
				JsonObject result = rdr.readObject();
				dimred.name=result.getString("name");
				dimred.ownerID=result.getInt("owner");
				dimred.id=id;
				int relatedto=result.getInt("relatedto");
				is.close();
				
				//// Attach the data
				File fileh=new File(f,"dimred.h5");
				File filecsv=new File(f,"dimred.csv");
				if(fileh.exists())
					{
					dimred.readFromHdf(fileh, db);
					}
				else if(filecsv.exists())
					{
					CellSetFile cellset=db.datasets.cellsets.get(relatedto);//.getCellSet();
					CellProjection.readProjectFromCSV(dimred, filecsv, cellset);
					}
				else
					throw new IOException("Invalid dimred");
				db.datasets.projections.put(id,dimred);
				}
		}
		
	
	/**
	 * 
	 * Scan for clusterings
	 * 
	 * @throws IOException
	 */
	public void scanDataClusterings() throws IOException
		{
		File fexpdir=new File("data/clustering");
		for(File f:fexpdir.listFiles())
			if(f.isDirectory())
				{
				int id=Integer.parseInt(f.getName());
				System.out.println(id);
				
				CellClustering clust=new CellClustering();
				
				//// Read the metadata
				FileInputStream is=new FileInputStream(new File(f,"info.json"));
				JsonReader rdr = Json.createReader(is);
				JsonObject result = rdr.readObject();
				clust.name=result.getString("name");
				clust.owner=result.getInt("owner");
				clust.id=id;
				int relatedto=result.getInt("relatedto");
				is.close();
				
				//// Attach the data
				File fileh=new File(f,"clust.h5");
				File filecsv=new File(f,"clust.csv");
				if(fileh.exists())
					{
					clust.fromHdf(fileh, relatedto, db);
					}
				else if(filecsv.exists())
					{
					CellSetFile cellset=db.datasets.cellsets.get(relatedto);//.getCellSet();
					CellClustering.readClusteringFromCSV(clust, filecsv, cellset);
					}
				else
					throw new IOException("Invalid cell clustering");
				db.datasets.clusterings.put(id,clust);
				}
		}
		
	

	/**
	 * 
	 * Scan for clusterings
	 * 
	 * @throws IOException
	 */
	public void scanDataConnectivity() throws IOException
		{
		File fexpdir=new File("data/connectivity");
		for(File f:fexpdir.listFiles())
			if(f.isDirectory())
				{
				int id=Integer.parseInt(f.getName());
				System.out.println(id);
				
				CellConnectivity connectivity=new CellConnectivity();
				
				//// Read the metadata
				FileInputStream is=new FileInputStream(new File(f,"info.json"));
				JsonReader rdr = Json.createReader(is);
				JsonObject result = rdr.readObject();
				connectivity.name=result.getString("name");
				connectivity.owner=result.getInt("owner");
				connectivity.id=id;
				int relatedto=result.getInt("relatedto");
				is.close();
				
				//// Attach the data
				File fileh=new File(f,"connectivity.h5");
				File filecsv=new File(f,"connectivity.csv");
				if(fileh.exists())
					{
					System.out.println("-----------conn-----------------------------------");
					connectivity.fromHdf(fileh, relatedto, db);
					}
				/*else if(filecsv.exists())
					{
					CellSetFile cellset=db.datasets.cellsets.get(relatedto);//.getCellSet();
					CellConnectivity.readFromCSV(connectivity, filecsv, cellset);
					}*/
				else
					throw new IOException("Invalid cell clustering");
				db.datasets.connectivity.put(id,connectivity);
				}
		}

	
	
	/**
	 * Open port for incoming connections
	 * 
	 * @throws IOException
	 */
	public void openPort() throws IOException
		{
		CellStorePortListener listener=new CellStorePortListener(this);
		listener.start();
		listeners.add(listener);
		}
	
	
	/**
	 * Run server
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
		{
		System.out.println("---------- Starting CellStore ------------");
		CellStoreMain main=new CellStoreMain();
		main.openPort();		
		System.out.println("---------- Port opened -------------------");
		try
			{
			Thread.sleep(1000);
			}
		catch (InterruptedException e)
			{
			} 
		System.out.println("---------- Testing connection -------------------");
		
		RCellStore rcs=new RCellStore();
		rcs.init("localhost");
		
//		System.out.println("did init. now auth:");
//		rcs.authenticate("mahogny", "123");
		
		System.out.println(rcs.getClustering(0));
		
		
		
		System.out.println("---------- Done -------------------");
		
//		System.exit(0);
		
		}
		
	}
