/*
 * Names: Alexa Hansen (ahanse27), Lily Alvarado (aalva129)
 * Class: CPE 349
 * Assignment: Project 5 Pt 3 & 4
 * Date: 07/23/2022
 */
import java.util.ArrayList;
import java.util.LinkedList;

public class DiGraph {

    /* Pt 3 private nested class, 2 variables */
    private class VertexInfo {
        int distance;
        int predecessor;
        public VertexInfo(int distance, int predecessor) {
            this.distance = distance;
            this.predecessor = predecessor;
        }
    }
    /* Pt 4 private nested class */
    private class TreeNode{
        int numVertex;
        LinkedList<TreeNode> list;
        public TreeNode(int numVertex, LinkedList<TreeNode> list){
            this.numVertex = numVertex;
            this.list = list;
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
        if(i != A.length){
            throw new IllegalArgumentException("This is a Cyclic Graph");
        }
        return A;
    }

    /************************ Pt 3 Functions ************************/

    /* Returns data that can be used to construct shortest path */
    private VertexInfo[] BFS(int s){
        int N = this.graph.size();
        VertexInfo[] VA = new VertexInfo[N];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        for(int u = 0; u < N; u++){
            VA[u] = new VertexInfo(-1, -1);             /* initialize all values*/
        }
        VA[s].distance = 0;
        queue.addLast(s);
        while (!queue.isEmpty()){
            int w = queue.removeFirst();
            for(int t = 0; t < graph.get(w).size(); t++){
                int v = graph.get(w).get(t);
                if (VA[v].distance == -1){
                    VA[v].distance = VA[w].distance + 1;
                    VA[v].predecessor = w;
                    queue.addLast(v);
                }
            }
        }
        return VA;
    }

    /* Returns true if path exists */
    public boolean isTherePath(int from, int to){
        VertexInfo[] VA = BFS(from - 1);
        if(VA[to - 1].distance != -1){   /* if predecessor for to exists */
            return true;
        }
        else
            return false;
    }

    /* returns the shortest distance from the to and from vertices */
    public int lengthOfPath(int from, int to){      /* only returning 0 for path length */
        int length = 0;
        int k = to - 1;
        VertexInfo[] VA = BFS(from - 1);
        if (!isTherePath(from, to)){    /* if path is not reachable */
            return -1;
        }
        while(VA[k].predecessor != -1){
            length++;
            //increament while loop to change
            k = VA[k].predecessor;
        }
        return length;
    }

    /* Prints shortest path */
    public void printPath(int from, int to){

        if(isTherePath(from, to)){
            int k = to - 1;
            VertexInfo[] VA = BFS(from - 1);
            String output = "";
            while(VA[k].predecessor != -1){
                output = "->" + (k + 1) + output;
                k = VA[k].predecessor;
            }
            System.out.println("" + (k + 1) + output);
        }
        else{
            System.out.println("There is no path");
        }

    }

    /************************ Pt 4 Functions ************************/

    /* Returns the root of the BF Tree for given s vertex */
    private TreeNode buildTree(int s){
        int N = this.graph.size();
        VertexInfo[] VA = BFS(s - 1); //(s)
        TreeNode[] treeNodes = new TreeNode[N];
        for(int i = 0; i < N; i++){              // i not i + 1?
            treeNodes[i] = new TreeNode(i + 1, new LinkedList<TreeNode>());
        }
        for(int j = 0; j < VA.length; j++){
            if(VA[j].predecessor != -1){
                treeNodes[VA[j].predecessor].list.add(treeNodes[j]);
            }
        }
        return treeNodes[s -1];
    }

    /* Prints the Breadth-First Tree for given s */
    public void printTree(int s){
        TreeNode root = buildTree(s);
        recursiveTree(root, "");
    }

    private void recursiveTree(TreeNode s, String tabs){
        System.out.println(tabs + s.numVertex);
        if(s.list.size() > 0){
            for(int i = 0; i < s.list.size(); i++){
                recursiveTree(s.list.get(i), tabs + "    ");
            }
        }
    }

}
