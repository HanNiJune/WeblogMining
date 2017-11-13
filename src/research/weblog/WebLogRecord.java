package research.weblog;
public class WebLogRecord
{
	public int recordid = 0;
	public String ipaddress = "";
	public String date = "";
	public String time = "";
	public String method = "";
	public String url = "";
	public int urlidx = 0; 
	public String host = "";
	public int status = 0;
	public String browser = "";
	public String agent = "";	
	
	public String toString()
	{
		return ipaddress + "**** " + date + "**** " + time + "**** " + method + "**** " + url + "**** " + status;
	}
}