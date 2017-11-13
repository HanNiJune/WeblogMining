package research.weblog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.URI;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import research.phs.ItemSet;
import research.phs.ItemSets;
import research.phs.PerfectHash;
import research.phs.PredictPage;


public class OntoWebLog {

	String BASE_URL = "http://www.semanticweb.org/nuwarhsan/ontologies/2015/9/untitled-ontology-7";
	String filename="C:\\Ontology\\ucsy.owl";
	File file=new File(filename);	
	URI ontofilename=new File(filename).toURI();	
	
	public static ArrayList<PredictPage> predictpages = new ArrayList<PredictPage>();
	
	public void InitSenseDatabase()
	{
		System.setProperty("wordnet.database.dir", "C:/Downloads/wn3.1.dict/dict/");
	}
	
	public void InitOntology()
	{
		try
		{
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			//OWLOntology ontology=null;
			//OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontofilename);
			OWLOntology ontology;
			
			ontology = manager.loadOntologyFromOntologyDocument(file);			 
			
			if(ontology.isEmpty())
			{
				System.out.println("ontology is empty");
			}
			else
			{
				System.out.println("ontology is not empty");
				//System.out.println(ontology.toString());
			}			
						
	        OWLReasonerFactory reasonerFactory = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance();	          
	        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration());	        
			
			//PelletReasoner reasoner1 = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology, new SimpleConfiguration());
					
			OWLDataFactory factory = manager.getOWLDataFactory();
	        PrefixOWLOntologyFormat pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology);
	        pm.setDefaultPrefix(BASE_URL + "#");
	        //OWLClass subClass = factory.getOWLClass(":BathRoomFacilities", pm);
	        
	        //System.out.println("*******************************");
	        
	        Set<OWLClass> classes =ontology.getClassesInSignature();
	        
	        System.out.println("Total  Class Count = " + classes.size());
	        
	        for (OWLClass oc : classes)
	        {
	        	System.out.println("***********");	   
	        	
	        	Set<OWLIndividual> individuals = oc.getIndividuals(ontology);
	        	
	        	System.out.println(oc + " inviduals = " + individuals);
	        	System.out.println("***********");
	        }
	        
	        //System.out.println("*******************************");
	        
		}		
		catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ItemSets FullPatterns = new ItemSets();
	public static ItemSets ConceptPatterns = new ItemSets();
	
	
	
	static ArrayList<String> remainingpatterns = new ArrayList<String>();
	static int MatchPattern(ItemSet iset, ArrayList<String> uptn)
	{
		remainingpatterns.clear();
		
		int matchcount = 0;
		
		for (String u : iset.itemsets)
		{
			//if (iset.itemsets.contains(u))
			if (uptn.contains(u))
			{
				matchcount ++;
			}
			else
			{
				remainingpatterns.add(u);
			}
		}
		
		return matchcount;
	}
	
	public static void GenerateConcept()
	{
		FullPatterns.clear();
		ConceptPatterns.clear();
		for (ItemSets isets : PerfectHash.Lks)
		{
			for (ItemSet is : isets)
			{
				ItemSet fp = new ItemSet();
				ItemSet cp = new ItemSet();
				
				fp.count = is.count;
				cp.count = is.count;
				
				for (String item : is.itemsets)
				{
					int pi = Integer.parseInt(item.substring(1));
					String u = Settings.urls.get(pi-1);
					fp.itemsets.add(u);
					
					String c = Settings.GetConceptFromLink(u);
					//System.out.
					
					if (!cp.itemsets.contains(c))
						cp.itemsets.add(c);
				}
				
				FullPatterns.add(fp);
				
				int idx = ConceptPatterns.indexOf(cp);
				if (idx >= 0)
				{
					ItemSet iset = ConceptPatterns.get(idx);
					iset.count += cp.count;
					ConceptPatterns.set(idx, iset);
				}
				else
				{
					ConceptPatterns.add(cp);
				}			
				
			}
		}
	}
	
	public static void main(String []args)
	{
		OntoWebLog owl = new OntoWebLog();
		owl.InitOntology();
	}
	
	public static boolean bPredict = false;
	public static ArrayList<String> curpattern = new ArrayList<String>();
	
	public static ArrayList<PredictPage> Predict()
	{
		ArrayList<PredictPage> pages = new ArrayList<PredictPage>();
		if (curpattern.size() == 0)
			return pages;		
		
		int thresh = 0;
		
		for (ItemSet is : FullPatterns)
		{
			int matchcount = MatchPattern(is, curpattern);
			
			thresh = is.itemsets.size() / 3;
			
			if (matchcount <= thresh)
				continue;
			
			for (String r : remainingpatterns)
			{
				PredictPage pp = new PredictPage(r);
				int idx = pages.indexOf(pp);
				
				if (idx < 0)
				{
					pp.count = is.count;
					pp.matchcount = matchcount;
					pages.add(pp);
				}
				else
				{
					pp = pages.get(idx);
					pp.count += is.count;
					if (pp.matchcount < matchcount)
						pp.matchcount = matchcount;
					pages.set(idx, pp);
				}
			}
		}
		
		PredictPage []temppages = new PredictPage[pages.size()];
		
		temppages = pages.toArray(temppages);
		
		Arrays.sort(temppages);
		
		ArrayList<PredictPage> tp = new ArrayList<PredictPage>();
		for (PredictPage pp : temppages)
		{
			tp.add(pp);
		}
		
		return tp;
	}
	
	public ArrayList<ItemSet> Predict(ItemSet is)
	{
		ArrayList<ItemSet> results = new ArrayList<ItemSet>();
		
		return results;
	}
	
}
