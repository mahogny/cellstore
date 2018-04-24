package cellstore.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import cellstore.db.CellClustering;
import cellstore.db.CellDimRed;
import cellstore.db.CellSet;
import cellstore.db.CellSetFile;
import cellstore.db.CellStoreDB;
import cellstore.db.GeneLinCounts;
import cellstore.hdf.GeneexpStoreHdf;

/**
 * Running of the database only
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreMain
	{
	public CellStoreDB db=new CellStoreDB();

	public CellStoreMain() throws IOException
		{
		db.readUsers();
		
		//later problem
		scanDataCellSet();
		scanDataClust();
		scanDataDimred();
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
					CellSet cellset=readCountTable(filecsv);
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
	public void scanDataDimred() throws IOException
		{
		File fexpdir=new File("data/dimred");
		for(File f:fexpdir.listFiles())
			if(f.isDirectory())
				{
				int id=Integer.parseInt(f.getName());
				System.out.println(id);
				
				CellDimRed dimred=new CellDimRed();
				
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
					readDimRed(dimred, filecsv, cellset);
					}
				else
					throw new IOException("Invalid dimred");
				db.datasets.dimreds.put(id,dimred);
				}
		}
		
	
	public void scanDataClust() throws IOException
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
					readClustering(clust, filecsv, cellset);
					}
				else
					throw new IOException("Invalid cell clustering");
				db.datasets.clusterings.put(id,clust);
				}

		}
		
	
	public static void main(String[] args) throws IOException
		{
		new CellStoreMain();
		}
	
	
	
	
	/**
	 * 
	 * Read a clustering from CSV, assuming it all refers to a single cellset
	 * 
	 * @param f
	 * @param cellset
	 * @return
	 * @throws IOException
	 */
	public CellClustering readClustering(CellClustering clustering, File f, CellSetFile cellset) throws IOException
		{
		BufferedReader bi=new BufferedReader(new FileReader(f));
		
		String xName="cluster_cd45minus";
		
		//clustering.cellsets.add(cellset);
		
		ArrayList<Integer> cellsetIndex=new ArrayList<Integer>();
		ArrayList<Integer> cellsetCell=new ArrayList<Integer>();
		ArrayList<String> cluster=new ArrayList<String>();

		
		////// Read the header and figure out which columns are the x & y axis
		int xi=-1;
		StringTokenizer stok=new StringTokenizer(bi.readLine(), ",");
		stok.nextToken();  //bit scary that I skip it!
		for(int i=0;stok.hasMoreTokens();i++)
			{
			String s=stok.nextToken();
			//System.out.println("---"+s);
			if(s.equals(xName))
				xi=i;
			}
		//System.out.println("Got index "+xi+"   "+yi);
		
		////// Read the positions
		String line;
		while((line=bi.readLine())!=null)
			{
			stok=new StringTokenizer(line, ",");
			String geneName=stok.nextToken();

			String thecluster="";
			
			for(int i=0;stok.hasMoreTokens();i++)
				{
				String s=stok.nextToken();
				if(i==xi)
					thecluster=s;
				}
			
			cellsetIndex.add(cellset.id);
			cellsetCell.add(cellset.getCellSet().getIndexOfCell(geneName));
			cluster.add(thecluster);
			}
		
		clustering.set(cellsetIndex,cellsetCell, cluster);
		
		bi.close();
		return clustering;
		}
		
	

	
	
	/**
	 * 
	 * Read a dimensionality reduction from CSV, assuming it all refers to a single cellset
	 * 
	 * @param f
	 * @param cellset
	 * @return
	 * @throws IOException
	 */
	public CellDimRed readDimRed(CellDimRed dimred, File f, CellSetFile cellset) throws IOException
		{
		BufferedReader bi=new BufferedReader(new FileReader(f));
		
		String xName="PC1_cd45minus";
		String yName="PC2_cd45minus";
		
		//CellDimRed dimred=new CellDimRed();
		//dimred.cellsets.add(cellset);
		
		ArrayList<Integer> cellsetIndex=new ArrayList<Integer>();
		ArrayList<Integer> cellsetCell=new ArrayList<Integer>();
		ArrayList<Double> x=new ArrayList<Double>();
		ArrayList<Double> y=new ArrayList<Double>();

		
		////// Read the header and figure out which columns are the x & y axis
		int xi=-1;
		int yi=-1;
		StringTokenizer stok=new StringTokenizer(bi.readLine(), ",");
		//stok.nextToken();  //bit scare that I skip it!
		for(int i=0;stok.hasMoreTokens();i++)
			{
			String s=stok.nextToken();
			//System.out.println("---"+s);
			if(s.equals(xName))
				xi=i;
			if(s.equals(yName))
				yi=i;
			}
		//System.out.println("Got index "+xi+"   "+yi);
		
		////// Read the positions
		String line;
		while((line=bi.readLine())!=null)
			{
			stok=new StringTokenizer(line, ",");
			String geneName=stok.nextToken();

			double theX=0;
			double theY=0;
			
			
			for(int i=0;stok.hasMoreTokens();i++)
				{
				String s=stok.nextToken();
				if(i==xi)
					theX=Double.parseDouble(s);
				if(i==yi)
					theY=Double.parseDouble(s);
				}
			
			cellsetIndex.add(cellset.id);
			cellsetCell.add(cellset.getCellSet().getIndexOfCell(geneName));
			x.add(theX);
			y.add(theY);
			}
		
		dimred.set(cellsetIndex,cellsetCell, x,y);
		
		
		bi.close();
		return dimred;
		}
		
	
	
	/**
	 * Read a count table from CSV file
	 */
	public CellSet readCountTable(File f) throws IOException
		{
		CellSet cellset=new CellSet();
		
		BufferedReader bi=new BufferedReader(new FileReader(f));

		//First all the cell names
		StringTokenizer stok=new StringTokenizer(bi.readLine(), ",");
		//stok.nextToken();  //scare to skip
		ArrayList<String> listCellNames=new ArrayList<String>();
		while(stok.hasMoreTokens())
			listCellNames.add(stok.nextToken());
		
		ArrayList<String> geneNames=new ArrayList<String>();
		ArrayList<GeneLinCounts> countList=new ArrayList<GeneLinCounts>();
		
		//For all genes
		String line;
		while((line=bi.readLine())!=null)
			{
			stok=new StringTokenizer(line, ",");
			
			String geneName=stok.nextToken();
			geneNames.add(geneName);
			
			ArrayList<Integer> cellid=new ArrayList<Integer>(listCellNames.size());
			ArrayList<Double> counts=new ArrayList<Double>(listCellNames.size());
			for(int i=0;i<listCellNames.size();i++)
				{
				double count=Double.parseDouble(stok.nextToken());
				if(count!=0)
					{
					cellid.add(i);
					counts.add(count);
					}
				}
			
			GeneLinCounts cnt=new GeneLinCounts();
			cnt.set(cellid,counts);
			
			countList.add(cnt);
			}
		
		cellset.cellnames=listCellNames.toArray(new String[]{});
		cellset.genename=geneNames.toArray(new String[]{});
		//cellset.geneCount=countList.toArray(new GeneLinCounts[]{});
		
		bi.close();
		return cellset;
		}
		
	}
