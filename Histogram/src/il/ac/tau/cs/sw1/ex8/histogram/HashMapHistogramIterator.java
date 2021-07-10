package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{
	

	private List<T> keys;
	private Map<T, Integer> map;
	private int index;
	
	public HashMapHistogramIterator(Map<T, Integer> map2) 
	{
		map = map2;
		keys = new ArrayList<T>(map.keySet());
		index = 0;
		Collections.sort(keys, new ComparatorHist());
	}
	
	public boolean hasNext() {
		if(index < keys.size()) 
		{
			return true;
		}
		return false;
	}

	
	public T next() 
	{
		T val = keys.get(index++);
		return val;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	public class ComparatorHist implements Comparator<T>{
		
		public int compare(T item1, T item2) 
		{
        	int val1 = map.get(item1);
        	int val2 = map.get(item2);
        	
         if(val1 > val2) 
        	{
        		return -1;
        	}
         else if(val2>val1)
        		return 1;
         return item1.compareTo(item2);
		}
	}
}
