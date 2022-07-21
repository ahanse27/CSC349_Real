/*
* Names: Alexa Hansen (ahanse27), Lily Alvarado (aalva129), Sophie Martyrossian (smartryo)
* Class: CPE 349
* Assignment: Project 2 Pt 2
* Date: 07/01/2022
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixProduct {

    /* Will compute and return the product of A & B using Divided and Conquer Algorithm */
    public static int[][] matrixProduct_DAC(int[][] A, int[][] B){
        int aRows = A.length;
        int aCols = A[0].length;
        int bRows = B.length;
        int bCols = B[0].length;
        int n = A.length;

        if((aRows != bRows) || (aCols != bCols) || (n == 0) || (n%2 ==1)){                /* Checks if nxn matrix */
            throw new IllegalArgumentException("Either matrix A or matrix B is not a square matrix that is a power of 2.");
        }
        else{
            int[][] C = matrixProduct_DAC(A, B, 0, 0, 0, 0, A.length);
            return C;
        }
    }

    /* Will compute and return the product of A & B using Divided and Conquer Algorithm */
    public static int[][] matrixProduct_DAC(int[][] A, int[][] B, int startARow, int startACol, int startBRow, int startBCol, int n){

        int[][] C = new int[n][n];
        if(n == 1){                                                         /* Checks that matrix is power of 2 */
            C[0][0] = A[startARow][startACol] * B[startBRow][startBCol];
        }
        else{

            int midpoint = n/2;                         /* value used to split the matrix into 4 quadrants */

            /* C11 = A11 * B11 + A12 * B21 */
            int[][] C11 = sumDACMat(matrixProduct_DAC(A, B, startARow, startACol, startBRow, startBCol, midpoint), 
                matrixProduct_DAC(A, B, startARow, startACol + midpoint, startBRow + midpoint, startBCol, midpoint));

            /* C12 = A11 * B12 + A12 * B22 */
            int[][] C12 =  sumDACMat(matrixProduct_DAC(A, B, startARow, startACol, startBRow, startBCol + midpoint, midpoint), 
                matrixProduct_DAC(A, B, startARow, startACol + midpoint, startBRow + midpoint, startBCol + midpoint, midpoint));

            /* C21 = A21 * B11 + A22 * B21 */
            int[][] C21 =  sumDACMat(matrixProduct_DAC(A, B, startARow + midpoint, startACol, startBRow, startBCol, midpoint), 
                matrixProduct_DAC(A, B, startARow + midpoint, startACol + midpoint, startBRow + midpoint, startBCol, midpoint));

            /* C22 = A21 * B12 + A22 * B22 */
            int[][] C22 =  sumDACMat(matrixProduct_DAC(A, B, startARow + midpoint, startACol, startBRow, startBCol + midpoint, midpoint), 
                matrixProduct_DAC(A, B, startARow + midpoint, startACol + midpoint, startBRow + midpoint, startBCol + midpoint, midpoint));

            /* filling the 4 quadrants into the C matrix */
            /*  Fill C with first quadrant */
            for (int i = 0; i < midpoint; i++){
                for (int j = 0; j < midpoint; j++){
                    C[i][j] = C11[i][j];
                }
            }
            /* Fill C with second quadrant */
            for (int i = 0; i < midpoint; i++){
                for (int j = 0; j < midpoint; j++){
                    C[i][j + midpoint] = C12[i][j];
                }
            }
            /*  Fill C with third quadrant */
            for (int i = 0; i < midpoint; i++){
                for (int j = 0; j < midpoint; j++){
                    C[i + midpoint][j] = C21[i][j];
                }
            }
            /*  Fill C with fourth quadrant */
            for (int i = 0; i < midpoint; i++){
                for (int j = 0; j < midpoint; j++){
                    C[i + midpoint][j + midpoint] = C22[i][j];
                }
            }


        }


        return C;
    }

    /* Function used to create eac C quadrant matrix */
    private static int[][] sumDACMat(int[][] mat1, int[][] mat2){
        int[][] sumMatrix = new int[mat1.length][mat1.length];
        for(int i = 0; i < mat1.length; i++){
            for(int j = 0; j < mat1.length; j++){
                sumMatrix[i][j] = mat1[i][j] + mat2[i][j];
            }
        }
        return sumMatrix;
    }

    /* Will compute and return the product of A & B using the Strassen Algorithm */
    public static int[][] matrixProduct_Strassen(int[][] A, int[][] B){

        int aRows = A.length;
        int aCols = A[0].length;
        int bRows = B.length;
        int bCols = B[0].length;
        int n = A.length;

        if((aRows != bRows) || (aCols != bCols) || (n == 0) || (n%2 ==1)){                /* Checks if nxn matrix */
            throw new IllegalArgumentException("Either matrix A or matrix B is not a square matrix that is a power of 2.");
        }
        else{
            int[][] C = matrixProduct_Strassen(A, 0, 0, B, 0, 0, A.length);
            return C;
        }
    }

    public static int[][] matrixProduct_Strassen(int[][] A,  int startARow, int startACol, int[][] B, int startBRow, int startBCol, int n){
        
        int i = 0;
        int y = 0;
        int[][] C = new int[n][n];
        if(n == 1)
            C[0][0] = A[startARow][startACol] * B[startBRow][startBCol];
        else {
            int midpoint = n/2;
            int [][] s1 = matrixArithmetic(B, startBRow, startBCol + midpoint, B, startBRow + midpoint, startBCol + midpoint, midpoint, 1);         /* B12 - B22 */
            int [][] s2 = matrixArithmetic(A, startARow, startACol, A, startARow, startACol + midpoint, midpoint, 0);                               /* A11 + A12 */
            int [][] s3 = matrixArithmetic(A, startARow + midpoint, startACol, A, startARow + midpoint, startACol + midpoint, midpoint, 0);         /* A21 + A22 */
            int [][] s4 = matrixArithmetic(B, startBRow + midpoint, startBCol, B, startBRow, startBCol, midpoint, 1);                               /* B21 - B11 */ 
            int [][] s5 = matrixArithmetic(A, startARow, startACol, A, startARow + midpoint, startACol + midpoint, midpoint, 0);                    /* A11 + A22 */
            int [][] s6 = matrixArithmetic(B, startBRow, startBCol, B, startBRow + midpoint, startBCol + midpoint, midpoint, 0);                    /* B11 + B22 */
            int [][] s7 = matrixArithmetic(A, startARow, startACol + midpoint, A, startARow + midpoint, startACol + midpoint, midpoint, 1);         /* A12 - A22 */
            int [][] s8 = matrixArithmetic(B, startBRow + midpoint, startBCol, B, startBRow + midpoint, startBCol + midpoint, midpoint, 0);         /* B21 + B22 */
            int [][] s9 = matrixArithmetic(A, startARow, startACol, A, startARow + midpoint, startACol, midpoint, 1);                               /* A11 - A21 */
            int [][] s10 = matrixArithmetic(B, startBRow, startBCol, B, startBRow, startBCol + midpoint, midpoint, 0);                              /* B11 + B12 */
            int [][] p1 = matrixProduct_Strassen(A, startARow, startACol, s1, 0, 0, midpoint);                                      /* A11 * S1 */
            int [][] p2 = matrixProduct_Strassen(s2, 0, 0, B, startBRow + midpoint, startBCol + midpoint, midpoint);                /* S2 * B22 */
            int [][] p3 = matrixProduct_Strassen(s3, 0, 0, B, startBRow, startBCol, midpoint);                                      /* S3 * B11 */
            int [][] p4 = matrixProduct_Strassen(A, startARow + midpoint, startACol + midpoint, s4, 0, 0, midpoint);                /* A22 * S4 */
            int [][] p5 = matrixProduct_Strassen(s5, 0, 0, s6, 0, 0, midpoint);                                /* S5 * S6 */
            int [][] p6 = matrixProduct_Strassen(s7, 0, 0, s8, 0, 0, midpoint);                                /* S7 * S8 */
            int [][] p7 = matrixProduct_Strassen(s9, 0, 0, s10, 0, 0, midpoint);                               /* S9 * s10*/
            /*C11 = P5 +P4 - P2 + P6 */
            int [][] C11 = matrixArithmetic(matrixArithmetic(matrixArithmetic(p5, 0, 0, p4, 0, 0, midpoint, 0), 0, 0, p2, 0, 0, midpoint, 1), 0, 0, p6, 0, 0, midpoint, 0);
            int [][] C12 = matrixArithmetic(p1, 0, 0, p2, 0, 0, midpoint, 0);
            int [][] C21 = matrixArithmetic(p3, 0, 0, p4, 0, 0, midpoint, 0);
            /*C22 = P5 + P1 - P3 - P7 */
            int [][] C22 = matrixArithmetic(matrixArithmetic(matrixArithmetic(p5, 0, 0, p1, 0, 0, midpoint, 0), 0, 0, p3, 0, 0, midpoint, 1), 0, 0, p7, 0, 0, midpoint, 1);

            for(i = 0; i < midpoint; i++) {
                for(y = 0; y < midpoint; y++) {
                    C[i][y] = C11[i][y];
                }
            }
            for(i = 0; i < midpoint; i++) {
                for(y = 0; y < midpoint; y++) {
                    C[i][y+midpoint] = C12[i][y];
                }
            }
            for(i = 0; i < midpoint; i++) {
                for(y = 0; y < midpoint; y++) {
                    C[i+midpoint][y] = C21[i][y];
                }
            }
            for(i = 0; i < midpoint; i++) {
                for(y = 0; y < midpoint; y++) {
                    C[i+midpoint][y+midpoint] = C22[i][y];
                }
            }
        }
        return C;
    }

    private static int[][] matrixArithmetic(int[][] A, int startARow, int startACol, int[][] B, int startBRow, int startBCol, int n, int flag) {
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (flag == 1)
                    C[i][j] = A[i + startARow][j + startACol] - B[i + startBRow][j + startBCol];
                else
                    C[i][j] = A[i + startARow][j + startACol] + B[i + startBRow][j + startBCol];
            }
        }

        return C;
    }
    /* We commented out our main becaue we were told the grader would be running their own, please uncomment if needed */

    // public static void main(String[] args) throws FileNotFoundException {
 
    //     Scanner scan = new Scanner(System.in);
    //     System.out.println("Enter filename to test");
    //     String fileName = scan.next();
    //     File file = new File(fileName);
    //     Scanner inputFile = new Scanner(file);
  
  
    //     /*Get Matrix size */
    //     int rowsA = inputFile.nextInt();            /* gets rows of matrix*/
    //     int colsA = inputFile.nextInt();            /* gets cols of matrix */
    //     inputFile.nextLine();
  
    //     int[][] tempA = new int[rowsA][colsA];
  
  
    //     for( int r = 0; r < rowsA; r++){            /* rows */
    //         for (int c = 0; c < colsA; c++){        /* cols */
    //             tempA[r][c] = inputFile.nextInt();
    //         }
    //     }
  
  
    //     int rowsB = inputFile.nextInt();            /* gets rows of matrix*/
    //     int colsB = inputFile.nextInt();            /* gets cols of matrix */
    //     int[][] tempB = new int[rowsB][colsB];
    //     for(int r = 0; r < rowsB; r++){             /* rows */
    //         for(int c = 0; c < colsB; c++){         /* cols */
    //             tempB[r][c] = inputFile.nextInt();
    //         }
    //     }
  
    //     int[][] A = tempA;
    //     int[][] B = tempB;
       
    //     try {
    //     int[][] Cd = matrixProduct_DAC(A, B);
    //     int[][] Cs = matrixProduct_Strassen(A, B);
  
    //     /* Print out Cd matrix */
    //     System.out.println("Matrix Product for the simple Divide and Conquer Algorithm");
    //     for(int i = 0; i < Cd.length; i++){
    //         for(int j = 0; j < Cd[0].length; j++){
    //             System.out.print(Cd[i][j] + " ");
    //         }
    //         System.out.println();
    //     }
    //     System.out.println("Matrix Product for the Strassen Algorithm");
    //     for(int i = 0; i < Cs.length; i++){
    //         for(int j = 0; j < Cs[0].length; j++){
    //             System.out.print(Cs[i][j] + " ");
    //         }
    //         System.out.println();
    //     }

    //     }
    //     catch (IllegalArgumentException e)
    //     {
    //         System.out.println(e.getMessage());
    //     }
  
    // }
}
