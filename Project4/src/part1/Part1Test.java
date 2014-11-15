package part1;

import java.io.File;

public class Part1Test {

    public static void main(String[] args) {
        String fileName = args[0];
        if(fileName!=null&&!"".equals(fileName)){

            File file = new File(fileName);
            Tree tree = Tree.readTreeFromFile(file);
            int height;
            height= Tree.iterativeHeight(tree);
            System.out.println("Iterative height of tree = " + height);
            height= Tree.recursiveHeight(tree);
            System.out.println("Recursive height of tree = " + height);

        }
        else{
            System.err.println("Missing inputfile argument");
        }


    }

}
