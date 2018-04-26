package cellstore.r;

import java.io.IOException;
import java.net.UnknownHostException;

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
	 * Easy access to an R matrix
	 * 
	 *[1,2;
	 *[3,4]  becomes [1,2,3,4]
	 */
	public static class RMatrix
		{
		double[] matrix;
		public int nrow, ncol;
		
		public RMatrix(double[] matrix, int[] matrixDim)
			{
			this.matrix=matrix;
			nrow=matrixDim[0];
			ncol=matrixDim[1];
			}
		
		public double get(int row, int col)
			{
			return matrix[row*col+col];
			}
		}
	
	
	/**
	 * Upload a new projection
	 * 
	 * @param matrix
	 * @param matrixDim
	 * @param name
	 * @return
	 */
	public boolean uploadProjection(double[] matrix, int[] matrixDim, String name)
		{
		RMatrix m=new RMatrix(matrix, matrixDim);
		
		try
			{
			CellProjection p=new CellProjection();
			p.name=name;

			p.allocate(m.nrow);
			
			for(int i=0;i<m.nrow;i++)
				{
				p.x[i]=m.get(i,0);
				p.y[i]=m.get(i,1);
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


	public CellClustering getClustering(int i) throws IOException
		{
		return conn.getClustering(i);
		}

	
		
	/*
	public static double[] test()
		{
		return new double[0];
		}
	*/
	}
