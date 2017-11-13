package research.weblog;
import java.util.*;
import java.sql.Connection;

public class Settings
{
	static String []nonurl = {"jpg", "gif", "bmp", "exe", "pdf", "zip", "xbm", "png", "js", "css", "htc", "ico", "txt", ".jpeg"};
	
	static ArrayList<String> nonurls = new ArrayList<String>();
	
	public static URLHierarchy root = null;
	
	static void InitURL()
	{
		nonurls.clear();
		
		for (String s : nonurl)
		{
			nonurls.add(s);
		}
	}
	
	//public static HashMap<String, String> replcements = new HashMap<String, String>();
	public static ArrayList<String> urls = new ArrayList<String>();
	
	
//	public static void SaveURL()
//	{
//		Connection conn = DBConnector.Connect();
//		
//		DBConnector.ExecuteStatement("DELETE FROM URL", conn);
//		
//		int len = urls.size();
//		
//		for (int i = 0; i < len; i++)
//		{
//			int id = i+1;
//			String url = urls.get(i);
//			
//			String strSQL = "INSERT INTO URL VALUES (" + id + ", '" + url + "', '')";
//			
//			DBConnector.ExecuteStatement(strSQL, conn);
//		}
//		
//		DBConnector.Disconnect(conn);
//	}
	
	public static void ProcessHierarchy()
	{
		URLHierarchy h = new URLHierarchy();
		System.out.println("Total Hierarchy = " + urls.size());
		System.out.println(urls);
		root = h.Parse(urls);		
	}
	
	public static String GetConcept(String link)
	{
		String up = "";
		for (URLHierarchy child : root.children)
		{
			up = GetHierarchy(child, link);
			if (up.length() > 0)
				break;
		}
		
		if (up.equals(""))
			up = link;
		return up;
	}	
	
	public static String GetConceptFromLink(String link)
	{
		String up = "";
		
		String sep = "/";
		
		int idx = link.lastIndexOf(sep);
		
		if (idx < 0)
		{
			sep = "\\";
		}
		
		idx = link.lastIndexOf(sep);
		
		if (idx < 0)
			return link;

		
		
		up = GetConcept(link.substring(idx+1));
		
		return up;
	}
	
	public static String GetConceptByOntology(String st)
	{
		String up = "";
		
		return up;
	}
	
	static String GetHierarchy(URLHierarchy parent, String link)
	{
		String p = "";
		
		if (parent.children.size() == 0)
			return p;
		
		for (URLHierarchy h : parent.children)
		{
			if (h.link.equals(link))
				return parent.link;
			
			p = GetHierarchy(h, link);
			if (p.length() > 0)
				return p;
		}
		
		return p;
	}
	
	public static void SaveLogs()
	{
		
	}
}