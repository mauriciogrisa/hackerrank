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
    public static int heightRecursive(Node root, int depth) {
        if(root != null) {
            int leftHeight = heightRecursive(root.left, depth + 1);
            int rightHeight = heightRecursive(root.right, depth + 1);
            if(leftHeight > rightHeight)
                return leftHeight;
            else
                return rightHeight;
        }
        
        return depth - 1;
    }
    
	public static int height(Node root) {
      	return heightRecursive(root, 0);
    }

	public static Node insert(Node root, int data) {
