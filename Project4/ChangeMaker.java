/*
 * Names: Alexa Hansen (ahanse27), Lily Alvarado (aalva129), Sophia Martyrossian (smartryo)
 * Class: CPE 349
 * Assignment: Project 4
 * Date: 07/16/2022
 */
import java.util.Scanner;

public class ChangeMaker {

    public static int[] change_DP(int n, int[] d){  /* n is the amount and d is the array for the coin-values */

        int e = d.length;
        int [] A = new int[n]; // 1 to n
        int [] B = new int[e]; //count of coins (di-denomination) for the given n
        int [] C = new int[n]; // the remainder of our coins to compute through

        C[0] = 1;
        A[0] = e - 1;

        for (int j = 2; j <= n; j++){
            int minCoin = 500000;                   //max value of coin integer
            for (int i = 0; i < e; i++){            //going the length of di - 5 (100, 25, 10, 5, 1)
                int remainder = j - d[i];
                if(remainder == 0){                 /* case where no more change needs made */
                    minCoin = 0;
                    C[j - 1] = 1;
                    A[j - 1] = i;
                }
                else if(remainder > 0){             /* case where large enough coin is found */
                    int count = 1;
                    int added = C[remainder - 1];
                    if(count + added < minCoin){
                        minCoin = count + added;
                        C[j - 1] = minCoin;
                        A[j - 1] = i;
                    }

                }
            }
        }
        /* fill B with the numerical amount for each denomination */
        int next = A[n-1];
        B[next] = B[next] + 1;
        int count2 = n - d[next];
        while(count2 > 0){
            next = A[count2 - 1];
            B[next] = B[next] + 1;
            count2 = count2 - d[next];
        }
        return B;
    }

    public static int[] change_greedy(int n, int[] d){
        int e = d.length;
        int [] A = new int[n]; // 1 to n
        int [] B = new int[e]; //count of coins (di-denomination) for the given n
        int [] C = new int[n]; // the remainder of our coins to compute through

        C[0] = 1;
        A[0] = e - 1;
        int remainder = n;
        int dcount = 0;

        while(remainder > 0){
            if(d[dcount] <= remainder){
                B[dcount] = remainder/ d[dcount];       /* numerical amount for each denomination */
                remainder = remainder % d[dcount];      /* new remainder to be calculated */
            }
            else{
                dcount++;                               /* move to next denomination */
            }
        }
        return B;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of coin-denominations and the set of coin values:");
        /*
          input should look like: 5 100 25 10 5 1
          k = 5 (#of coins), d = 100 25 10 5 1 (value of each coin)
         */
        int k = in.nextInt();
        int[] d = new int[k];
        for (int counter = 0; counter < k; counter++){
            d[counter] = in.nextInt();
        }
        int n;
        int loopcount = 0;
        while (loopcount < k){ //repeat while n's value is positive
            System.out.println("Enter a positive amount to be changed (enter 0 to quit):");
            n = in.nextInt();
            if(n == 0){
                System.out.println("Thanks for playing. Good Bye.");
                System.exit(0);        /* purposeful exit */
            }
            if(n < 0){
                System.out.println("The value you entered is less than zero, please enter a positive value.");
                n = in.nextInt();   /* get new n value till positive */
            }
            int [] ch = change_DP(n, d);
            int [] chg = change_greedy(n, d);
            loopcount++;

            /* printing for change_dp */
            System.out.println("DP algorithm results");
            System.out.println("Amount: " + n);
            System.out.print("Optimal distribution: " );
            int distr = 0;
            for(int l = 0; l < d.length; l++){
                if(ch[l] > 0){
                    if(distr > 0){
                        System.out.print(" + " + ch[l] + "*" + d[l] + "c");
                    }
                    else{
                        System.out.print("" + ch[l] + "*" + d[l] + "c");
                    }
                }
                distr = distr + ch[l];
            }
            System.out.print("\n");
            System.out.println("Optimal coin count: " + distr);

            System.out.println();   /* Empty line to space out output/*
            /* printing for change_greedy */
            System.out.println("Greedy algorithm results");
            System.out.println("Amount: " + n);
            System.out.print("Optimal distribution: " );
            int distr2 = 0;
            for(int l = 0; l < d.length; l++){
                if(chg[l] > 0){
                    if(distr2> 0){
                        System.out.print(" + " + chg[l] + "*" + d[l] + "c");
                    }
                    else{
                        System.out.print("" + chg[l] + "*" + d[l] + "c");
                    }
                }
                distr2 = distr2 + chg[l];
            }
            System.out.print("\n");
            System.out.println("Optimal coin count: " + distr2);
            System.out.println();   // new line to space out for the next number
        }
    }
}