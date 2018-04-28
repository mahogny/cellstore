package cellstore.r;

import java.util.ArrayList;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class RDataFrame
	{

	public ArrayList<String> name=new ArrayList<>();
	
	/**
	 * List of rows
	 */
	public ArrayList<ArrayList<Object>> data=new ArrayList<>();

	/**
	 * Add a column
	 */
	public void add(String string, ArrayList<Object> listID)
		{
		name.add(string);
		data.add(listID);
		}
	
	public String[] getNames()
		{
		return name.toArray(new String[0]);
		}

	public int getNumRow()
		{
		return data.get(0).size();
		}
	
	/**
	 * An array of columns
	 * 
	 * TODO handle empty dataframe
	 */
	public Object[] getColumns()
		{
		//Allocate columns
		int ncol=name.size();

		//Convert columns to scalar arrays
		ArrayList<Object> outarray=new ArrayList<>();
		for(int i=0;i<ncol;i++)
			{
			ArrayList<Object> onecol=data.get(i);
			Object ob=onecol.get(0);
			if(ob instanceof String)
				{
				outarray.add(onecol.toArray(new String[0]));
				/*String[] arr=new String[onecol.size()];
				for(int j=0;j<arr.length;j++)
					arr[j]=(String)onecol.get(j);
				outarray.add(arr);*/
				}
			else if(ob instanceof Integer)
				{
				int[] arr=new int[onecol.size()];
				for(int j=0;j<arr.length;j++)
					arr[j]=(Integer)onecol.get(j);
				outarray.add(arr);
				}
			else if(ob instanceof Double)
				{
				double[] arr=new double[onecol.size()];
				for(int j=0;j<arr.length;j++)
					arr[j]=(Double)onecol.get(j);
				outarray.add(arr);
				}
			else
				throw new RuntimeException("Failed to handle type "+ob.getClass());
			}
		
		//Convert the list to an object[]
		return outarray.toArray(new Object[0]);
		}
		
	}
