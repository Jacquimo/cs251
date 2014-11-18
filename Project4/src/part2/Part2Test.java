package part2;

import java.io.File;

public class Part2Test {

    public static void main(String[] args) {
        String fileName = args[0];
        if(fileName!=null&&!"".equals(fileName)){

            File file = new File(fileName);
            Tree tree = Tree.readTreeFromFile(file);
            
            // Brute Force Testing
            boolean [] pebbelingBruteForce = PebbleGame.bruteForceOptimalPebbling(tree);
            if (PebbleGame.isValid(tree, pebbelingBruteForce))
            	System.out.println("Brute force pebbling is valid");
            else
            	System.out.println("Brute force pebbling is NOT valid");
            System.out.printf("Brute Force max profit = %.2f\n", PebbleGame.profit(tree, pebbelingBruteForce));
            
            // Recursive Testing
            boolean [] pebbelingRecursive = PebbleGame.recursiveOptimalPebbling(tree);
            if(PebbleGame.isValid(tree, pebbelingRecursive))
            	System.out.println("Recursive pebbling is valid");
            else
            	System.out.println("Recursive pebbling is NOT valid");
            System.out.printf("Recursive max profit = %.2f\n", PebbleGame.profit(tree, pebbelingRecursive));
        }
        else{
            System.err.println("Missing inputfile argument");
        }


    }

}
