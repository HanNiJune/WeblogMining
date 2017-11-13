package research.phs;
import java.util.*;

import research.weblog.Settings;
import research.weblog.WebLogTimeSession;

public class PerfectHash
{
	public static ArrayList<Hk> hash = new ArrayList<Hk>();
	public static TransactionDB db = new TransactionDB();
	public static ArrayList<Item> itemcounts = new ArrayList<Item>();
	public static ArrayList<Item> frequentcounts = new ArrayList<Item>();
	public static ArrayList<Item> nonfrequentcounts = new ArrayList<Item>();
	public static int minsupportcount = 20;
	
	public static ArrayList<TransactionDB> reducedbs = new ArrayList<TransactionDB>();
	public static ArrayList<ItemSets> Cks = new ArrayList<ItemSets>();
	public static ArrayList<ItemSets> Lks = new ArrayList<ItemSets>();	
	
	public static int K = 2;
	public static boolean flag = false;
	
	public static void InitDB(ArrayList<WebLogTimeSession> timesessions)
	{
		reducedbs.clear();
		Cks.clear();
		Lks.clear();
		itemcounts.clear();
		frequentcounts.clear();
		nonfrequentcounts.clear();
		
		hash.clear();
		
		db = new TransactionDB(timesessions);
		FilterItems();
		ReduceDB1();
	}
	
	public static void ProcessPerfectHash()
	{
		PrepareC2L2();
		System.out.println("Preparing C2 and L2 finished.");
		K = 2;
		flag = true;
		//ComputeHash();
	}
	
	private static void FilterItems()
	{
		for (Item iset : itemcounts)
		{
			if (iset.count >= minsupportcount)
				frequentcounts.add(iset);
			else
				nonfrequentcounts.add(iset);
		}
	}	
	
	static void ReduceDB1()
	{
		TransactionDB newdb = new TransactionDB();
		
		for (Transaction t : db.transactions)
		{
			ArrayList<String> temp = new ArrayList<String>();
			
			for (String s : t.items)
			{
				Item it = new Item(s);
				if (frequentcounts.contains(it))
					temp.add(s);				
			}
			
			if (temp.size() <= 1)
				continue;
			Transaction nt = new Transaction();
			nt.items = temp;
			newdb.transactions.add(nt);
			
		}
		reducedbs.add(newdb);
	}
		
	static void PrepareC2L2()
	{
		TransactionDB rdb1 = reducedbs.get(0);
		Hk h2 = new Hk();
		
		for (Transaction t: db.transactions)
		{			
			for (int i = 0; i < t.items.size()-1; i++)
			{
				String si = t.items.get(i);
				Item item = new Item(si);				
				
				for (int j=i+1; j <t.items.size(); j++)
				{
					String ti = t.items.get(j);
					ArrayList<String> items = new ArrayList<String>();
					items.add(si);
					items.add(ti);
					ItemSet iset = new ItemSet(items);
					h2.AddItemSet(iset);
				}
			}			
		}
		
		ItemSets c2 = new ItemSets(h2.GetComputedItemsets());
		ItemSets l2 = c2.Filter(minsupportcount);
		
		System.out.println("c2 count = " + c2.size());
		System.out.println("l2 count = " + l2.size());
		Cks.add(c2);
		Lks.add(l2);
		
		hash.add(h2);
		
		System.out.println("Total ck count = " + Cks.size());
		System.out.println("Total lk count = " + Lks.size());
	}
	
	public static void ComputeHashStep()
	{
		System.out.println("Total ck count = " + Cks.size());
		System.out.println("Total lk count = " + Lks.size());
		System.out.println("Preparing Reduced DB for k = " + K);
		K++;
		ItemSets lp = Lks.get(Lks.size()-1);
		
		TransactionDB dbp = reducedbs.get(reducedbs.size()-1);
		TransactionDB dbk = ReduceDB(dbp, K, lp);
		if (dbk.transactions.size() < minsupportcount)
		{
			flag = false;
			K --;
			return;			
		}
		
		System.out.println("Total Transaction Count for K - 1 = " + dbp.transactions.size());
		System.out.println("Total Transaction Count for K - 1 = " + dbk.transactions.size());
		reducedbs.add(dbk);
		
		
		System.out.println("Preparing Ck for k = " + K);
		ItemSets ck = ComputeCK(K, dbk);			
		System.out.println("Ck Count = " + ck.size());
		System.out.println("Preparing Lk for k = " + K);
		ItemSets lk = ck.Filter(minsupportcount);
		System.out.println("Lk Count = " + lk.size());
		Cks.add(ck);
		Lks.add(lk);
	}
	
