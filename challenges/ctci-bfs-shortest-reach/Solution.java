import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static class Graph {

        int[][] graph;        
        
        public Graph(int size) {
            graph = new int[size][size];
        }

        public void addEdge(int first, int second) {
            graph[first][second] = 1;
            graph[second][first] = 1;
        }
        
        public int[] shortestReach(int startId) { // 0 indexed
        
            Queue<Integer> nodesToBeVisited = new LinkedList<>();        
            int[] output = new int[graph.length];
            Arrays.fill(output, -1);
            output[startId] = 0;
            nodesToBeVisited.add(startId);
            
            while(!nodesToBeVisited.isEmpty()) {
                int i = nodesToBeVisited.poll();
                for(int j = 0; j < graph.length; j++) {
                    if(graph[i][j] == 1 && output[j] == -1) {
                        output[j] = output[i] + 6;
                        nodesToBeVisited.add(j);                        
                    }
                }                    
            }        
        
            return output;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
      
        int queries = scanner.nextInt();
        
        for (int t = 0; t < queries; t++) {
            
            // Create a graph of size n where each edge weight is 6:
            Graph graph = new Graph(scanner.nextInt());
            int m = scanner.nextInt();
            
            // read and set edges
            for (int i = 0; i < m; i++) {
                int u = scanner.nextInt() - 1;
                int v = scanner.nextInt() - 1;
                
                // add each edge to the graph
                graph.addEdge(u, v);
            }
            
            // Find shortest reach from node s
            int startId = scanner.nextInt() - 1;
            int[] distances = graph.shortestReach(startId);
 
            for (int i = 0; i < distances.length; i++) {
                if (i != startId) {
                    System.out.print(distances[i]);
                    System.out.print(" ");
                }
            }
            System.out.println();            
        }
        
        scanner.close();
    }
}
