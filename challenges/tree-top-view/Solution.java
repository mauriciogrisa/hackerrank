import java.util.*;
import java.io.*;

class Node {
    Node left;
    Node right;
    int data;
    
    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class Solution {

	/* 
    
    class Node 
    	int data;
    	Node left;
    	Node right;
	*/
    
    static Map<Integer, List<Integer>> map = new TreeMap<>();
    
    public static void checkNodes(Node root, int pos, int depth) {
        if(root != null) {
            checkNodes(root.left, pos - 1, depth + 1);
            
            if(!map.containsKey(pos) || depth < map.get(pos).get(1))
                map.put(pos, new ArrayList<Integer>(Arrays.asList(root.data, depth)));
            
            checkNodes(root.right, pos + 1, depth + 1); 
        }      
    }    
    
	public static void topView(Node root) {
        checkNodes(root, 0, 0);        
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet())
            System.out.print(entry.getValue().get(0) + " ");
    }

	public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        topView(root);
    }	
}
