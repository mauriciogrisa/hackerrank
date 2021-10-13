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
    protected Node left, right, parent;

    Node(int value, Node top) {
        this.value = value;
        this.parent = top;
        left = null;
        right = null;
    }

}

class Result {

    public static Node insert(Node root, Node parent, int value) {

        if (root == null) {
            return new Node(value, parent);
        }

        if (value <= root.value) {
            root.left = insert(root.left, root, value);
        } else {
            root.right = insert(root.right, root, value);
        }

        return root;
    }

    static Node root = null;
    static Node m = null;

    static Node getPrevNode(Node node) {

        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        } else if (node.parent != null) {

            if (node.parent.right == node) {
                return node.parent;
            }

            if (node.parent.left == node) {
                while (node.parent.right != node) {
                    if (node.parent == root) {
                        return null;
                    }
                    node = node.parent;
                }
                return node.parent;
            }
        }

        return null;
    }

    static Node getNextNode(Node node) {
        Node current;

        if (node.right != null) {
            current = node.right;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        current = node.parent;
        while (current != null && node == current.right) {
            node = current;
            current = current.parent;
        }
        return current;
    }

    public static List<Double> runningMedian(List<Integer> a) {
        List<Double> results = new ArrayList<>();

        for (int i = 0; i < a.size(); i++) {
            int value = a.get(i);
            root = insert(root, null, value);
            if (i == 0) {
                m = root;
            }

            if (i % 2 == 0) {
                if (value > m.value)
                    m = getNextNode(m);
                results.add((double) m.value);
            } else {
                Node n = null;
                if (value <= m.value) {
                    n = m;
                    m = getPrevNode(m);
                } else
                    n = getNextNode(m);

                results.add((m.value + n.value) / 2.0);

            }

        }

        return results;

    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = IntStream.range(0, aCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        List<Double> result = Result.runningMedian(a);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
