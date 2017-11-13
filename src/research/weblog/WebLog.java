package research.weblog;
import java.io.*;
import java.util.*;
import java.util.regex.*;


public class WebLog
{
	String logfilename = "";
	public ArrayList<WebLogRecord> records = new ArrayList<WebLogRecord>();	
	 
	public ArrayList<WebLogUserSession> usersessions = new ArrayList<WebLogUserSession>();
	public ArrayList<String> sessionips = new ArrayList<String>();
	
	String []dates = {"jan", "feb", "mar", "apr", "may", "jun",	"jul", "aug", "sep", "oct", "nov", "dec"};
	
	
	public WebLog()
	{
		
	}
	
	public WebLog(String filename)
	{
		logfilename = filename;
		//ReadFile();
	}
	
	public void ReadFile()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(logfilename));
			
			String line = "";
			
			while ((line = reader.readLine())!=null)
			{
				ArrayList<String> words = GetWords(line.toLowerCase());
				
				WebLogRecord record = ParseRecord(words);
				
				if (record == null || record.url.length() == 0)
					continue;
					
				if (record.status < 200 || record.status > 299)
					continue;	
				
				if (record.status >199 && record.status < 201)				
					records.add(record);
				else
					continue;
				
				int idx = sessionips.indexOf(record.ipaddress);
				
				if (idx >= 0)
				{
					WebLogUserSession session = usersessions.get(idx);
					session.AddRecord(record);
					usersessions.set(idx, session);
				}
				else
				{
					WebLogUserSession session = new WebLogUserSession(record.ipaddress);
					session.AddRecord(record);
					sessionips.add(record.ipaddress);
					usersessions.add(session);
					
				}
			}
			
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	ArrayList<String> GetWords(String line2)
	{
		String line = line2.replace("[", "");
		line = line.replace("]", "");
		
		boolean sflag = true;
		boolean dflag = false;
		
		ArrayList<String> words = new ArrayList<String>();
		
		String curword = "";
		
		int len = line.length();
		
		for (int i = 0; i < len; i++)
		{
			char c = line.charAt(i);
			
			if (c == ' ' && !dflag)
			{
				if (curword.length() > 1)
				{
					if (curword.toLowerCase().startsWith("get") || curword.toLowerCase().startsWith("post"))
					{
						StringTokenizer token = new StringTokenizer(curword);
						
						while (token.hasMoreTokens())
						{
							String word = token.nextToken();
							words.add(word);
						}
					}
					else
					{
						words.add(curword);
					}
					
					
					curword = "";
				}
				continue;
			}
			
			if (c == '"')
			{
				if (dflag)
				{
					dflag = false;
					
					//if (curword.length() > 1)
					//	words.add(curword);
						
					//curword = "";
					
					if (curword.length() > 1)
					{
						if (curword.toLowerCase().startsWith("get") || curword.toLowerCase().startsWith("post"))
						{
							StringTokenizer token = new StringTokenizer(curword);
							
							while (token.hasMoreTokens())
							{
								String word = token.nextToken();
								words.add(word);
							}
						}
						else
						{
							words.add(curword);
						}
						
						
						curword = "";
					}
				}
				else
				{
					dflag = true;
				}
				
				continue;
			}
			
			curword = curword + c;
		}
		
		return words;
	}
	
	
	WebLogRecord ParseRecord(ArrayList<String> words)
	{
		WebLogRecord record = new WebLogRecord();
		
		int j = 0;
		
		int get = -2;		
		
		
		for(String data : words)
		{			
			String data1 = data.trim();
			
			data1 = data1.replace("\"", "");
			
			if (IsIPPattern(data1))
			{
				record.ipaddress = data1;
				j++;
				continue;
			}
			
			if (IsTimeStamp(data1))
			{				
				record.time = data1;
				j++;
				continue;
			}
			
			if (IsDate(data1))
			{
				
				for (int k = 1; k <=12; k++)
				{
					data1 = data1.replace(dates[k-1], k+"");
				}
				record.date = data1;
				j++;
				continue;
			}
			
			if (data1.equalsIgnoreCase("get") || data1.equalsIgnoreCase("post"))
			{
				get = j;
				record.method = data1;
				//System.out.println("Get = " + j);
				j++;
				continue;
			}
			
			if (j == get+1) //url
			{
				//record.url = data1;
				if (data1.length() == 0)
					return null;
				
				
				int idx = data1.indexOf("?");
				
				if (idx > 0)
				{
					data1 = data1.substring(0, idx);
				}
				
				idx = data1.indexOf(";");
				if (idx > 0)
				{
					data1 = data1.substring(0, idx);
				}
				
				data1 = data1.replace("%20", " ");
				idx = data1.indexOf("%");
				if (idx > 0)
				{
					data1 = data1.substring(0, idx);
				}
					
				if (IsWebSite(data1))
				{	
					idx = Settings.urls.indexOf(data1);
					
					if (idx < 0)
					{
						Settings.urls.add(data1);
						idx = Settings.urls.size();
					}
					else
					{
						idx++;
					}
					
					record.url = "P" + idx;
					record.urlidx = idx -1;
					//System.out.println("url A = " + record.url);
				}
				else
				{
					return null;
				}
				j++;
				continue;
			}
			
			
			if (data.length() == 3 && record.status == 0)
			{			
				try
				{
					record.status = Integer.parseInt(data);
				}
				catch(Exception e)
				{
					
				}
				j++;
				continue;
			}
			
			if (data1.startsWith("http://"))
			{
				record.agent = data;
				j++;
				continue;
			}
			
			if (data1.indexOf("mozilla")>= 0 || data1.indexOf("msie")>=0)
			{
				record.browser = data1;
				j++;
				continue;
			}
			
			j++;
		}
		
		return record;
	}
	
	
	
	public boolean IsIPPattern(String sp)
	{
		//String patternString = "[0-9]?[0-9]?[0-9].[0-9]?[0-9]?[0-9].[0-9]?[0-9]?[0-9].[0-9]?[0-9]?[0-9]";
		String patternString = "[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";
		
		try
		{
			Pattern pattern = Pattern.compile(patternString);
			
			Matcher matcher = pattern.matcher(sp);
			
			return matcher.matches();
		}
		catch(PatternSyntaxException e)
		{
			e.printStackTrace();
		}
		
		return false;		
	}
	
	boolean IsDate(String token)
	{
		String patternstring="[0-9]{1,2}/[A-Z a-z]{1,4}/[0-9]{4}";
		//String patternstring = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
		Pattern pattern = Pattern.compile(patternstring, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(token);
		if (matcher.find())
		{
		
			return true;
		}
		else return false;
	}
	
	
	boolean IsTimeStamp(String token)
	{
		String patternstring =  "[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
		Pattern pattern = Pattern.compile(patternstring, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(token);
		if (matcher.find())
		{
		
			return true;
		}
		else return false;
	}
	
//	public boolean IsTimeStamp(String sp)
//	{
//		String patternString = "[0-9][0-9]:[0-9][0-9]:[0-9][0-9]";
//		
//		try
//		{
//			Pattern pattern = Pattern.compile(patternString);
//			
//			Matcher matcher = pattern.matcher(sp);
//			
//			return matcher.matches();
//		}
//		catch(PatternSyntaxException e)
//		{
//			e.printStackTrace();
//		}
//		
//		return false;
//	}	
//	
//	public boolean IsDate(String data)
//	{
//		String patternString1 = "([0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9])";
//		String patternString2 = "([0-9][0-9]/???/[0-9][0-9][0-9][0-9])";
//		String patternString3 = "([0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9])";
//		String patternString4 = "([0-9]/???/[0-9][0-9][0-9][0-9])";
//		
//		try
//		{
//			Pattern pattern = Pattern.compile(patternString1 + "|" + patternString2 + "|" + patternString3 + "|" + patternString4);
//			
//			Matcher matcher = pattern.matcher(data);
//			
//			return matcher.matches();
//		}
//		catch(PatternSyntaxException e)
//		{
//			e.printStackTrace();
//		}
//		
//		
//		return false;
//	}
	
	public boolean IsWebSite(String website)
	{
		if (website.trim().length() == 0)
			return false;
		
		for (String noise : Settings.nonurl)
		{
			if (website.endsWith(noise))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String []args)
	{
		String line = "184.22.164.54 - - [21/Oct/2012 07:47:33 +0630] \"GET / HTTP/1.1\" 301 549 \"-\" \"Mozilla/4.0 (compatible; MSIE8.0; Windows NT 6.0) .NET CLR 2.0.50727)\"";
		
		WebLog log = new WebLog();
		
		ArrayList<String> words = log.GetWords(line.toLowerCase());
		
		for (String s : words)
		{
			System.out.println(s);
		}
		
		System.out.println("********************");
		
		WebLogRecord record = log.ParseRecord(words);
		
		System.out.println(record);
		
		String ds = "10/10/2012 07:47:33";
		
		Date mydate = new Date(ds);
		System.out.println(mydate.toGMTString());
		System.out.println(mydate.toLocaleString());
		System.out.println(mydate.toString());
	}
}