/*
 * Names: Alexa Hansen (ahanse27), Lily Alvarado (aalva129), Sophia Martyrossian (smartryo)
 * Class: CPE 349
 * Assignment: Project 3
 * Date: 07/09/2022
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameProblem {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Please enter a filename to read. (i.e \"name.txt\")");  //asking for input (file name)
        Scanner scan = new Scanner(System.in);                                      //read input
        String fileName = scan.next();                                              //save filename
        File file = new File(fileName);                                             

        try {
            Scanner inputFile = new Scanner(file);                                      //reading info from file
            int rows = inputFile.nextInt();
            int columns = inputFile.nextInt();
            inputFile.nextLine();               //
            int[][] A = new int[rows][columns]; //array to hold matrix

            //read values from file -- will be passed as parameters for game method
            for(int a = 0; a < rows; a++){
                for(int b = 0; b < columns; b++){
                    A[a][b] = inputFile.nextInt();
                }
            }
            game(rows, columns, A);
        }
        catch (FileNotFoundException e) {
            //System.out.println(e.getMessage());
            System.out.println("File not found. \nGoodbye.");
        }

    }
    /*
     * Bottom-up method
     * This method suggests an iterative approach: solving sub-problems one by one,
     * from the smallest to the largest, registering results of already solved sub-problems
     * in a suitable data structure (e.g. array, hash-table, etc.) to be used later.
     */
    public static void game(int n, int m, int[][] A) {          /* n = #rows, m = #columns, A is the grid */
        //define a table S[1..n,1..m] to hold max scores
        int row, column;
        row = n-1;
        column = m-1;
        /* S matrix will save the route that will produce the greatest score */
        int[][] S = new int[n][m];
        /* R is an auxiliary matrix that will be filled in parallell with S */
        char[][] R = new char[n][m];                            /* will save d (down), r (right), or e (exit) in table */

        S[row][column] = A[row][column];                        /* bottom right corner base case */
        R[row][column] = 'e';                                   /* bottom right corner base case */

        int max = S[0][0];
        int max_row = 0, max_col = 0;
        /*
          S[i][j] = A[n][m] -> for i = n, j = m; bottom-right square... we can only exit
                    max{S[i + 1, m],0} + A[i, m] -> for i != n, j = m; last column... we can move down or exit
                    max{S[n, j + 1],0} + A[n, j] -> for i = n, j != m; last row... we move right or exit
                    max{S[i + 1, j], S[i, j + 1]} + A[i, j] -> for i != n, j != m; check for better... move down or right
         */
        /* max{S[i + 1, m],0} + A[i, m] -> for i != n, j = m */
        for (int i = row - 1; i >= 0; i--){
            if (S[i + 1][column] > 0){                                  /* case of far right column & cant move right */
                S[i][column] = S[i + 1][column] + A[i][column];
                R[i][column] = 'd';                                     /* move down */
            }
            else{                                                       /* case of far right corner */
                S[i][column] = A[i][column];
                R[i][column] = 'e';
            }
            if (S[i][m-2] >= max) {
                max = S[i][m-2];
                max_row = i;
                max_col = m-2;
            }
        }

        /* max{S[n, j + 1],0} + A[n, j] -> for i = n, j != m */
        for (int j = column - 1; j >= 0; j--){
            if (S[row][j + 1] >=0){                                    /* case of last row and cant move down */
                S[row][j] = S[row][j + 1] + A[row][j];
                R[row][j] = 'r';                                        /* move right */
            }
            else{                                                       /* case of exit last row */
                S[row][j] = A[row][j];
                R[row][j] = 'e';
            }
            if (S[n-2][j] >= max) {
                max = S[n-2][j];
                max_row = n-2;
                max_col = j;
            }
        }

        /* max{S[i + 1, j], S[i, j + 1]} + A[i, j] -> for i != n, j != m */
        for (int k = row - 1; k >= 0; k--){
            for(int l = column - 1; l >= 0; l--){
                if (S[k + 1][l] > S[k][l + 1]){                                  /* is it better to move right or down */
                    S[k][l] = S[k+1][l] + A[k][l];
                    R[k][l] = 'd';                                              /* move down */
                }
                else{
                    S[k][l] = S[k][l + 1] + A[k][l];
                    R[k][l] = 'r';                                              /* move right */
                }
                if (S[k][l] >= max) {
                    max = S[k][l];
                    max_row = k;
                    max_col = l;
                }
            }
        }

        printRoute(R, max_row, max_col, max);
    }

    private static void printRoute(char[][] R, int row, int column, int max){
        /* Best score: 15 */
        /* Best route: [3,1] to [3,2] to [4,2] to [4,3] to [4,4] to [5,4] to exit */
        System.out.println("Best score: " + max);
        System.out.print("Best route: ");
        while(R[row][column] != 'e'){                                       /*so long as we haven't exited the matrix */

            System.out.print("[" + (row + 1) + "," + (column + 1) + "] to ");

            if (R[row][column] == 'r')
                column++;

            else
                row++;
        }
        System.out.println("[" + (row + 1) + "," + (column + 1) + "] to exit");
    }
}