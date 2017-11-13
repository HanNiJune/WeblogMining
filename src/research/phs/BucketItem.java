package research.phs;
import java.util.ArrayList;

public class BucketItem
{
	ArrayList<ItemSet> items = new ArrayList<ItemSet>();
	
	public ArrayList<ItemSet> GetItemset()
	{
		ArrayList<ItemSet> sets = new ArrayList<ItemSet>();
		
		ArrayList<ItemSet> temp = items;
		
		while (temp.size() >0)
		{
			ItemSet fs = temp.remove(0);
			fs.count = 1;
			int idx = temp.indexOf(fs);
			while (idx>=0)
			{
				fs.count++;
				temp.remove(idx);
				idx = temp.indexOf(fs);
			}
			sets.add(fs);
		}
		
		return sets;
	}
}