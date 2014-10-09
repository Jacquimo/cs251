/*
 * @author:
 
 */
import java.util.*;
public class solution {
    
    public static void main(String args[]){
        
        int input[] = new int[5];
        
        int sol[] = {0,0,0,0,0};
        
        char operations[] = new char[4];
        
        Scanner scanner = new Scanner(System.in);
        
        while (true){
            System.out.println("Please Enter Your Input:(Enter -1 5 times to exit)");
            int counter = 0;
            for (int i = 0; i < 5; i ++){
                input[i] = scanner.nextInt();
                if(input[i] == -1) counter++;
            }
            if (counter == 5) break;
            Solver s = new Solver();
            
            boolean ret = s.solve(input, 45, sol, operations);
            
            System.out.println(ret);
            
            if (ret == true){
                System.out.print("45 = (");
                for (int i = 0; i < 9; i ++){
                    if (i % 2 == 0){
                        System.out.print(sol[i / 2] + "");
                        
                    }
                    else {
                        System.out.print(operations[(i - 1)/2] );
                        if (i != 7) System.out.print("(");
                    }
                }
                System.out.println("))))");
            }
        }
    }
}
