package research.phs;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class ItemSet {
	public SortedSet<String> itemsets = new TreeSet<String>();
	public int count = 1;
	String st = "";
	
	public ItemSet()
	{
		
	}
	
	public ItemSet(ArrayList<String> isets)
	{	
		itemsets.addAll(isets);
		//itemsets = isets;
	}
	
	@Override
	public boolean equals(Object o)
	{
		ItemSet iset = (ItemSet)o;		
		
		return iset.itemsets.equals(itemsets);
	}
	
	public ItemSet Clone()
	{
		ItemSet newset = new ItemSet();
		newset.itemsets.addAll(this.itemsets);
		return newset;
	}
	
	@Override
	public String toString()
	{
		return itemsets.toString();
	}
	
	public static void main(String []args)
	{
		ArrayList<String> isets = new ArrayList<String>();
		
		isets.add("zz");
		isets.add("xx");
		isets.add("cc");
		isets.add("yy");
		
		ItemSet iset = new ItemSet(isets);
		
		ArrayList<String> isets2 = new ArrayList<String>();
		
		isets2.add("zz");
		isets2.add("xx");
		isets2.add("cc");
		isets2.add("yy");
		
		ItemSet iset2 = new ItemSet(isets2);
		
		if (iset.equals(iset2))
			System.out.println("equal = right");
		else
			System.out.println("not equal = wrong");
		
		ItemSet iset3 = iset2.Clone();
		iset3.itemsets.add("myaaa");
		
		System.out.println(isets2.containsAll(iset2.itemsets));
		
		System.out.println(iset2);
		System.out.println(iset3);
	}
}
