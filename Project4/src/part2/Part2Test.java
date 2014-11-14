package part2;

import java.io.File;

public class Part2Test {

    public static void main(String[] args) {
        String fileName = args[0];
        if(fileName!=null&&!"".equals(fileName)){

            File file = new File(fileName);
            Tree tree = Tree.readTreeFromFile(file);
            boolean [] pebbelingBruteForce = PebbleGame.bruteForceOptimalPebbling(tree);
            PebbleGame.isValid(tree, pebbelingBruteForce);
            PebbleGame.profit(tree, pebbelingBruteForce);
            boolean [] pebbelingRecursive = PebbleGame.recursiveOptimalPebbling(tree);
            PebbleGame.isValid(tree, pebbelingRecursive);
            PebbleGame.profit(tree, pebbelingRecursive);
        }
        else{
            System.err.println("Missing inputfile argument");
        }


    }

}
