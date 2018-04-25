package cellstore.db;

import java.util.TreeMap;

/**
 * 
 * A list of datasets. Can be the whole database, but also a grouping for a paper
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreDatasets
	{
	public int id;
	public String groupName="";
	public Object ownerID;
	
	public TreeMap<Integer,CellSetFile> cellsets=new TreeMap<Integer, CellSetFile>();
	public TreeMap<Integer,CellClustering> clusterings=new TreeMap<Integer, CellClustering>();
	public TreeMap<Integer,CellProjection> projections=new TreeMap<Integer, CellProjection>();
	public TreeMap<Integer,CellConnectivity> connectivity=new TreeMap<Integer, CellConnectivity>();
	}
