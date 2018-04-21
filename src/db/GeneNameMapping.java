package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * 
 * A general name mapping. Subset over the gene symbols available in the dataset
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class GeneNameMapping
	{
	///Maps Id <-> sym
	HashMap<String, LinkedList<String>> mapIdSym=new HashMap<String, LinkedList<String>>();
	HashMap<String, LinkedList<String>> mapSymId=new HashMap<String, LinkedList<String>>();
	
	public void add(String id, String symbol)
		{
		//Map id -> sym
		LinkedList<String> listsym=mapIdSym.get(id);
		if(listsym==null)
			mapIdSym.put(id, listsym=new LinkedList<String>());
		listsym.add(symbol);
		
		//Map sym -> id
		LinkedList<String> listid=mapSymId.get(id);
		if(listid==null)
			mapSymId.put(symbol, listid=new LinkedList<String>());
		listid.add(id);
		}
		
	
	public GeneNameMapping getSubset(CellSet cellset)
		{
		GeneNameMapping m=new GeneNameMapping();
		for(String geneid:cellset.genename)
			{
			LinkedList<String> listsym=mapIdSym.get(geneid);
			if(listsym!=null)
				{
				//Could take shortcuts here if not subsetting symbols
				for(String sym:listsym)
					m.add(geneid, sym);
				}
			}
		return m;
		}
	
	
	
	/**
	 * Read a mapping table from CSV file
	 */
	public void readMapping(File f) throws IOException
		{
		BufferedReader bi=new BufferedReader(new FileReader(f));
		bi.readLine(); //ensemblID, gene symbol
		String line;
		while((line=bi.readLine())!=null)
			{
			StringTokenizer stok=new StringTokenizer(line, ",");
			String geneID=stok.nextToken();
			String geneSymbol=stok.nextToken();
			add(geneID, geneSymbol);
			}
		bi.close();
		}

	
	public static GeneNameMapping getInstance()
		{
		GeneNameMapping m=new GeneNameMapping();
		try
			{
			m.readMapping(new File("e2g_mouse.csv"));
			m.readMapping(new File("e2g_human.csv"));
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		return m;
		}


	public String getIDfor(String genesym)
		{
		LinkedList<String> list=mapSymId.get(genesym);
		//System.out.println("got ids "+list);
		if(list==null || list.isEmpty())
			return "";
		else
			return list.getFirst();
		}
	}
