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
    
    public static int updateListWithCumulativeSum(int maxSum, List<Integer> arr) {
        int sum = 0;        
        int end = 0;
        for(end = 0; end < arr.size(); end++) {
            sum += arr.get(end);
            arr.set(end, sum);
            if(sum > maxSum)            
                break;
        }
        
        return end;
        
    }
    
    public static int twoStacks(int maxSum, List<Integer> a, List<Integer> b) {
                
        int endA = updateListWithCumulativeSum(maxSum, a);
        int endB = updateListWithCumulativeSum(maxSum, b);
        
        int totalMoves = endB;
        
        int j = endB - 1;
        for(int i = 0; i < endA; i++) {                       
            while(j >= 0 && a.get(i) + b.get(j) > maxSum)
                j--;            
            totalMoves = Integer.max(totalMoves, 2 + i + j);
        }
        
        return totalMoves;
 
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int g = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, g).forEach(gItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                int maxSum = Integer.parseInt(firstMultipleInput[2]);

                List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                List<Integer> b = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                int result = Result.twoStacks(maxSum, a, b);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
