package cellstore.r;

import java.io.IOException;
import java.net.UnknownHostException;

import cellstore.server.conn.CellStoreConnection;
import cellstore.server.conn.CellStoreConnectionServer;

/**
 * Connection object for R
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
			System.out.println(e);
			e.printStackTrace();
			}
		return false;
		}
	
	/**
	 * 
	 * Authenticate connection
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean init(String user, String password)
		{
		try
			{
			return conn.authenticate(user, password);
			}
		catch (IOException e)
			{
			System.out.println(e.getMessage());
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

	
		
	/*
	public static double[] test()
		{
		return new double[0];
		}
	*/
	}
