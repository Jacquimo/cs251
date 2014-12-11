import java.io.File;
import java.io.PrintWriter;
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
            System.out.printf("Longest Path = %d\n", length);
            
            Schedule schedule;

            if (stations == -1){
                schedule = DagUtilities.minProdSpanSchedule(graph);
            }
            else{
                schedule = DagUtilities.spanKStations(graph, stations);
            }
            
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
            
            
            
            File test = new File("./project5_skeleton/dags/400v850e.txt");
            Digraph outGraph = DagUtilities.readGraphFromFile(test);
            PrintWriter write = null;
            
            try {
            	write = new PrintWriter("./output.txt", "UTF-8");
            }
            catch (Exception e) {
            	e.printStackTrace();
            }
            
            DagUtilities.topologicalSort(outGraph);
            DagUtilities.longestPath(outGraph);
            
            for (int k = 1; k < 100; ++k) {
            	int prodSpan = DagUtilities.spanKStations(outGraph, k).getProductionSpan();
            	write.printf("%d\t", prodSpan);
            }
            write.println();
            write.close();
        }
        else {
            System.err.println("Missing inputfile argument");
        }

    }
}
