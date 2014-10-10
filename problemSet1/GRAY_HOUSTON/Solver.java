import java.util.ArrayList;

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
				
		for (int i = 0; i < numbers.size(); ++i) {
			int value = numbers.remove(i);
			sol[4] = value;
			if (solve(numbers, sol, operations, targetAnswer, value, 3))
				return true;
			numbers.add(i, value);
		}
		return false;
    }
	
	public boolean solve(ArrayList<Integer> num, int sol[], char ops[], int targetAnswer, int prevSum,  int opsIndex) throws Exception {		
		if (prevSum > targetAnswer)
			return false;
		
		if (prevSum == targetAnswer)
			return num.size() <= 0;
		
		if (num.size() <= 0)
			return targetAnswer == prevSum;
		
		for (int i = 0; i < num.size(); ++i) {
			int nextNum = num.remove(i);
			sol[opsIndex] = nextNum;
			
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
