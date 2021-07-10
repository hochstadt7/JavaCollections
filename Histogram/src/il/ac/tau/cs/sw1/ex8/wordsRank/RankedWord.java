package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Collections;
import java.util.Map;



/*********************************************************************************
 *   This is a complete implementation.                                          *
 *   you can add more code or alter the existing code, but                       *
 *   keep in mind that this class is designed for the purpose of this exercise   *
 *   you you should be able to use it just as it is                              *
 *********************************************************************************/

class RankedWord{
	
	public static enum rankType {average, min, max};

	
	private String word;
	private Map<String, Integer> ranksFile;
	private int average;
	private int min;
	private int max;
	
	public RankedWord(String word, Map<String, Integer> ranksForFiles){
		this.word = word;
		ranksFile = ranksForFiles;
		min = Collections.min(ranksFile.values());
		max = Collections.max(ranksFile.values());
		int sum = 0;
		for (Integer rank : ranksFile.values()){
			sum += rank;
		}
		average = (int)Math.round(((double)sum)/ranksFile.size());
	}

	public String getWord() {
		return word;
	}

	public Map<String, Integer> getRanksForFile() {
		return ranksFile;
	}

	public int getRankByType(rankType rType){
		
		switch(rType){
		case average:
			return average;
		case min:
			return min;
		default: 
			return max;
		}
	}

	@Override
	public String toString() {
		return "RankedWord [word=" + word + ", ranksForFile=" + ranksFile + ", average=" + average + ", min="
				+ min + ", max=" + max + "]";
	}
}
