package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Comparator;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

class RankedWordComparator implements Comparator<RankedWord>{
    
	rankType type;
	
	public RankedWordComparator(rankType type) {
		this.type = type;
	}
	
	@Override
	public int compare(RankedWord r1, RankedWord r2) {
		
		if(r1.getRankByType(type) > r2.getRankByType(type)) {
			return 1;
		}
		else 
			if(r1.getRankByType(type) < r2.getRankByType(type)) {
			return -1;
		}
		return 0;
		
	}		
}
