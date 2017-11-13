package research.phs;
import java.util.ArrayList;

public class Hk
{
	int k = 2;
	ArrayList<BucketItem> hashtable = new ArrayList<BucketItem>();
	static int bucketcount = 7;
	
	ArrayList<ItemSet> patterns = new ArrayList<ItemSet>();
	
	public Hk()
	{
		for (int i =0; i < bucketcount; i++)
		{
			BucketItem bucket = new BucketItem();
			hashtable.add(bucket);
		}
	}
	
	public Hk(int k)
	{
		this.k = k;
		for (int i =0; i < bucketcount; i++)
		{
			BucketItem bucket = new BucketItem();
			hashtable.add(bucket);
		}
	}
	
	public void AddItemSet(ItemSet iset)
	{
		int a = 0;
		int count = iset.itemsets.size();		
		
		Object [] temp = iset.itemsets.toArray();
		for (int i = 0; i <count-1; i++)
		{
			String p = temp[i].toString();
			try
			{
				a += Integer.parseInt(p.substring(1));
			}
			catch(Exception e)
			{
				System.out.println("Error P = " + p);
			}
			
		}
		
		int b = Integer.parseInt(temp[count-1].toString().substring(1));
		int bucketno = ((10 * a) + b) % bucketcount; 
		
		BucketItem bucketitem = hashtable.get(bucketno);
		bucketitem.items.add(iset);
		hashtable.set(bucketno, bucketitem);
		/*
		try
		{
			BucketItem bucketitem = hashtable.get(bucketno);
			bucketitem.items.add(iset);
			hashtable.set(bucketno, bucketitem);
		}
		catch(Exception e)
		{
			System.out.println("Bucket no = " + bucketno);
			System.out.println("Hash Table Size" + hashtable.size());
			//throw(e);
		}
		*/
	}
	
	public ArrayList<ItemSet> GetComputedItemsets()
	{
		//ArrayList<ItemSet> isets = new ArrayList<ItemSet>();
		if (patterns.size() > 0)
			return patterns;
		for (BucketItem bi : hashtable)
		{
			ArrayList<ItemSet> sets = bi.GetItemset();
			patterns.addAll(sets);
		}
		
		return patterns;
	}
}