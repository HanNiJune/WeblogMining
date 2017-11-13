package research.weblog;
import java.util.ArrayList;

public class WebLogTimeSession
{
	public static long timeframe = 30 * 60 * 1000;
	
	public ArrayList<WebLogRecord> records = new ArrayList<WebLogRecord>();
	public String userip = "";
	public String starttime = "";
	
	public WebLogTimeSession()
	{
		
	}
	
	public WebLogTimeSession(String ip)
	{
		userip = ip;
	}
	
	public void AddRecord(WebLogRecord record)
	{
		records.add(record);
	}
	
	public String ToCodeString()
	{
		WebLogRecord record = records.get(0);
		String str =  record.url;
		
		for (int i = 1; i < records.size(); i++)
		{
			record = records.get(i);
			str += ", " + record.url + "(" + record.time + ")";
		}
		
		return str;
	}
}