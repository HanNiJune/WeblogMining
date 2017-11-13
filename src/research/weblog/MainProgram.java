package research.weblog;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

import javax.swing.tree.*;
import javax.swing.border.*;

import research.phs.PerfectHash;
import research.phs.Transaction;

public class MainProgram extends JFrame
{	
	JTextField jtFileName = new JTextField(30);
	JTextArea taFile = new JTextArea();
	
	String inputFileName = "";
	
	JMenu mnuLog = new JMenu("Web Log Mining");
	JMenuItem mnuOpen = new JMenuItem("Open Log File", new ImageIcon("Images\\OpenImage.JPG"));
	JMenuItem mnuPreprocessing = new JMenuItem("Cleaning", new ImageIcon("Images\\check.gif"));
	JMenuItem mnuUserIdentification = new JMenuItem("User Identification", new ImageIcon("Images\\discuss.gif"));
	JMenuItem mnuSessionIdentification = new JMenuItem("Session Identification", new ImageIcon("Images\\msg_joke.gif"));	
	
	JMenu mnuFile = new JMenu("File");
	JMenuItem mnuAbout = new JMenuItem("About", new ImageIcon("Images\\AddTo_Simpy.png"));
	JMenuItem mnuExit = new JMenuItem("Exit", new ImageIcon("Images\\AddTo_Blink.png"));
	
	
	JButton btnOpen = new JButton("Open");
	JButton btnPreprocessing = new JButton("Cleaning");
	JButton btnUserIdentification = new JButton("User Identification");
	JButton btnSessionIdentification = new JButton("Session Identification");
	JButton btnTransactions = new JButton("Transaction Preparation");
	
	JButton btnAbout = new JButton("About");
	JButton btnExit = new JButton("Exit");
	
	//JButton btnAbout = new JButton(new ImageIcon("Images\\AddTo_Simpy.png"));
	//JButton btnExit = new JButton(new ImageIcon("Images\\AddTo_Blink.png"));
	
	void BuildMenu()
	{
		JMenuBar mb = new JMenuBar();
		
		mnuLog.add(mnuOpen);
		mnuLog.add(mnuPreprocessing);
		mnuLog.add(mnuUserIdentification);
		mnuLog.add(mnuSessionIdentification);
		
		mnuLog.addSeparator();		
		
		mnuFile.add(mnuAbout);
		mnuFile.addSeparator();
		mnuFile.add(mnuExit);
		
		mb.add(mnuLog);
		mb.add(mnuFile);
		
		setJMenuBar(mb);
		
		AddActions();
	}
	
	void InitToolbar()
	{
		JToolBar tb = new JToolBar();
		tb.add(btnOpen);
		tb.add(btnPreprocessing);
		tb.add(btnUserIdentification);
		tb.add(btnSessionIdentification);
		tb.add(btnTransactions);
		
		tb.addSeparator();
		
		
		tb.addSeparator();		
		
		tb.addSeparator();
		
		tb.add(btnAbout);
		tb.add(btnExit);
		
		add(tb, BorderLayout.NORTH);
	}
	
