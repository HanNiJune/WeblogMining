package research.phs;
import java.util.ArrayList;

import research.weblog.WebLogRecord;
import research.weblog.WebLogTimeSession;

public class Transaction
{
	ArrayList<String> items = new ArrayList<String>();
	public Transaction()
	{
		
	}
	
	public Transaction(WebLogTimeSession session)
	{
		for (WebLogRecord lr : session.records)
		{
			if (items.contains(lr.url))
				continue;
			Item iset = new Item(lr.url);
			
			int idx = PerfectHash.itemcounts.indexOf(iset);
			if (idx < 0)
			{
				PerfectHash.itemcounts.add(iset);				
			}
			else
			{
				PerfectHash.itemcounts.get(idx).count++;
			}
			
			items.add(lr.url);
		}
	}
	
	public String toString()
	{
		return items.toString();
	}
	
}