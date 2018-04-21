package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import hdf.GeneexpStoreHdf;

public class CellStoreMain
	{
	public CellStoreDB db=new CellStoreDB();

	public CellStoreMain() throws IOException
		{
		//Read the count matrix
		CellSetFile f=new CellSetFile();
	/*	
		CellSet cellset=readCountTable(new File("/home/mahogny/javaproj/bifurlo.git/bifurlo/data/rosmir/CD45-/sc_data.csv"));
		cellset.cellsetName="CD45-";
		f.setCellSet(cellset);
		db.datasets.cellsets.put(0, f);
*/
		GeneexpStoreHdf.scanExpdata(db);
		CellSet cellset=db.datasets.cellsets.get(0).getCellSet();
		
		
		//Read the layout
		CellDimRed dimred=readDimRed(new File("other/sc_pca.csv"), cellset);
		dimred.name="tSNE";
		db.datasets.dimreds.put(0,dimred);

		//Read the clustering
		CellClustering clust=readClustering(new File("other/sc_samplemeta.csv"), cellset);
		clust.name="clustering";
		//sample,cluster_cd45minus,organism
		db.datasets.clusterings.put(0,clust);

		
		//later problem
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
	public CellClustering readClustering(File f, CellSet cellset) throws IOException
		{
		BufferedReader bi=new BufferedReader(new FileReader(f));
		
		String xName="cluster_cd45minus";
		
		CellClustering clustering=new CellClustering();
		clustering.cellsets.add(cellset);
		
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
			
			cellsetIndex.add(0);
			cellsetCell.add(cellset.getIndexOfCell(geneName));
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
	public CellDimRed readDimRed(File f, CellSet cellset) throws IOException
		{
		BufferedReader bi=new BufferedReader(new FileReader(f));
		
		String xName="PC1_cd45minus";
		String yName="PC2_cd45minus";
		
		CellDimRed dimred=new CellDimRed();
		dimred.cellsets.add(cellset);
		
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
			
			cellsetIndex.add(0);
			cellsetCell.add(cellset.getIndexOfCell(geneName));
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
