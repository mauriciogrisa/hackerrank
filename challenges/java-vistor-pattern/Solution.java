import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Scanner;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis
{
    public abstract int getResult();
    public abstract void visitNode(TreeNode node);
    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    
    int result = 0;
    
    public int getResult() {
        return result;
    }

    public void visitNode(TreeNode node) {
    }

    public void visitLeaf(TreeLeaf leaf) {
        result += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    
    static final int MODULE = 1000000007;
    
    long result = 1;
    
    public int getResult() {
        return (int) result;
    }

    public void visitNode(TreeNode node) {
        if(node.getColor() == Color.RED)
              result = (result * node.getValue()) % MODULE;
    }

    public void visitLeaf(TreeLeaf leaf) {
        if(leaf.getColor() == Color.RED)
              result = (result * leaf.getValue() % MODULE);
    }
}

class FancyVisitor extends TreeVis {
    
    int sum1 = 0;
    int sum2 = 0;
    
    public int getResult() {
        return Math.abs(sum1 - sum2);
    }

    public void visitNode(TreeNode node) {
        if(node.getDepth() % 2 == 0)
            sum1 += node.getValue();
    }

    public void visitLeaf(TreeLeaf leaf) {
        if(leaf.getColor() == Color.GREEN)
            sum2 += leaf.getValue();
    }
}

public class Solution {
  
    static int[] values;
    static Color[] colors;
    static Tree[] nodes;
    static Map<Integer, Set<Integer>> children = new HashMap<>();  

    public static Tree addNode(int i, int depth) {
        if(nodes[i] == null) {            
            if(children.get(i).size() > 1 || i == 0) {
                 // System.out.println("Insert tree node " + values[i] + " from index " + i + " at depth " + depth);

                nodes[i] = new TreeNode(values[i], colors[i], depth); 
                
                for(int child: children.get(i)) {                    
                    Tree childNode = addNode(child, depth + 1);
                    if(childNode != null) {
                        ((TreeNode) nodes[i]).addChild(nodes[child]);
                        // System.out.println(i + " " + child);
                    }
                }
                return nodes[i];
            } else {
                 // System.out.println("Insert leaf node " + values[i] + " from index " + i + " at depth " + depth);
                nodes[i] = new TreeLeaf(values[i], colors[i], depth);
                return nodes[i];
            }
        }
        return null;
    }
  
    public static Tree solve() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        values = new int[n];
        colors = new Color[n];
        nodes = new Tree[n];
        
        for(int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
        }
        for(int i = 0; i < n; i++) {
            int color = scanner.nextInt();
            if(color == 0)
                colors[i] = Color.RED;
            else
                colors[i] = Color.GREEN;
        }
        
        while(scanner.hasNext()) {
            int u = scanner.nextInt() - 1;
            int v = scanner.nextInt() - 1;
            
            Set<Integer> list;
            
            if(!children.containsKey(u)) {
                list = new HashSet<>();
                list.add(v);
                children.put(u, list);                
            } else {
                list = children.get(u);
                list.add(v);
            }
            
            if(!children.containsKey(v)) {
                list = new HashSet<>();
                list.add(u);
                children.put(v, list);                
            } else {
                list = children.get(v);
                list.add(u);
            }            
            
        }         
        
        return  addNode(0, 0);
        
    }

