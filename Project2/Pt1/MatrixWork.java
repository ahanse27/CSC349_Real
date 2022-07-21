package Project2.Pt1;

 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
 
/*
* Names:
* Class: CPE 349
* Assignment: Project 2 Pt 1
* Date: 06/29/2022
*/
 
public class MatrixWork{
 
   /*
    * Prompt the user to enter the input-file’s name
    * then input the file-name and set up a scanner.
    *
    * create matrices A and B, fill with numbers from input file
    * All integer numbers will be separated by whitespaces
    * first two numbers indicate size of A (rows & columns)
    * values of A matrix follow (row after row, listing elements from left to right)
    * the next two numbers will indicate the size of the matrix B
    * then values of B matrix will follow (row after row, listing elements from left to right)
    *
    * Call matrixProduct method pass in matrices A and B as parameters
    * save this into a 2-dimen array (matrix C)
    * will have to throw an exception and output error message - DO NOT CRASH
    *
    * Output matrix C on the screen in correct format
    * one line per matrix-row, separating values on the same row with space(s)
    */
   public static void main(String[] args) throws FileNotFoundException {
 
       Scanner scan = new Scanner(System.in);
       System.out.println("Enter filename to test");
       String fileName = scan.next();
       File file = new File(fileName);
       Scanner inputFile = new Scanner(file);
 
 
       /*Get Matrix size */
       int rowsA = inputFile.nextInt();      /* gets rows of matrix*/
       int colsA = inputFile.nextInt();      /* gets cols of matrix */
       inputFile.nextLine();
 
       int[][] tempA = new int[rowsA][colsA];
 
 
       for( int r = 0; r < rowsA; r++){             /* rows */
           for (int c = 0; c < colsA; c++){    /* cols */
               tempA[r][c] = inputFile.nextInt();
           }
       }
 
 
       int rowsB = inputFile.nextInt();      /* gets rows of matrix*/
       int colsB = inputFile.nextInt();      /* gets cols of matrix */
       int[][] tempB = new int[rowsB][colsB];
       for( int r = 0; r < rowsB; r++){             /* rows */
           for (int c = 0; c < colsB; c++){    /* cols */
               tempB[r][c] = inputFile.nextInt();
           }
       }
 
       int[][] A = tempA;
       int[][] B = tempB;
      
       try {
       int[][] C = matrixProduct(A, B);
 
       /* Print out C matrix */
       System.out.println("Matrix Product");
       for (int i = 0; i < C.length; i++) {
           for (int j = 0; j < C[0].length; j++)
               System.out.print(C[i][j] + " ");
           System.out.println();
       }
       }
       catch (IllegalArgumentException e)
       {
           System.out.println(e.getMessage());
       }
 
   }
 
   /*
    * multiply given A and B matrices of n×k and k×m sizes
    * return the result-matrix C of size n×m (i.e. return C where C = A·B)
    * assume A and B are valid arrays with > 0 number of rows and columns.
    *
    * C has as many rows as A, and as many columns as B. so --> C array = [rowsA][colB]
    * to multiply A and B matrices, the number of columns in A must be equal to the number of rows in B.
    * if colA != rowB then throw an "IllegalArgumentException"
    *
    * the number of rows = "arr.length"
    * the number of columns = "arr[0].length"
    */
   public static int[][] matrixProduct (int[][] A, int[][] B){
 
 
       /* C has A.rows and B.cols */
       int aRows = A.length;
       int aCols = A[0].length;
       int bRows = B.length;
       int bCols = B[0].length;
       int[][] C = new int[aRows][bCols];
       int i = 0, j = 0, k = 0;
 
       /* check if A.cols is eqauls to B.rows */
       if(aCols != bRows){
           throw new IllegalArgumentException("Columns of A != to Rows of B");
       }
       else{
           for(i = 0; i < aRows; i++){                  /* A row */
               for(j = 0; j < bCols; j++){               /* B column */
                   for (k = 0; k < aCols; k++) {          /* A column */
                       C[i][j] += A[i][k] * B[k][j];
                   }
               }
           }
       }
       return C;
   }
 
}
