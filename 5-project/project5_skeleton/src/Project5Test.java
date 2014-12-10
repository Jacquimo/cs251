import java.io.File;
import java.util.ArrayList;

/** @author: CS251TAGroup
 * This file is designed to help you debug your code and check your output
 * WARNING: show the schedule of a big graph is not a good idea
 * */


public class Project5Test {

    public static void main(String[] args) {
        
        String fileName = args[0];
        
        boolean enableSchedule = false;
        int stations = -1; /* infinity number of stations */
        
        for (int i = 1; i < args.length; i ++){
          if (args[i].equals("-s")) enableSchedule = true;   
          else stations = Integer.parseInt(args[i]);
        }

        
        if (fileName != null && !"".equals(fileName)) {

        	System.out.printf("File Name = %s\n\n", fileName);
        	
        	File file = new File(fileName);
            
            Digraph graph = DagUtilities.readGraphFromFile(file);
            
            int[] graphTopoSort = DagUtilities.topologicalSort(graph);
            
            int length = DagUtilities.longestPath(graph);
            System.out.printf("Longest Path = %d\n\n", length);
            
            Schedule schedule;

            if (stations == -1){
                schedule = DagUtilities.minProdSpanSchedule(graph);
            }
            else{
                schedule = DagUtilities.spanKStations(graph, stations);
            }
            
            /// My Code ///
            for (int i = 0; i < schedule.getProductionSpan(); ++i) {
            	ArrayList<Integer> step = schedule.getSchedule(i);
            	System.out.printf("Step %d: ", i + 1);
            	
            	for (int j = 0; j < step.size(); ++j) 
            		System.out.printf("%d ", step.get(j));

            	System.out.println();
            }
            /// My Code ///
            
            System.out.println("----------------- Results ------------------");
            int spanlength = schedule.getProductionSpan();

            System.out.println("The production span is " + spanlength );

            if (enableSchedule){
                System.out.println("The schedule looks like:\nTime\t  ");

                for (int t = 0; t < spanlength; t ++){
                    ArrayList<Integer> currentTimeSchedule = schedule.getSchedule(t);
                    System.out.print(t + "\t");
                    for (int j = 0; j < currentTimeSchedule.size(); j ++){
                    
                        System.out.print(currentTimeSchedule.get(j) + "\t");

                    }
                    System.out.println();
                }
            }
        }
        else {
            System.err.println("Missing inputfile argument");
        }

    }
}
