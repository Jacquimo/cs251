package part2;

import java.io.File;

public class Part2Test {

    public static void main(String[] args) {
        /*String fileName = args[0];
        if(fileName!=null&&!"".equals(fileName)){
    		int i = -1;*/
    	
    	for (int i = 1; i <= 26; ++i) {
    		String fileName = "./freeTrees/freeTree_" + i + ".txt";

            File file = new File(fileName);
            Tree tree = Tree.readTreeFromFile(file);
            System.out.printf("Testing with %d nodes\n", i);
            
            double brute = bruteForceTesting(tree);
            double recursive = recursiveTesting(tree);
            
            if (brute > 0.0 && recursive > 0.0)
            	System.out.printf("Recursive speed increase = %.2f%c increase\n", (brute - recursive) / recursive * 100, '%');
            
            System.out.println("-------------------------");
    		System.out.flush();
    	}
    	
    	// Only recursive testing, as it would take far too long with the brute force solution
    	int[] testFiles = {/*26,*/ 27, 28, 29, 30, 50, 75, 100, 150, 200, 250, 500};
    	for (int i = 0; i < testFiles.length; ++i) {
    		String fileName = "./freeTrees/freeTree_" + testFiles[i] + ".txt";

            File file = new File(fileName);
            Tree tree = Tree.readTreeFromFile(file);
            System.out.printf("Testing with %d nodes\n", testFiles[i]);
            
            recursiveTesting(tree);
            
            System.out.println("-------------------------");
            System.out.flush();
    	}
            
        /*}
        else{
            System.err.println("Missing inputfile argument");
        }*/


    }
    
    private static double bruteForceTesting(Tree tree) {
    	// Brute Force Testing
        double bruteBegin, bruteEnd, bruteRun;
        
        bruteBegin = System.nanoTime();
		boolean[] pebbelingBruteForce = PebbleGame.bruteForceOptimalPebbling(tree);
		double profit = PebbleGame.profit(tree, pebbelingBruteForce);
		bruteEnd = System.nanoTime();
		
		bruteRun = bruteEnd - bruteBegin;
		
		System.out.printf("Brute Force running time = %.2f ns\n", bruteRun);
		if (PebbleGame.isValid(tree, pebbelingBruteForce))
			System.out.println("Brute force pebbling is valid");
		else
			System.out.println("Brute force pebbling is NOT valid");
		System.out.printf("Brute Force max profit = %.2f\n", profit);
		System.out.println();
		
		return bruteRun;
    }
    
	private static double recursiveTesting(Tree tree) {
		// Recursive Testing
		double dynamicStart, dynamicEnd, dynamicRun;
		
		dynamicStart = System.nanoTime();
		boolean[] pebbelingRecursive = PebbleGame.recursiveOptimalPebbling(tree);
		double profit = PebbleGame.profit(tree, pebbelingRecursive);
		dynamicEnd = System.nanoTime();
		
		dynamicRun = dynamicEnd - dynamicStart;

		System.out.printf("Recursive running time = %.2f ns\n", dynamicRun);
		if (PebbleGame.isValid(tree, pebbelingRecursive))
			System.out.println("Recursive pebbling is valid");
		else
			System.out.println("Recursive pebbling is NOT valid");
		System.out.printf("Recursive max profit = %.2f\n", profit);
		System.out.println();
		
		return dynamicRun;
	}

}
