package research.weblog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyURL
{
	int urlid = 0;
	String url = "";
	String category = "";
	
	public boolean equals(Object o)
	{
		
		if (getClass() == o.getClass())
		{
			MyURL ou = (MyURL)o;
			return (url.equalsIgnoreCase(ou.url));
		}
		
		
		return (url.equalsIgnoreCase(o.toString()));
	}
	
	
	public void Save()
	{
		Connection conn = DBConnector.Connect();
		
		urlid = DBConnector.GetNewID("URL", "URLID", conn);
		
		String strSQL = "INSERT INTO URL VALUES (" + urlid + ", '" + url + "', '" + category + "')";
		DBConnector.ExecuteStatement(strSQL, conn);
		
		DBConnector.Disconnect(conn);
	}
	
	public static ArrayList<MyURL> GetURLs()
	{
		Connection conn = DBConnector.Connect();
		
		ResultSet rs = DBConnector.GetQuery("SELECT * FROM URL", conn);
		
		ArrayList<MyURL> urls = new ArrayList<MyURL> ();
		
		try
		{
			while (rs.next())
			{
				while (rs.next())
				{
					MyURL mu = new MyURL();
					
					mu.urlid = rs.getInt("URLID");
					mu.url = rs.getString("URL");
					mu.category = rs.getString("URLType");
					
					urls.add(mu);
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		DBConnector.Disconnect(conn);
		
		return urls;
	}
	
	public static void main(String []args)
	{
		ArrayList<MyURL> urls = GetURLs();
		
		MyURL url = new MyURL();
		url.url = "/ucsy/secondicca.do";
		
		if (urls.contains(url))
		{
			System.out.println("true");
		}
		else
		{
			System.out.println("false");
		}
		
		if (urls.indexOf(url) >= 0)
		{
			System.out.println("true1");
		}
		else
		{
			System.out.println("false2");
		}
	}
}