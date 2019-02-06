package client;

import java.util.EmptyStackException;
import java.util.Stack;

public class Sudoku {
    
   public static int [][] matrix =    {
                                {5,0,0,0,2,0,0,0,0},
                                {0,0,0,0,0,0,3,9,0},
                                {2,0,8,4,0,6,0,0,0},
                                {0,9,0,5,3,0,0,0,0},
                                {0,6,5,0,0,0,0,0,8},
                                {0,8,0,0,0,0,9,0,3},
                                {6,0,4,0,0,0,7,0,0},
                                {0,0,0,0,0,0,2,1,0},
                                {0,0,0,2,7,4,0,0,0}
                                };
    
    public static Stack <Integer> myStack = new Stack <Integer>();
 
    public static void main (String[] args) {
    System.out.println("                Sudoku");
    ausgeben();
    
    long lngStartTime = System.currentTimeMillis();
    solve(matrix);
    long lngEndTime = System.currentTimeMillis();
    System.out.println("Benoetigte Loesungszeit des Solvers: \n" +(lngEndTime-lngStartTime)+" ms");
    
    ausgeben ();
    System.exit(0);
    }
    
    public static int possibleAssignment (int iY, int iX, int iOffset) {
      for (int iPossibility = iOffset+1; iPossibility <= 9; iPossibility++)
        if (zeilentest (matrix,iY,iPossibility) &&
        spaltentest (matrix,iX,iPossibility) &&
        blocktest (matrix,iX,iY,iPossibility)
        )
        return iPossibility;
      return -1;
    }

    public static void solve(int [][] matrix) {
      int iBacktracks = 0;
      for(int y=0; y<9; y++) 
        for(int x=0; x<9; x++) {
          if(matrix[y][x] == 0) {
          int iAss = possibleAssignment(y,x,0);
          while( iAss== -1){
            iBacktracks++;
            try{
              int iLast = (int)myStack.pop();
              int iLastX = iLast%9;
              int iLastY = iLast/9;
              int iOffset = matrix[iLastY][iLastX];
              iAss = possibleAssignment(iLastY,iLastX,iOffset);
              matrix[iLastY][iLastX] = 0;
              x=iLastX;
              y=iLastY;
            }         
            catch (EmptyStackException e){
              System.out.println("Ungueltiges Sudoku! Das Programm wird beendet.");
              System.exit(0);
            }
          }
        matrix[y][x] = iAss;
        myStack.push(y*9+x);
        }
      }
     System.out.println("Anzahl notwendiger Backtracks: \nl" + iBacktracks);
    }

    public static boolean zeilentest (int [][] matrix, int iZeile, int iZahl) {
      for(int i=0; i<9; i++){
        if (matrix[iZeile][i] == iZahl){
          return false;
        }    
      }        
      return true;
    }

    public static boolean spaltentest (int [][] matrix, int iSpalte, int iZahl) {
      for(int i=0; i<9; i++) {
        if (matrix[i][iSpalte] == iZahl){
          return false;
        }
      }
      return true;
    }

    public static boolean blocktest (int [][] matrix,int iSpalte, int iZeile, int iZahl) {
      int iStartX = (iSpalte / 3) * 3;
      int iStartY = (iZeile / 3) * 3;
        for(int y=0; y<3; y++)
          for(int x=0; x<3; x++){
            if (matrix[iStartY+y][iStartX+x] == iZahl){
            return false;
            }
          }        
          return true;
    }

     public static void ausgeben(){
      for (int iZeile = 0; iZeile<=9; iZeile++) {
        if ((iZeile==0)|(iZeile==9)) System.out.println("#####################################");
        if ((iZeile==3)|(iZeile==6)) System.out.println("#-----------+-----------+-----------#");
        if (iZeile==9) break;
        System.out.print("#  ");
        for (int iSpalte = 0; iSpalte<9; iSpalte++) {
          if ((iSpalte==3)|(iSpalte==6)) System.out.print("|  ");
          if (matrix[iZeile][iSpalte]==0) System.out.print(" ");
            else System.out.print(matrix[iZeile][iSpalte]);
          if (iSpalte == 8) System.out.print("  #");
            else System.out.print("  ");
        }
        System.out.println("");
      }
    }
}