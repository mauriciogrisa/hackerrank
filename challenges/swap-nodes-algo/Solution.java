import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Node {

    protected int value;
    protected Node left, right;

    Node(int value) {
        this.value = value;
        left = null;
        right = null;
    }

}

class Result {

    static void swap(Node root, int depth, int query, List<Integer> result) { 
        if (root != null) { 
            if(depth % query == 0) {
                Node aux = root.left;
                root.left = root.right;
                root.right = aux;
            } 
            
            swap(root.left, depth + 1, query, result);
            result.add(root.value);             
            swap(root.right, depth + 1, query, result);                               
         } 
    }

    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
 
        List<List<Integer>> results = new ArrayList<List<Integer>>();    
 
        Queue<Node> q = new LinkedList<>();
        Node root = new Node(1);
       
        q.add(root);        
    
        for(List<Integer> index : indexes) {
            
            Node currentNode = q.poll();
                        
            int left = index.get(0);
            int right = index.get(1);
            
            if(left != -1) {
                Node leftNode = new Node(left);
                currentNode.left = leftNode;
                q.add(leftNode); 
            }
            
            if(right != -1) {
                Node rightNode = new Node(right);
                currentNode.right = rightNode;
                q.add(rightNode);                
            }
            
        }
        
        for(int query : queries) {
            List<Integer> result = new ArrayList<>();
            swap(root, 1, query, result);
            results.add(result);
        }        
        
        return results;

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> indexes = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                indexes.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        List<List<Integer>> result = Result.swapNodes(indexes, queries);

        result.stream()
            .map(
                r -> r.stream()
                    .map(Object::toString)
                    .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
                try {
                    bufferedWriter.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