	static void ComputeHash()
	{
		int k = 3;
		ItemSets lp = Lks.get(Lks.size()-1);
		System.out.println("Preparing Reduced DB for k = " + k);
		TransactionDB dbp = reducedbs.get(reducedbs.size()-1);
		TransactionDB dbk = ReduceDB(dbp, k, lp);
		
		
		while (dbk.transactions.size() > 0)
		{
			reducedbs.add(dbk);
			System.out.println("Preparing Ck for k = " + k);
			ItemSets ck = ComputeCK(k, dbk);			
			System.out.println("Preparing Lk for k = " + k);
			ItemSets lk = ck.Filter(minsupportcount);
			Cks.add(ck);
			Lks.add(lk);			
			k++;
			System.out.println("Preparing Reduced DB for k = " + k);
			dbk = ReduceDB(dbk, k, lk);
		}
		
	}
	
	static ItemSets ComputeCK0(int k, TransactionDB dbk)
	{
		ItemSets ck = new ItemSets();
		ItemSets plk = Lks.get(Lks.size()-1);
		
		ItemSets temp = new ItemSets();
		
		for (ItemSet is : plk)
		{
			for (Item fi:frequentcounts)
			{
				if (is.itemsets.contains(fi.item))
					continue;
				
				ItemSet newset = is.Clone();
				newset.itemsets.add(fi.item);
				if (temp.contains(newset))
					continue;
				temp.add(newset);
				
				
				for (Transaction t : dbk.transactions)
				{
					if (t.items.containsAll(newset.itemsets))
					{
						newset.count++;
					}
				}
				
				if (newset.count > 1)
					ck.add(newset);
			}
		}
		
		return ck;
	}
	
	static ItemSets  ComputeCK(int k, TransactionDB dbk)
	{
		ItemSets ck = new ItemSets();
		
		Hk hk = new Hk(k);
		
		for (Transaction t : dbk.transactions)
		{
			if (t.items.size() == k)
			{
				ItemSet set = new ItemSet(t.items);
				ck.add(set);
				continue;
			}
			
			ArrayList<ArrayList<String>> newsets = GeneratePattern(t.items, k);
			
			for(ArrayList<String> sets : newsets)
			{
				ItemSet set = new ItemSet(sets);
				hk.AddItemSet(set);
			}
			
		}
		
		hash.add(hk);
		ck = new ItemSets(hk.GetComputedItemsets());
		
		return ck;
	}
	
	static TransactionDB ReduceDB(TransactionDB dbp, int k, ItemSets lp)
	{
		TransactionDB dbk = new TransactionDB();
		for (Transaction t : dbp.transactions)
		{
			int tsize = t.items.size();
			if (tsize < k)
				continue;
			
			/*Transaction nt = new Transaction();
			nt.items = (ArrayList<String>)t.items.clone();
			int size = nt.items.size();
			
			for (int i = 0 ; i < size; i++)				
			{
				String s = t.items.get(i);
				ArrayList<String> temp = (ArrayList<String>)t.items.clone();
				temp.remove(s);
				ItemSet tempset = new ItemSet(temp);
				if (lp.indexOf(tempset)<0)
				{
					flag = false;
					break;
				}
			}
*/			
			if (tsize == k)
			{
				boolean flag = true;
				for (int i = 0 ; i < t.items.size(); i++)
				//for (String s : t.items)
				{
					String s = t.items.get(i);
					ArrayList<String> temp = (ArrayList<String>)t.items.clone();
					temp.remove(s);
					ItemSet tempset = new ItemSet(temp);
					if (lp.indexOf(tempset)<0)
					{
						flag = false;
						break;
					}
				}
				
				if (flag)
					dbk.transactions.add(t);
				continue;
			}
			
			dbk.transactions.add(t);
		}
		return dbk;
	}
	
