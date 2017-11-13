package research.weblog;
import java.util.ArrayList;
import java.util.Date;

public class WebLogUserSession
{
	public ArrayList<WebLogRecord> records = new ArrayList<WebLogRecord>();
	public String userip = "";
	
	public ArrayList<WebLogTimeSession> timesessions = new ArrayList<WebLogTimeSession>();
	public ArrayList<String> times = new ArrayList<String>();
	
	public WebLogUserSession()
	{
		
	}
	
	public WebLogUserSession(String ip)
	{
		userip = ip;
	}
	
	WebLogTimeSession GetTimeSession(WebLogRecord record)
	{
		int len = times.size();
		for (int i = 0; i < len; i++)
		{
			String pt = times.get(i);
			Date d1 = new Date(pt);
			String nt = record.date + " " + record.time;
			Date d2 = new Date(nt);
			long l1 = d1.getTime();
			long l2 = d2.getTime();
			
			if (l2 > l1 && l2 - l1 <= WebLogTimeSession.timeframe)
				return timesessions.get(i);
		}
		
		return null;
	}
	
	public void AddRecord(WebLogRecord record)
	{
		records.add(record);
		
		int size = times.size();
		
		String lasttime = "";
		
		if (size > 0)
		{
			lasttime = times.get(size-1);
			
			long lt = Date.parse(lasttime);
			long rt = Date.parse(record.date + " " + record.time);
			
			long diff = rt - lt;
			
			if (diff < WebLogTimeSession.timeframe)
			{
				WebLogTimeSession session = timesessions.get(size-1);
				session.records.add(record);
				timesessions.set(size-1, session);
			}
			else
			{
				WebLogTimeSession session = new WebLogTimeSession();
				session.records.add(record);
				times.add(record.date + " " + record.time);
				session.userip = record.ipaddress;
				timesessions.add(session);
			}
		}
		else
		{
			WebLogTimeSession session = new WebLogTimeSession();
			
			session.AddRecord(record);
			
			//timesessions.add(session);
			times.add(record.date + " " + record.time);
			session.userip = record.ipaddress;
			
			timesessions.add(session);
		}
	}
}