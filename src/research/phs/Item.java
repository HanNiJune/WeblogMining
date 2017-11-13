package research.phs;

import java.util.ArrayList;

public class Item {
	public String item = "";
	public int count = 0;
	
	public Item()
	{
		
	}
	
	public Item(String i)
	{
		item = i;
		count = 1;
	}
	
	public Item(String i, int c)
	{
		item = i;
		count = c;
	}
	
	@Override 
	public boolean equals(Object o)
	{
		Item other = (Item)o;
		return other.item.equals(item);
	}
	
	public static void main(String []args)
	{
		ArrayList<Item> sets = new ArrayList<Item>();
		
		sets.add(new Item("AA"));
		sets.add(new Item("BB"));
		sets.add(new Item("CC"));
		
		Item ss = new Item("AA");
		System.out.println(sets.indexOf(ss));
	}
}
