package research.phs;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public class MyTest implements SortedSet<String> {

	
	
	@Override
	public boolean add(String arg0) {
		// TODO Auto-generated method stub
		return this.add(arg0);		
	}

	@Override
	public boolean addAll(Collection<? extends String> arg0) {
		// TODO Auto-generated method stub
		return this.addAll(arg0);		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return this.contains(arg0);		
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return this.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.isEmpty();		
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return this.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return this.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return this.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return this.toArray(arg0);
	}

	@Override
	public Comparator<? super String> comparator() {
		// TODO Auto-generated method stub
		return this.comparator();
	}

	@Override
	public String first() {
		// TODO Auto-generated method stub
		return this.first();
	}

	@Override
	public SortedSet<String> headSet(String arg0) {
		// TODO Auto-generated method stub
		return this.headSet(arg0);
	}

	@Override
	public String last() {
		// TODO Auto-generated method stub
		return last();
	}

	@Override
	public SortedSet<String> subSet(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return this.subSet(arg0, arg1);
	}

	@Override
	public SortedSet<String> tailSet(String arg0) {
		// TODO Auto-generated method stub
		return this.tailSet(arg0);
	}

	public static void main(String []args)
	{
		MyTest mytest = new MyTest();
		mytest.add("V100");
		mytest.add("H100");
		mytest.add("B100");
		mytest.add("A100");
		
		MyTest mytest2 = new MyTest();
		mytest2.add("V100");
		mytest2.add("H100");
		mytest2.add("B100");
		mytest2.add("A100");
		
		System.out.println(mytest);
		
		System.out.println(mytest.equals(mytest2));
	}
	
}
