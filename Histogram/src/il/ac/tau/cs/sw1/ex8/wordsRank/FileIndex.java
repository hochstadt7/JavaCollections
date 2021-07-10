package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 30;
	public Map<String ,IHistogram<String>> filesMap;
	public Map<String ,IHistogram<String>> wordsMap;
	public Set<String> wordsSet;
	
	

	public FileIndex() {
		
		wordsMap = new HashMap<String, IHistogram<String>>();
		filesMap = new HashMap<String, IHistogram<String>>();
		wordsSet = new HashSet<String>();
	}

	/*
	 * @pre: the directory is not empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		

		File folder = new File(folderPath);
		int place;
  		String nextWord;
  		Iterator<?> wordsIterate;
  		IHistogram<String> wordsHist;
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) 
		{
			
			if (file.isFile()) {
				IHistogram<String> wordsHist1 = new HashMapHistogram<>();
				try {
					List<String> words = FileUtils.readAllTokens(file);
					for(String word: words)
					{
						wordsHist1.addItem(word);
						wordsSet.add(word);
					}
					
				} 
				catch (IOException e)
				{
					
				}
				finally
				{
				filesMap.put(file.getName(), wordsHist1);
				}
			}
		}
		Set<String> files=  new HashSet<String>(filesMap.keySet());
  		
  		for(String file: files) 
  		{
  			 wordsHist= new HashMapHistogram<>();
  		    wordsIterate = filesMap.get(file).iterator();
  		  place = 1;
  			while(wordsIterate.hasNext()) 
  			{
  				nextWord = (String) wordsIterate.next();
  				for(int i=0 ; i<place; i++) 
  				{
  	  				wordsHist.addItem(nextWord);
  				}
	  				place++;
  			}
  			wordsMap.put(file, wordsHist);
  		}
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{
		if (filesMap.containsKey(filename))
		{
			return filesMap.get(filename).getCountForItem(word.toLowerCase());
		}
			throw new FileIndexException("file is not found");
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		
		int countWord;
		if (wordsMap.containsKey(filename))
		{
		    countWord = wordsMap.get(filename).getCountForItem(word);
			if(countWord == 0) 
			{
				 return UNRANKED_CONST+wordsMap.get(filename).getItemsSet().size();
			}
				return countWord;
		}
			throw new FileIndexException("file is not found");
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		Map<String,Integer> map = new HashMap<String, Integer>();
		int rank;
		for(String file: wordsMap.keySet()) {
			try 
			{
				rank = getRankForWordInFile(file, word);
				
				map.put(file, rank);
			} 
			catch (FileIndexException e)
			{
				
			}
		}
		RankedWord rankWord = new RankedWord(word, map);
		return rankWord.getRankByType(rankType.average);
	}
	public int count;
	public List<String> check(int k, String type){
		List<RankedWord> list = new ArrayList<RankedWord>();
		List<String> final1 = new ArrayList<String>();
		int rank;
		for(String word: wordsSet) 
		{
			Map<String, Integer> map = new HashMap<String, Integer>();
			for(String file: wordsMap.keySet()) 
			{
				try 
				{
					rank = getRankForWordInFile(file, word);
					map.put(file, rank);
				} 
				catch (FileIndexException e)
				{
					
				}
			}
			RankedWord nextWord = new RankedWord(word, map);
			switch(type)
			{
			case("minimum"):
			{
				if (nextWord.getRankByType(rankType.min) < k) 
				   {
					list.add(nextWord);
					count++;
				}
				break;
			}
			case("maximum"):
			{
				if (nextWord.getRankByType(rankType.max) < k) 
				   {
					list.add(nextWord);
					count++;
				}
				break;
			}
			case("average"):
			{
				if (nextWord.getRankByType(rankType.average) < k) 
				   {
					list.add(nextWord);
					count++;
				}
				break;
			}
			
			}
			
			
		}
		
		Collections.sort(list, new RankedWordComparator(rankType.min));
		for(RankedWord word: list) 
		{
			final1.add(word.getWord());
		}
		return final1;
	}
	
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		return check(k, "minimum");
	}
	
	public List<String> getWordsWithAverageRankLowerThenK(int k){
		return check(k, "average");
	}
	
	
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		return check(k, "maximum");
	}

}
