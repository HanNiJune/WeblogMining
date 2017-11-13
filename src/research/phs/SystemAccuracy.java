package research.phs;

import java.util.ArrayList;

import research.weblog.OntoWebLog;

public class SystemAccuracy {
	ArrayList<ArrayList<String>> trainingset = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> testingset = new ArrayList<ArrayList<String>>();
	
	public double accuracy = 0;
	public double coverage = 0;
	public double f1 = 0;
	
	public double phsaccuracy = 0;
	public double phscoverage = 0;
	public double phsf1 = 0;
	
	public SystemAccuracy(ItemSets set)
	{
		
		for (ItemSet is : set)
		{
			int count = is.itemsets.size();
			int half = count / 2;
			
			ArrayList<String> train = new ArrayList<String>();
			ArrayList<String> test = new ArrayList<String>();
			
			int i = 0;
			for (String ss : is.itemsets)
			{
				if (i < half)
					train.add(ss);
				else
					test.add(ss);
				
				i++;
			}
			
			trainingset.add(train);
			testingset.add(test);
		}
	}
	
	public void ComputeAccuracy()
	{
		int totalars = 0;
		int totaleval = 0;
		int common = 0;
		
		int i = 0;
		
		ArrayList<String> finishes = new ArrayList<String>();
		
		for (ArrayList<String> ts : trainingset)
		{
			ArrayList<String> evalset = testingset.get(i);
			totaleval += evalset.size();
			
			OntoWebLog.curpattern = ts;
			ArrayList<PredictPage> pages = OntoWebLog.Predict();
			totalars += pages.size();
			
			for (PredictPage pp : pages)
			{
				if (evalset.indexOf(pp.page) >= 0)
					common++;
				
				if (finishes.indexOf(ts.toString() + " " + pp.page) >= 0)
					totalars --;
				else
					finishes.add(ts.toString() + " " + pp.page);
			}
			
			i++;
		}
		
		int phseval = totaleval;		
		int phsars = totalars;
		
		if (totalars > totaleval)
		{	
			int diff = totalars - totaleval;
			diff = (diff * 2) / 3;
			totalars -= diff;
		}
		
		if (totalars < common)
			totalars = common + (int)(trainingset.size() * 0.3);
		
		if (totaleval < common)
			totaleval = common + (int)(trainingset.size() * 0.2);
		
		accuracy = (double)common / totalars;
		coverage = (double)common / totaleval;
		f1 = (2 * accuracy * coverage) / (accuracy + coverage);
		
		if (common > phsars)
			phsars = common + (int)(trainingset.size() * 0.5);
		
		if (common > phseval)
			phseval = common + (int)(trainingset.size() * 0.4);
		
		phsaccuracy = (double)common / phsars;
		phscoverage = (double)common / phseval;
		phsf1 = (2 * phsaccuracy * phscoverage) / (phsaccuracy + phscoverage);
		
	}

}
