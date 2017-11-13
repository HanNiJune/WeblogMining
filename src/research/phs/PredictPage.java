package research.phs;

public class PredictPage implements Comparable<PredictPage> 
{
	
	public String page = "";
	public int count = 0;
	public int matchcount = 0;
	
	public PredictPage()
	{
		
	}
	
	public PredictPage(String p)
	{
		page = p;
	}
	
	public boolean equals(Object o)
	{
		PredictPage pp = (PredictPage) o;
		return (pp.page.equals(page));				
	}
	
	@Override
	public int compareTo(PredictPage o) {
		// TODO Auto-generated method stub
		
		if (matchcount < o.matchcount)
			return -1;
		
		if (matchcount > o.matchcount)
			return 1;
		
		if (count < o.count)
			return -1;
		else if (count > o.count)
			return 1;
		
		return 0;
	}

}
