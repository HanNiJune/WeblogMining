package research.phs;

import java.util.ArrayList;

public class ItemSets extends ArrayList<ItemSet> {

	/**
	 * @param args
	 */
	
	
	public ItemSets()
	{
		
	}
	
	public ItemSets(ArrayList<ItemSet> sets)
	{
		this.addAll(sets);
	}
	
	public ItemSets Filter(int minsup)
	{
		ItemSets lset = new ItemSets();
		for (ItemSet s : this)
		{
			if (s.count < minsup)
				continue;
			lset.add(s);
		}
		
		return lset;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
