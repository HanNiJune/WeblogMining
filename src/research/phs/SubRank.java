package research.phs;

public class SubRank implements Comparable<SubRank> 
{
	public String fname = "";
	public double std = 0;
	public double weight = 0;
	
	@Override
	public int compareTo(SubRank o) {
		// TODO Auto-generated method stub
		
		if (std > o.std)
			return 1;
		else if (std < o.std)
			return -1;
		
		return 0;
	}
	
}
