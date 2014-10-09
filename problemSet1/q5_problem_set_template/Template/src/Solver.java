import java.awt.List;
import java.lang.reflect.Array;
import java.util.*;

/* TODO: Implement the solver here 
 * @author:
 */
public class Solver {
    
    /* Input: 
     *  - num[] 5 numbers
     *  - targetAnswer: the goal
     * Output:
     *  - Return true if there is a solution
     *  - Save the solution in the sol array
     *  - Save the operations you choose in operations array
     */
    
	public boolean solve(int num[], int targetAnswer, int sol[], char operations[]) throws Exception{
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < num.length; ++i)
			numbers.add(num[i]);
				
		//return solve(num, sol, operations, targetAnswer, 4, 0);
		//return false;
		//int firstVal = numbers.remove(0);
		return solve(numbers, sol, operations, targetAnswer, 0, numbers.size() - 2);
    }
	
	public boolean solve(ArrayList<Integer> num, int sol[], char ops[], int targetAnswer, int prevSum,  int opsIndex) throws Exception {		
		if (prevSum > targetAnswer)
			return false;
		
		if (prevSum == targetAnswer)
			return num.size() <= 0;
		
		if (num.size() <= 0)
			return targetAnswer == prevSum;
		
		//int valToUse = num.remove(indexNumToUseInComp);
		for (int i = 0; i < num.size(); ++i) {
			int nextNum = num.remove(i);
			sol[opsIndex + 1] = nextNum;
			//ArrayList<Integer> otherNums = (ArrayList<Integer>) num.clone();
			
			// First check to see if the solution works using addition
			ops[opsIndex] = '+';
			if (solve(num, sol, ops, targetAnswer, nextNum + prevSum, opsIndex - 1)) // Recursively check if this configuration is valid	
				return true;
			
			ops[opsIndex] = '-';
			if (solve(num, sol, ops, targetAnswer, nextNum - prevSum, opsIndex - 1))
				return true;
			
			ops[opsIndex] = '*';
			if (solve(num, sol, ops, targetAnswer, nextNum * prevSum, opsIndex - 1))
				return true;
			
			// This wasn't the right place for this number, so put it back in the list (for consideration in future recursive calls)
			num.add(i, nextNum);
		}
		// No permutation of all of the numbers work
		return false;
	}
    
}
