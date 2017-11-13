package research.weblog;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class URLHierarchy {
	public String link = "";
	public ArrayList<URLHierarchy> children = new ArrayList<URLHierarchy>();

	public boolean equals(Object o)
	{
		URLHierarchy other = (URLHierarchy)o;
		return (other.link.equals(link));
	}
	
	public URLHierarchy()
	{
		
	}
	
	public URLHierarchy(String l)
	{
		link = l;
	}
	
	public URLHierarchy Parse(ArrayList<String> allurl)
	{
		URLHierarchy h = new URLHierarchy();
		
		for (String url : allurl)
		{
			StringTokenizer token = new StringTokenizer(url, "\\/");
			
			URLHierarchy current = h;
			while (token.hasMoreTokens())
			{
				String t = token.nextToken();
				URLHierarchy c = new URLHierarchy(t);
				if (current.children.contains(c))
				{
					current = current.children.get(current.children.indexOf(c));
					continue;
				}
				else
				{
					current.children.add(c);
					current = c;
				}
			}
		}
		
		return h;
	}
	
	public URLHierarchy Parse(String url)
	{
		URLHierarchy h = new URLHierarchy();
		
		StringTokenizer token = new StringTokenizer(url, "\\/");	
		
		URLHierarchy current = h;
		while (token.hasMoreTokens())
		{
			String t = token.nextToken();
			URLHierarchy c = new URLHierarchy(t);
			if (current.children.contains(c))
			{
				current = c;
				continue;
			}
			else
			{
				children.add(c);
				current = c;
			}
		}
		
		return h;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("<td valign=top>" + link + "</td><td valign=top><table border=1 cellspacing=3>");
		for (URLHierarchy c : children)
		{
			sb.append("<tr>");
			sb.append(c.toString());
			sb.append("</tr>");
		}
		sb.append("</table></td>");
		return sb.toString();
	}
	
}
