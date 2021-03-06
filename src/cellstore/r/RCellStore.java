package cellstore.r;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import cellstore.db.CellClustering;
import cellstore.db.CellProjection;
import cellstore.server.CellStorePortListener;
import cellstore.server.conn.CellStoreConnection;
import cellstore.server.conn.CellStoreConnectionServer;

/**
 * Connection object for R
 * 
 * Can possible share this code with python?
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class RCellStore
	{

	CellStoreConnection conn;
	
	/**
	 * Initialize the connection
	 * 
	 * @param address
	 * @param port
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean init(String address, int port)
		{
		try
			{
			conn=new CellStoreConnectionServer(address, port);
			return true;
			}
		catch (UnknownHostException e)
			{
			System.out.println("Cannot find host");
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		return false;
		}

	
	public boolean init(String address)
		{
		return init(address, CellStorePortListener.DEFAULT_PORT);
		}

	/**
	 * 
	 * Authenticate connection
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean authenticate(String user, String password)
		{
		try
			{
			return conn.authenticate(user, password);
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		return false;
		}
		
	/**
	 * Upload a count table
	 * 
	 * @param matrix
	 * @param name
	 * @return
	 */
	public boolean uploadCount(double[][] matrix, String name)
		{
		
		return true;
		}

	
	/**
	 * Upload a new projection
	 * 
	 * @param matrix
	 * @param matrixDim
	 * @param name
	 * @return
	 */
	public boolean uploadProjection(RMatrixD mCoordinates, RMatrixI mCellID,
			String name)
		{
//		RMatrixDouble mCoordinates=new RMatrixDouble(coordinates, dimCoordinates);
	//	RMatrixInt mCellid=new RMatrixInt(cellid, dimCellid);
		
		int numcell=mCellID.nrow;
		try
			{
			CellProjection p=new CellProjection();
			p.name=name;

			p.allocate(numcell);
			for(int i=0;i<numcell;i++)
				{
				p.indexCellSet[i]=mCellID.get(i, 0);
				p.indexCell[i]=mCellID.get(i, 1);
				p.x[i]=mCoordinates.get(i,0);
				p.y[i]=mCoordinates.get(i,1);
				}
			
			System.out.println("Uploading projection");
			
			conn.putProjection(p);
			
			return true;
			}
		catch (IOException e)
			{
			e.printStackTrace();
			return false;
			}
		}


	
	/**
	 * Get one clustering
	 * 
	 * @param i
	 * @return
	 * @throws IOException
	 */
	public CellClustering getClustering(int i) throws IOException
		{
		return conn.getClustering(i);
		}

	
	/**
	 * Get a list of clusterings
	 * 
	 * @return
	 */
	public RDataFrame listClustering()
		{
		ArrayList<Object> listID=new ArrayList<>();
		ArrayList<Object> listName=new ArrayList<>();
		ArrayList<Object> listNumCell=new ArrayList<>();
		try
			{
			for(int id:conn.getListClusterings())
				{
				CellClustering clustering=conn.getClustering(id);
				listID.add(new Integer(id));
				listName.add(clustering.name);
				listNumCell.add(new Integer(clustering.getNumCell()));
				}
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		RDataFrame df=new RDataFrame();
		df.add("id",listID);
		df.add("name",listName);
		df.add("num.cell",listNumCell);
		return df;
		}
		

	}
