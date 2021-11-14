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

class Key {
    
    private String word;
    private int index;
    
    Key(String word, int index) {
        this.word = word;
        this.index = index;
    }
    
    public String toString() {
        return "[word=" + word + ",index=" + index + "]";
    }
    
    public String getWord() {
        return word;
    }
    
    public int getIndex() {
        return index;
    }
        
}

class Result {    

    public static void noPrefix(List<String> words) {
  
        Comparator<Key> comparator = Comparator.comparing(Key::getWord).thenComparing(Comparator.comparing(Key::getIndex));         
    
        TreeSet<Key> tree = new TreeSet<>(comparator);       
        
        for(int i = 0; i < words.size(); i++)
            tree.add(new Key(words.get(i), i));
        
        int indexWordTestedFirst = Integer.MAX_VALUE;
            
        for(int i = 0; i < Integer.min(indexWordTestedFirst, words.size()); i++) {
            String word = words.get(i);
            Key ceilingEntry = tree.ceiling(new Key(word, i + 1));
            while(ceilingEntry != null && ceilingEntry.getWord().startsWith(word)) {
                indexWordTestedFirst = Integer.min(indexWordTestedFirst, ceilingEntry.getIndex() < i ? i : ceilingEntry.getIndex());
                //System.out.println(word + " " + i + " " + ceilingEntry);
                ceilingEntry = tree.ceiling(new Key(ceilingEntry.getWord(), ceilingEntry.getIndex() + 1));
            }                
        }
        
        if(indexWordTestedFirst == Integer.MAX_VALUE) {
            System.out.println("GOOD SET");
        } else {
            System.out.println("BAD SET");
            System.out.println(words.get(indexWordTestedFirst) + " ");            
        }
        
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> words = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        Result.noPrefix(words);

        bufferedReader.close();
    }
}