	static ArrayList<ArrayList<String>> GeneratePattern(ArrayList<String> sets, int k)
	{
		int count = sets.size();		
		
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		for (int i = 0; i <= (count - k); i++)
		{	
			
			int counter = 1;
			
			for (int j = i+1; j < (count - k+2); j++)
			{
				ArrayList<String> tt = new ArrayList<String>();
				tt.add(sets.get(i));
				for (int l = j; l < j+k-1; l++)
				{
					tt.add(sets.get(l));
				}
				temp.add(tt);
			}
		}
		
		return temp;
	}
	
	static ArrayList<ArrayList<String>> GeneratePatternOld(ArrayList<String> sets, int k)
	{
		int count = sets.size();		
		
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		for (int i = 0; i <= (count - k); i++)
		{
			ArrayList<String> t = new ArrayList<String>();
			t.add(sets.get(i));
			
			ArrayList<ArrayList<String>> t0 = new ArrayList<ArrayList<String>>();
			t0.add(t);
			
			int counter = 1;
			ArrayList<ArrayList<String>> t1 = new ArrayList<ArrayList<String>>();
			while (counter < k)
			{
				t1.clear();
				for (int j = i + counter; j < count-(k-counter); j++)
				{
					for (ArrayList<String> tt0 : t0)
					{	
						ArrayList<ArrayList<String>> tt = GenerateSet(tt0, sets.subList(j, count));						
						t1.addAll(tt);
					}
				}
				
				t0 = (ArrayList<ArrayList<String>>)t1.clone();
				counter ++;
			}
			
			temp.addAll(t0);
		}
		//System.out.println("Hello");
		//System.out.println(temp);
		
		return temp;
	}
	
	public static ArrayList<ArrayList<String>> GenerateSet(ArrayList<String> current, List<String>Extra)
	{
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		for (String e : Extra)
		{
			ArrayList<String> temp = (ArrayList<String>)current.clone();
			if (temp.indexOf(e) < 0)
			{
				temp.add(e);
				results.add(temp);
			}
		}
		
		return results;
	}
	
	public static void main(String []args)
	{
		ArrayList<String> sets = new ArrayList<String>();
		sets.add("a");
		sets.add("b");
		sets.add("c");
		sets.add("d");
		sets.add("e");
		sets.add("f");
		sets.add("g");
		sets.add("h");
		sets.add("i");
		sets.add("j");
		int k = 3;
		
		int count = sets.size();
		
		
		
		/*
		for (int i = 0; i <= (count - k); i++)
		{
			ArrayList<String> t = new ArrayList<String>();
			t.add(sets.get(i));
			
			ArrayList<ArrayList<String>> t0 = new ArrayList<ArrayList<String>>();
			t0.add(t);
			
			int counter = 1;
			ArrayList<ArrayList<String>> t1 = new ArrayList<ArrayList<String>>();
			while (counter < k)
			{
				t1.clear();
				for (int j = i + counter; j < count-(k-counter); j++)
				{
					for (ArrayList<String> tt0 : t0)
					{	
						ArrayList<ArrayList<String>> tt = GenerateSet(tt0, sets.subList(j, count));
						//System.out.println(tt);
						t1.addAll(tt);
					}
				}
				
				t0 = (ArrayList<ArrayList<String>>)t1.clone();
				counter ++;
			}
			
			temp.addAll(t0);
		}
		System.out.println("Hello");
		System.out.println(temp);
		*/
		ArrayList<ArrayList<String>> temp2 = GeneratePattern(sets, k);
		System.out.println("Hello from my program");
		System.out.println(temp2);
		System.out.println(temp2.size());
		
		ArrayList<ArrayList<String>> temp = GeneratePatternOld(sets, k);
		System.out.println("Hello from my program");
		System.out.println(temp);
		System.out.println(temp.size());
	}
}