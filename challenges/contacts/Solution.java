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

class Result {

    public static List<Integer> contacts(List<List<String>> queries) {
        List<Integer> result = new ArrayList<>();        
        TreeSet<String> tree = new TreeSet<>();
        
        for(List<String> query : queries) {
            String operation = query.get(0); 
            String name = query.get(1);   
            switch(operation) {
                case "add": 
                    tree.add(name);
                    break;
                case "find":
                    int count = 0;
                    Iterator<String> iterator = ((TreeSet<String>)tree).tailSet(name).iterator();
                    while(iterator.hasNext() && 
                          iterator.next().startsWith(name))
                        count++;                    
                    result.add(count);
                    break;                
            }
            
        }
        
        return result;

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int queriesRows = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> queries = new ArrayList<>();

        IntStream.range(0, queriesRows).forEach(i -> {
            try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.contacts(queries);

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
