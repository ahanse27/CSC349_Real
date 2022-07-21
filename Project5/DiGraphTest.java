
/*
 * Names: Alexa Hansen (ahanse27), Lily Alvarado (aalva129)
 * Class: CPE 349
 * Assignment: Project 5 Pt 1 & 2
 * Date: 07/20/2022
 */
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DiGraphTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        DiGraph graph = new DiGraph(sc.nextInt());
        sc.nextLine(); // throw away next line
        boolean switchFinish = false;

        System.out.println();
        printSwitchMenu();
        System.out.println();

        do{
            int from, to;
            System.out.println("Enter operation to be done: ");
            if(sc.hasNextLine()){                                                       /* checks is there is another line of input */
                String input = sc.nextLine();
                StringTokenizer line = new StringTokenizer(input);
                if(line.countTokens() > 1){                     /* not working on case 'a 3 4' in example 1*/
                    System.out.println("Invalid option.");
                }
                //switch statement
                switch(input){
                    case "a":
                        System.out.println("Input the starting edge value and ending edge value separated by a space");
                        from = sc.nextInt();
                        to = sc.nextInt();
                        sc.nextLine();
                        graph.addEdge(from, to);                                        /* call addEdge with user input values */
                        //System.out.println("Added Edge: From: " + from + " To: " + to);
                        break;
                    case "d":
                        System.out.println("Input the starting edge value to and ending edge value separated by a space");
                        from = sc.nextInt();
                        to = sc.nextInt();
                        sc.nextLine();
                        graph.deleteEdge(from, to);                                        /* call deleteEdge with user input values */
                       //System.out.println("Deleted Edge: From: " + from + " To: " + to);
                        break;
                    case "e":
                        System.out.println("Number of edges is: " + graph.edgeCount());
                        break;
                    case "v":
                        System.out.println("Number of vertices is: " + graph.vertexCount());
                        break;
                    case "p":
                        System.out.println("The graph is the following: ");
                        graph.print();
                        break;
                    case "q":
                        switchFinish = true;
                        System.out.println("Program has ended. Goodbye.");
                        break;
                    case "t":
                        try{
                        int[] sort = graph.topSort();
                        int i;
                        for(i = 0; i < graph.vertexCount() - 1; i++){
                            System.out.print("" + sort[i] + ", ");
                        }
                        System.out.println("" + sort[i]);
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                    default:
                        System.out.println("Invalid choice has been entered, please enter a valid option.");
                }
            }
        }while(!switchFinish);

    }

    private static void printSwitchMenu(){
        System.out.println("Choose one of the following operations: ");
        System.out.println("- add edge (enter a)");
        System.out.println("- delete edge (enter d)");
        System.out.println("- edge count (enter e)");
        System.out.println("- vertex count (enter v)");
        System.out.println("- print graph (enter p)");
        System.out.println("- Quit (enter q)");
    }
    
}
