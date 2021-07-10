package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	public Map<T,Integer> map=new HashMap<T,Integer>();
	
	@Override
	public Iterator<T> iterator() {

		return new HashMapHistogramIterator<T>(map);
	}

	@Override
	public void addItem(T item) {

		if (this.map.containsKey(item)) {
			int count = this.map.get(item);
			map.put(item, count+1);
		}
		else 
			map.put(item, 1);
		
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {

		if (map.containsKey(item)) {
			int count = map.get(item);
			if(count > 1) {
				map.put(item, count-1);
			}
			else
				map.remove(item);
		}
		else
			throw new IllegalItemException();
			
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {

		if (k>0)
		{
			if(map.containsKey(item))
			{
			int count=map.get(item);
			map.put(item, count+k);
			}
			else
				map.put(item, k);
		}
		else
			throw new IllegalKValueException(k);
			
	}
	
	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {

		if (map.containsKey(item) && k>0) {
			int count = map.get(item);
			if(count==k)
				map.remove(item);
			else if(count > k) {
				map.put(item, count-k);
			}
			else {
				throw new IllegalKValueException(k);
			}
		}
		else
		{
			if(k<=0)
				throw new IllegalKValueException(k);
			else
				throw new IllegalItemException();
				
		}
		
	}

	@Override
	public int getCountForItem(T item) {
		
		if (!map.containsKey(item)) {
			return 0;
		}
		else {
			return map.get(item);
		}
	}

	@Override
	public void addAll(Collection<T> items) {

		Iterator<T> iterator = items.iterator();
		while (iterator.hasNext()) {
	        addItem(iterator.next());
	        }
		
	}

	@Override
	public void clear() {
		map.clear();
		
	}

	@Override
	public Set<T> getItemsSet() {

		return map.keySet();
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {

		T item;
		Iterator<T> iterator = anotherHistogram.getItemsSet().iterator();
		while (iterator.hasNext()) {
		    item=iterator.next();
			for(int i=0; i<anotherHistogram.getCountForItem(item); i++)
				addItem(item);
	        }
	}
	
	

}