package research.phs;

import java.util.SortedSet;
import java.util.TreeSet;

public class SortedListTest {
	
	public static void main(String []args)
	{
		SortedSet<String> treeset = new TreeSet<String>();
		
		treeset.add("zfjklds");
		treeset.add("yflskd");
		treeset.add("rie90");
		treeset.add("a459");
		treeset.add("a9");
		
		SortedSet<String> treeset2 = new TreeSet<String>();
		
		treeset2.add("zfjklds");
		treeset2.add("yflskd");
		treeset2.add("rie90");
		treeset2.add("a459");
		treeset2.add("a9");
		
		System.out.println(treeset);
		
		System.out.println(treeset.equals(treeset2));
	}

}
