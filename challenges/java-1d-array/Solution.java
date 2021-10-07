import java.util.*;

public class Solution {
    
    public static boolean canWinRec(int leap, int[] game, int i) {
        int n = game.length;
        if(i > n - 1) {
            return true;
        } else if(i < 0 || game[i] != 0) {
            return false;
        }
        else {
            
            game[i] = -1;
            //System.out.println("Jump from " + i + " to " + (i + leap));
            boolean result = canWinRec(leap, game, i + leap);            
            if(result == false) {    
                //System.out.println("Jump from " + i + " to " + (i + 1)); 
                result = canWinRec(leap, game, i + 1);
            } 
            if(result == false) { 
                //System.out.println("Jump from " + i + " to " + (i - 1));
                result = canWinRec(leap, game, i - 1);                
            }
            return result;
        }
    }

    public static boolean canWin(int leap, int[] game) {
        return canWinRec(leap, game, 0);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int q = scan.nextInt();
        while (q-- > 0) {
            int n = scan.nextInt();
            int leap = scan.nextInt();
            
            int[] game = new int[n];
            for (int i = 0; i < n; i++) {
                game[i] = scan.nextInt();
            }
            
            System.out.println( (canWin(leap, game)) ? "YES" : "NO" );
        }
        scan.close();
    }
}
