/*
 * Names: Alexa Hansen (ahanse27), Lily Alvarado (aalva129)
 * Class: CPE 349
 * Assignment: Project 5 Pt 1 & 2
 * Date: 07/20/2022
 */
import java.util.ArrayList;
import java.util.LinkedList;

public class DiGraph {

    private static class VertexInfo {
        int distance;
        int predecessor;

        VertexInfo(int distance, int predecessor) {
            this.distance = distance;
            this.predecessor = predecessor;
        }
    }
    private ArrayList<LinkedList<Integer>> graph;                   //private Linked list array
    private int N;

    public DiGraph(int N) {                                         //constructor (int N)
        this.N = N;
        System.out.println("DiGraph constructor initialized");
        this.graph = new ArrayList<>(N);         //create variable array

        for (int i = 0; i < N; i++) {                               //initialize array
            this.graph.add(new LinkedList<>());
        }
    }

    /* 5 public methods */
    /* check if edge exists before adding anything two parameters identify vertices representing the edge that needs
       to be added to the graph (to vertex is added as from vertex’s neighbor). */
    public void addEdge(int from, int to){

        if(!graph.get(from - 1).contains(to - 1)){                          //if it doesn't exist then we add them
            graph.get(from - 1).add(to - 1);
            System.out.println("Added Edge: From: " + from + " To: " + to);
        }
        else {
            System.out.println("Edge was already added");
        }
    }
    /* where two parameters identify vertices representing the edge that needs to be deleted from the graph
       (to vertex is removed as from vertex’s neighbor). */
    public void deleteEdge(int from, int to){

        if(graph.get(from - 1).contains(to -1)){  //if it doesn't exist then we add them
            graph.get(from - 1).removeFirstOccurrence(to - 1);
            System.out.println("Deleted Edge: From: " + from + " To: " + to);
        }
        else{
            System.out.println("Edge does not exist");
        }

    }

    /* Computes and returns the number of edges in the graph */
    public int edgeCount(){
        int edges = 0;
        for(int i = 0; i < graph.size(); i++)
        {
            edges += graph.get(i).size();
        }
        return edges;
    }

    /* Returns the number of vertices in the graph (arrays length) */
    public int vertexCount(){
        return graph.size();
    }

    /* Method prints out the graph in the specified format. */
    public void print(){
        int numVertex = vertexCount();
        int j;
        for(int i = 0; i < numVertex; i++){
            System.out.print((i + 1) + " is connected to: ");
            LinkedList<Integer> vertex = graph.get(i);
            int nextVertex = vertex.size();
            if(nextVertex == 0){
                System.out.println();                                      /* skips to next line */
                continue;
            }
            for(j = 0; j < nextVertex - 1; j++){
                Integer V = vertex.get(j);
                System.out.print((V + 1) + ", ");
            }
            Integer V = vertex.get(j);
            System.out.println(V + 1);
        }

    }

    /************************ Pt 2 Functions ************************/

    /* Returns an array of integers representing the indegrees of all the vertices in the graph */
    private int[] indegrees(){
        int [] inds = new int[graph.size()]; //natural indexing
        for(int i = 0; i < graph.size(); i++) {
            for(int j = 0; j < graph.get(i).size();j++) {                  /* look for connected vertices*/
                inds[graph.get(i).get(j)]++;                            /* increment indegree connections*/
            }
        }
        return inds;
    }

    /* Returns an array of topologically sorted vertices */
    public int[] topSort() throws IllegalArgumentException {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int[] A = new int[graph.size()];
        int[] inds = indegrees();

        //queue is empty
        for (int u = 0; u < inds.length; u++){
            if (inds[u] == 0){
                queue.addLast(u); //natural index
            }
        }
        int i = 0;
        while(!queue.isEmpty()){
            int x = queue.removeFirst();
            A[i] = x + 1;
            i++;

            for(int k = 0; k < graph.get(x).size(); k++) {
                int v = graph.get(x).get(k);
                inds[v]--;
                if(inds[v] == 0) {
                    queue.addLast(v);
                }
            }
        }

        //IllegalArgumentException();
        if(i != A.length){
            throw new IllegalArgumentException("This is a Cyclic Graph");
        }

        return A;
    }

    /************************ Pt 3 ************************/

    private VertexInfo[] BFS(int s){
        int N = this.graph.size();
        VertexInfo[] VA = new VertexInfo[N];
        LinkedList<Integer> queue = new LinkedList<>();
        for (int u = 0; u < N; u++){
            VA[u].distance = -1;
            VA[u].predecessor = -1;
        }
        VA[s].distance = 0;
        queue.addLast(s);
        while (!queue.isEmpty()){
            int w = queue.removeFirst();
            for (int v = 0; v < graph.get(w).size(); v++){
                if (VA[v].distance == -1){
                    VA[v].distance = VA[w].distance + 1;
                    VA[v].predecessor = w;
                    queue.addLast(v);
                }
            }
        }
        return VA;
    }

    public boolean isTherePath(int from, int to){
        VertexInfo[] VA = BFS(from);
        if(VA[to].distance != -1){
            return true;
        }

        return false;
    }

    public int lengthOfPath(int from, int to){
        VertexInfo[] VA = BFS(from);

        return VA[to].distance;
    }

    public void printPath(int from, int to){
        VertexInfo[] VA = BFS(from);

        if (VA[to].distance == -1){
            System.out.println("There is no path");
        }
        else{
            String output ="";
            while(from != to){
                output = "->" + to + output;
                to = VA[to].predecessor;
            }
            output = from + output;
            System.out.println(output);
        }

    }
}