import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		eightQueenProcessor proc = new eightQueenProcessor() ;
		int counter = 0 ; 
		for (int i = 1 ; i <= 100 ; i++)
		{
			System.out.print(i+"\t");
			String state = proc.hillClimbingKLocalBeams(10) ;
			int value = proc.getStateValue(state) ; 
			if (value == 0) {
				counter ++ ;
			}
			
			System.out.println(value + "\t" + state);
				
		}
		
		System.out.println("there are " + counter/100.0 + "% non attacking queen");
	}

}
