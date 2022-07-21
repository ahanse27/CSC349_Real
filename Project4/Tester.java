/*
 * Names: Alexa Hansen (ahanse27), Lily Alvarado (aalva129), Sophia Martyrossian (smartryo)
 * Class: CPE 349
 * Assignment: Project 4
 * Date: 07/16/2022
 */
public class Tester {


    public static void main(String[] args){

        int[] d1 = new int[]{100, 50, 25, 10, 5, 1};                /* US denomination */
        int[] d2 = new int[]{100, 50, 20, 15, 10, 5, 3, 2, 1};      /* Soviet denominations */
        int[] d3 = new int[]{64, 32, 16, 8, 4, 2, 1};               /* Powers of 2 */
        int[] d4 = new int[]{100, 50, 25, 10, 1};                   /* US w/ no Nickel */
        int[] d5 = new int[]{66, 35, 27, 18, 10, 1};                /* Some Set */

        System.out.println("Testing change_DP and change_greedy algorithms");
        int matches1 = 0;
        for(int i = 1; i <= 200; i++){
            int[] dp1 = ChangeMaker.change_DP(i, d1);
            int[] g1 = ChangeMaker.change_greedy(i, d1);

            int match_dp = 0;
            int match_gr = 0;
            for(int j = 0; j < dp1.length; j++){
                match_dp += dp1[j];
                match_gr += g1[j];
            }
            if(match_dp == match_gr){
                matches1++;
            }
        }
        System.out.println("Testing set1: " + matches1 + " matches in 200 tests");

        int matches2 = 0;
        for(int i = 1; i <= 200; i++){
            int[] dp2 = ChangeMaker.change_DP(i, d2);
            int[] g2 = ChangeMaker.change_greedy(i, d2);

            int match_dp2 = 0;
            int match_gr2 = 0;
            for(int j = 0; j < dp2.length; j++){
                match_dp2 += dp2[j];
                match_gr2 += g2[j];
            }
            if(match_dp2 == match_gr2){
                matches2++;
            }
        }
        System.out.println("Testing set2: " + matches2 + " matches in 200 tests");

        int matches3 = 0;
        for(int i = 1; i <= 200; i++){
            int[] dp3 = ChangeMaker.change_DP(i, d3);
            int[] g3 = ChangeMaker.change_greedy(i, d3);

            int match_dp3 = 0;
            int match_gr3 = 0;
            for(int j = 0; j < dp3.length; j++){
                match_dp3 += dp3[j];
                match_gr3 += g3[j];
            }
            if(match_dp3 == match_gr3){
                matches3++;
            }
        }
        System.out.println("Testing set3: " + matches3 + " matches in 200 tests");

        int matches4 = 0;
        for(int i = 1; i <= 200; i++){
            int[] dp4 = ChangeMaker.change_DP(i, d4);
            int[] g4 = ChangeMaker.change_greedy(i, d4);

            int match_dp4 = 0;
            int match_gr4 = 0;
            for(int j = 0; j < dp4.length; j++){
                match_dp4 += dp4[j];
                match_gr4 += g4[j];
            }
            if(match_dp4 == match_gr4){
                matches4++;
            }
        }
        System.out.println("Testing set4: " + matches4 + " matches in 200 tests");

        int matches5 = 0;
        for(int i = 1; i <= 200; i++){
            int[] dp5 = ChangeMaker.change_DP(i, d5);
            int[] g5 = ChangeMaker.change_greedy(i, d5);

            int match_dp5 = 0;
            int match_gr5 = 0;
            for(int j = 0; j < dp5.length; j++){
                match_dp5 += dp5[j];
                match_gr5 += g5[j];
            }
            if(match_dp5 == match_gr5){
                matches5++;
            }
        }
        System.out.println("Testing set5: " + matches5 + " matches in 200 tests");
    }
    
}