	void AddActions()
	{
		mnuOpen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				OpenLogFile();
			}
		});
		
		mnuPreprocessing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Cleaning();
			}
		});
		
		mnuUserIdentification.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UserIdentification();
			}
		});
		
		mnuSessionIdentification.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SessionIdentification();
			}
		});		
		
		
		
		mnuAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tabPane.setSelectedIndex(7);
			}
		});
		
		mnuExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		////////////////
		btnOpen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				OpenLogFile();
			}
		});
		
		btnPreprocessing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Cleaning();
			}
		});
		
		btnUserIdentification.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UserIdentification();
			}
		});
		
		btnSessionIdentification.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SessionIdentification();
			}
		});		
		
		
		btnAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tabPane.setSelectedIndex(7);
			}
		});
		
		btnTransactions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				FillTransactions();
				//OpenLogFile();
			}
		});
		
		btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
	}
	
	JTable tblTransactions;
	Vector r5 = new Vector();
	Vector c5 = new Vector();
	
	void BuildTransactions()
	{
		c5.add("TID");
		c5.add("Session Items");
		tblTransactions = new JTable(r5, c5);
	}
	
	void FillTransactions()
	{
		PerfectHash.InitDB(TempData.timesessions);
		TempData.curstate = 4;
		int i = 0;
		
		r5.removeAllElements();
		for (Transaction t : PerfectHash.db.transactions)
		{
			i++;
			Vector r = new Vector();
			r.add("T" + i);
			r.add(t.toString());
			
			r5.addElement(r);
		}
		
		tblTransactions.updateUI();
	}
	
	JPanel TransactionPanel()
	{
		JPanel p = new JPanel(new BorderLayout());
		BuildTransactions();
		
		JScrollPane jsp = new JScrollPane(tblTransactions) ;
		p.add(jsp, BorderLayout.CENTER);		
		
		
		return p;
	}
	
	
	JPanel AboutPanel()
	{
		JPanel pTitle = new JPanel(new BorderLayout());
      	
	  	JLabel txtTitle=new JLabel("<html></html>");	
      
      	JLabel lblScheduler = new JLabel("<html></html>");
      
      	pTitle.add(lblScheduler,BorderLayout.CENTER);
      	
      	pTitle.add(txtTitle, BorderLayout.NORTH);
		
		return pTitle;
	}
	
	JPanel LogPanel()
	{
		JButton btnLoad = new JButton("Load");
		
		JPanel up = new JPanel();
		up.add(new JLabel("      Please Select Log File : ", JLabel.RIGHT));
		up.add(jtFileName, BorderLayout.CENTER);
		up.add(btnLoad);
		
		btnLoad.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				OpenLogFile();
			}
		});
		
		JPanel upperpanel = new JPanel();
		upperpanel.add(up);
		
		JScrollPane jsp = new JScrollPane(taFile);
		
		JPanel mp = new JPanel(new BorderLayout());
		mp.add(upperpanel, BorderLayout.NORTH);
		mp.add(jsp, BorderLayout.CENTER);
		
		return mp;
	}	
	
	JTable tblPreprocessing;
	Vector r1 = new Vector();
	Vector c1 = new Vector();
	
	JTable tblSubstitution;
	Vector r2 = new Vector();
	Vector c2 = new Vector();
	
	void PreparePreprocessingTable()
	{
		c1.add("No.");
		c1.add("IP Address");
		c1.add("Date");
		c1.add("Time");
		c1.add("URL");
		c1.add("Method");
		c1.add("Status");
		
		tblPreprocessing = new JTable(r1, c1);
		
		c2.add("No.");
		c2.add("Page");
		c2.add("Page Type");
		
		tblSubstitution = new JTable(r2, c2);
	}
	
	void FillPreprocessingTable()
	{
		r1.removeAllElements();
		
		int i = 0;
		
		for (WebLogRecord record : log.records)
		{
			i++;
			Vector v = new Vector();
			v.add(i+"");
			v.add(record.ipaddress);
			v.add(record.date);
			v.add(record.time);
			v.add(record.url);
			v.add(record.method);
			v.add(record.status + "");
			
			r1.addElement(v);
		}
		
		tblPreprocessing.updateUI();
		
		c2.removeAllElements();
		
		i = 0;
		
		for (String url : Settings.urls)
		{
			i++;
			Vector v = new Vector();
			
			v.add(i+"");
			v.add(url);
			v.add("");
			
			r2.addElement(v);
		}
		
		tblSubstitution.updateUI();
		
//		for (LogRecord record : Settings.cleaninglogs)
//		{
//			i++;
//			Vector v = new Vector();
//			v.add(i+"");
//			v.add(record.ipaddress);
//			v.add(record.date);
//			v.add(record.time);
//			v.add(record.url);
//			v.add(record.method);
//			v.add(record.status + "");
//			r1.addElement(v);
//		}
//		
//		tblPreprocessing.updateUI();
//		
//		i = 0;
//		
//		r2.removeAllElements();
//		for (String p : Settings.replcements.keySet())
//		{
//			i ++;
//			Vector v = new Vector();
//			v.add(i+"");
//			v.add(p);
//			v.add(Settings.replcements.get(p));
//			
//			r2.addElement(v);			
//		}
		
		tblSubstitution.updateUI();
	}
	
	JPanel PreprocessingPanel()
	{
		PreparePreprocessingTable();
		
		JPanel p = new JPanel(new BorderLayout());
		
		JScrollPane jsp1 = new JScrollPane(tblPreprocessing);
		JScrollPane jsp2 = new JScrollPane(tblSubstitution);		
		
		p.add(jsp1, BorderLayout.CENTER);
		p.add(jsp2, BorderLayout.EAST);
		
		return p;
	}
	
	JTable tblUserSessions;
	Vector r3 = new Vector();
	Vector c3 = new Vector();
	
	void BuildUserSessionTable()
	{
		c3.add("Session No.");
		c3.add("IP");
		c3.add("url");
		c3.add("time");
		
		tblUserSessions = new JTable(r3, c3);
	}
	
	void FillUserSessionTable()
	{
		r3.removeAllElements();
		
		int i = 0;
		
		for (WebLogUserSession session : log.usersessions)
		{
			i++;
			
			Vector v = new Vector();
			
			v.add(i+"");
			v.add(session.userip);
			v.add("");
			v.add("");
			
			r3.addElement(v);
			
			for (WebLogRecord record : session.records)
			{
				Vector v1 = new Vector();
				v1.add("");
				v1.add("");
				v1.add(record.url);
				v1.add(record.time);
				r3.addElement(v1);
			}
		}		
		
		tblUserSessions.updateUI();
	}
	
	JPanel UserIdentificationPanel()
	{
		JPanel p = new JPanel(new BorderLayout());
		BuildUserSessionTable();
		
		JScrollPane jsp = new JScrollPane(tblUserSessions);
		
		p.add(jsp, BorderLayout.CENTER);
		
		return p;
	}
	
	void UserIdentification()
	{
		tabPane.setSelectedIndex(2);
		
		//for (Web)
//		
//		for (LogRecord rec : Settings.cleaninglogs)
//		{
//			if (Settings.usersessions.containsKey(rec.ipaddress))
//			{
//				UserSession session = Settings.usersessions.get(rec.ipaddress);
//				session.AddDetail(rec.url, rec.time);
//				
//				Settings.usersessions.remove(rec.ipaddress);
//				Settings.usersessions.put(rec.ipaddress, session);
//			}
//			else
//			{
//				UserSession session = new UserSession();
//				session.ip = rec.ipaddress;
//				session.AddDetail(rec.url, rec.time);
//				Settings.usersessions.put(rec.ipaddress, session);
//			}
//		}
		
		FillUserSessionTable();
	}
	
	JTable tblSessions;
	Vector r4 = new Vector();
	Vector c4 = new Vector();
	
	void BuildSessionTable()
	{
		c4.add("No.");
		c4.add("Session");
		
		tblSessions = new JTable(r4, c4);
	}
	
	void FillSessionTable()
	{
		r4.removeAllElements();
		
		int i = 0;
		
		for (WebLogUserSession uses : log.usersessions)
		{
			Vector vm = new Vector();
			vm.add(uses.userip);
			vm.add(uses.times.get(0));
			
			r4.addElement(vm);
			
			for (WebLogTimeSession tses : uses.timesessions)
			{
				i++;
				Vector v = new Vector();
				v.add(i+"");
				v.add(tses.ToCodeString());
				r4.addElement(v);
			}
		}
		
		tblSessions.updateUI();

//		for (UserSession session : Settings.usersessions.values())
//		{
//			i++;
//			
//			Vector v = new Vector();
//			v.add(i+"");
//			v.add(session.ToIPString());
//			
//			r4.addElement(v);
//		}
		
		tblSessions.updateUI();
	}
	
	JPanel SessionPanel()
	{
		BuildSessionTable();
		JPanel p = new JPanel(new BorderLayout());
		
		JScrollPane jsp = new JScrollPane(tblSessions);
		
		p.add(jsp, BorderLayout.CENTER);
		
		return p;
	}
	
	void SessionIdentification()
	{
		tabPane.setSelectedIndex(3);
		FillSessionTable();
	}
	
	void OpenLogFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		
		
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
		{
			public boolean accept(File f)
			{
				String fname = f.getName().toLowerCase();
				return true;
				//return fname.endsWith(".txt") || f.isDirectory();
			}
			public String getDescription()
			{
				return "Text Files";
			}
		});
		
		if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		
		
		Clear();
		try
		{
			final File f = chooser.getSelectedFile();
			inputFileName = f.getAbsolutePath();
			
			log = new WebLog(inputFileName);
		
			FileInputStream fileIn = new FileInputStream(f);
			ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(this, "Reading " + f.getName(), fileIn);
			final Scanner in = new Scanner(progressIn);
			
			jtFileName.setText(inputFileName);
			
			taFile.setText("");
			//taFile.setText("");
			
			Runnable readRunnable = new Runnable()
			{
				public void run()
				{
					while (in.hasNextLine())
					{
						String line = in.nextLine();
						taFile.append(line + "\r\n");
						//taFile.append(line + "\r\n");
						
						ArrayList<String> words = log.GetWords(line.toLowerCase());
				
						WebLogRecord record = log.ParseRecord(words);
						
						if (record == null || record.url.length() == 0)
							continue;
							
						if (record.status < 200 && record.status > 299)
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
				}
			};
			
			Thread readThread = new Thread(readRunnable);
			readThread.start();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	
	JTextArea taSimilarity = new JTextArea();
	
	JLabel lblSim = new JLabel();
	
	JTable tblURL;
	Vector uRow = new Vector();
	Vector uCol = new Vector();
	
	void BuildURLTabel()
	{
		uCol.add("No.");
		uCol.add("URL");
		uCol.add("Type");
		tblURL = new JTable(uRow, uCol);
	}
	
	void FillURLTable()
	{
		int i = 0;
		//for (String )
	}
	
	
	JPanel URLPanel()
	{
		BuildURLTabel();
		JScrollPane jsp = new JScrollPane(tblURL);
		JPanel p = new JPanel(new BorderLayout());
		
		p.add(jsp);
		
		return p;
	}
	
	WebLog log = new WebLog();
	
	void Cleaning()
	{		
		//log = new WebLog(jtFileName.getText())	;
		tabPane.setSelectedIndex(1);		
		//Settings.urls.clear();		
		
		FillPreprocessingTable();		
		
		System.out.println("record size = " + log.records.size());
		
		System.out.println("url count = " + Settings.urls.size());
		
		//Settings.SaveURL();
	}	
	
	
	
	JTabbedPane tabPane = new JTabbedPane();
	int w = 0;
	int h = 0;
	
	void InitComponents()
	{
		Dimension d = WindowTools.GetFullDimension();
		w = d.width;
		h = d.height;
		
		setLayout(new BorderLayout());
		
		BuildMenu();
		
		InitToolbar();
		
		tabPane.addTab("Web Log Files", LogPanel());
		tabPane.addTab("Cleaning", PreprocessingPanel());
		tabPane.addTab("User Identification", UserIdentificationPanel());
		tabPane.addTab("Session Identification", SessionPanel());
		tabPane.addTab("Transactions", TransactionPanel());
		
		add(tabPane, BorderLayout.CENTER);
		
		setTitle("Web usage mining");
		
		
		setSize(d);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	public MainProgram()
	{
		InitComponents();
	}
	
	
	public static void main(String args[])
	{
		new MainProgram();
		
		//t i = (int)(Math.random() * 5);
		//System.out.println(i);
	}
	
	void Clear()
	{
		//Settings.urls.clear();		
		
		r1.removeAllElements();
		tblPreprocessing.updateUI();
		
		r2.removeAllElements();
		tblSubstitution.updateUI();
		
		r3.removeAllElements();
		tblUserSessions.updateUI();
		
		r4.removeAllElements();
		tblSessions.updateUI();			
		
		
	}
	
	JPanel VectorPanel()
	{
		JPanel panel = new JPanel();
		
		return panel;
	}
	
	void BuildVector()
	{
		
	}
}
