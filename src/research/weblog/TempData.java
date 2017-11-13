package research.weblog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
*/

import javax.swing.ProgressMonitorInputStream;

public class TempData {
	public static String filename = "";
	public static String filedata = "";
	public static int curstate = 0;
	
	public static WebLog log = new WebLog();
	
	public static ArrayList<WebLogTimeSession> timesessions = new ArrayList<WebLogTimeSession>();
		
	//public static void ReadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	public static void ReadFile()
	{
		final StringBuilder sb = new StringBuilder();
		
		log = new WebLog();
		
		try
		{
			File f = new File(filename);
			
			FileInputStream fileIn = new FileInputStream(f);
			ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(null, "Reading " + f.getName(), fileIn);
			final Scanner in = new Scanner(progressIn);
			//taFile.setText("");
			
			Runnable readRunnable = new Runnable()
			{
				public void run()
				{
					while (in.hasNextLine())
					{
						String line = in.nextLine();						
						sb.append(line + "<br>\r\n");
						//taFile.append(line + "\r\n");
						
						ArrayList<String> words = log.GetWords(line.toLowerCase());
				
						WebLogRecord record = log.ParseRecord(words);
						
						if (record == null || record.url.length() == 0)
							continue;
							
						if (record.status < 200 || record.status > 299)
							continue;
						
						log.records.add(record);
						
						int idx = log.sessionips.indexOf(record.ipaddress);
						
						if (idx >= 0)
						{
							WebLogUserSession session = log.usersessions.get(idx);
							session.AddRecord(record);
							log.usersessions.set(idx, session);
						}
						else
						{
							WebLogUserSession session = new WebLogUserSession(record.ipaddress);
							session.AddRecord(record);
							log.sessionips.add(record.ipaddress);
							log.usersessions.add(session);
							
						}
					}
					
					in.close();
					curstate = 1;
					System.out.println("Reading Finish");
					filedata = sb.toString();
					
				}
			};
			
			Thread readThread = new Thread(readRunnable);
			readThread.start();
		}
		catch(Exception e)
		{
			
		}
		
		filedata = sb.toString();
	}
	
}
