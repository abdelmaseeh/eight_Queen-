import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class eightQueenProcessor {
	// board size
	private int N = 8 ;
	
	// initialize random state 
	String randomState()
	{
		StringBuilder st = new StringBuilder() ;
		Random rand = new Random() ;
		for (int i = 0 ; i < N ; i++)
		{
			st.append(String.valueOf(rand.nextInt(N))) ;
		}
		return st.toString();
	}
	

	String getKBestNeighboor(String state, int k, Set<String> hs)
	{
		String best = state ; 
		int bestScore = getStateValue(state) ;
		
		PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>() {

			public int compare(String arg0, String arg1) {
				// TODO Auto-generated method stub
				if (getStateValue(arg0) < getStateValue(arg1)) return  1 ;
				else if (getStateValue(arg0) > getStateValue(arg1))return -1;
				else return 0 ; 
			}
		
		} );
		
		for (int i = 0 ; i < N ; i++)
		{
			String newState = state ;
			int currentPos = state.charAt(i) - '0' ; 
			int newScore = 0 ; 
			
			//before 
			for (int j = 0 ; j < currentPos ; j++)
			{
				newState = newState.substring(0, i) + String.valueOf(j) + newState.substring(i+1); 
				newScore = getStateValue(newState) ; 
				
				if(pq.peek() == null || pq.size() < k)
				{
					if (!hs.contains(newState))
						pq.add(newState) ;
				}
				else if (getStateValue(pq.peek()) >= newScore)
				{
					if (!hs.contains(newState)) {
						pq.poll() ;
						pq.add(newState);
					}
					
				}
			}
			//after 
			for (int j = currentPos+1 ; j < N ; j++)
			{
				newState = newState.substring(0, i) + String.valueOf(j) + newState.substring(i+1); 
				newScore = getStateValue(newState) ; 
				if(pq.peek() == null || pq.size() < k)
				{
					if (!hs.contains(newState))
						pq.add(newState) ;
				}
				else if (getStateValue(pq.peek()) >= newScore)
				{
					if (!hs.contains(newState)) {
						pq.poll() ;
						pq.add(newState);
					}
				}
			}
		}
		
		for (String str : pq)
		{
			int newScore = getStateValue(str) ; 
			if (bestScore >= newScore)
			{
				best = str ; 
				bestScore = newScore ; 
			}
		}
		
		return best;

	}
	
	// number of attacking queen 
	int getStateValue(String state)
	{
		int numOfAttacking = 0 ; 
		for (int i = 0 ; i < N ; i++)
		{
			for (int j = i+1 ; j < N ; j++)
			{
				// vertical 
				if (state.charAt(i) == state.charAt(j))
				{
					numOfAttacking++ ;
				}
				// diagonal
				else if (Math.abs(j - i) == Math.abs(Integer.valueOf(state.charAt(i)) - Integer.valueOf(state.charAt(j))))
				{
					numOfAttacking++ ;
				}
			}
		}
		
		return numOfAttacking;
	}
	
	String hillClimbing()
	{
		// hash set for uniques states 
		Set<String> hs = new HashSet<String>() ;
		String state = randomState() ; 
		String best = null ; 
		int oldScore = 0 ; 
		int newScore = getStateValue(state);  
		
		hs.add(state);
		
		do 
		{
			hs.add(state);
			oldScore = newScore ; 
			best = state ; 
			// get best of only one neighbor 
			state = getKBestNeighboor(state, 1, hs);
			newScore = getStateValue(state);
		}while (!hs.contains(state) && newScore <= oldScore) ;
		
		return best ;
	}
	
	String hillClimbingMultipleStart(int k)
	{
		String best = null ;
		int score = 56 ; 
		for (int i = 0 ; i < k ; i++)
		{
			String newState = hillClimbing() ;
			int newScore = getStateValue(newState);
			if (score > newScore)
			{
				best = newState ;
				score = newScore ; 
			}
		}
		return best ; 
	}
	
	String hillClimbingKLocalBeams(int k)
	{
		Set<String> hs = new HashSet<String>() ;
		String state = randomState() ; 
		String best = null ; 
		int oldScore = 0 ; 
		int newScore = getStateValue(state);  
		
		hs.add(state);
		
		do 
		{
			hs.add(state);
			oldScore = newScore ; 
			best = state ; 
			state = getKBestNeighboor(state, k, hs);
			newScore = getStateValue(state);
		}while (!hs.contains(state) && newScore <= oldScore) ;
		
		return best ;
	}
	
}
