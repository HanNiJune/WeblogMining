package research.phs;
import java.util.ArrayList;

import research.weblog.WebLogTimeSession;

public class TransactionDB
{
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	public TransactionDB()
	{
		
	}
	
	public TransactionDB(ArrayList<WebLogTimeSession> usersessions)
	{
		for (WebLogTimeSession ses:usersessions)
		{
			Transaction t = new Transaction(ses);
			transactions.add(t);
		}
	}
}