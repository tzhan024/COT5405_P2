import java.util.*;
import java.io.*;
import java.lang.*;

public class TestProblemOne {
    public static void main(String[] args)
    {
        String[] files = new String[] {"p1+given_case1.txt", "p1+given_case2.txt", "p1p2+100+100.txt", "p1p2+200+200.txt", "p1p2+300+300.txt", "p1p2+400+400.txt", "p1p2+500+500.txt"};

        for(String f : files)
        {
            System.out.println(f + ": ");
            task1(f);
            task2(f);
            task3(f);

            System.out.println();
        }

        
    }

    public static void task1(String fileName)
    {
        int[][] matrix = new int[0][0];
        int h = 0;
        try
        {
            File inFile = new File(fileName);
            Scanner fileReader = new Scanner(inFile);

            String[] arr = fileReader.nextLine().split(" ");
            int m = Integer.valueOf(arr[0]);
            int n = Integer.valueOf(arr[1]);

            matrix = new int[m][n];
            h = Integer.valueOf(arr[2]);

            for(int i = 0; i < m; i++)
            {
                String[] row = fileReader.nextLine().split(" ");
                for(int j = 0; j < n; j++)
                {
                    matrix[i][j] = Integer.valueOf(row[j]);
                }
            }

            fileReader.close();
            // in.close();

        } 
        catch(Exception e){
            e.printStackTrace();
        } 

        long startTime = System.nanoTime();

        int maxI = 0;
        int maxJ = 0;
        int maxX = 0;
        int maxY = 0;
        int maxSide = 0;

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                for(int x = 0; x <= i; x++)
                {
                    for(int y = 0; y <= j; y++)
                    {
                        if(isValid1(matrix, x, y, i, j, h))
                        {
                            int len = i - x + 1;
                            if(len > maxSide)
                            {
                                maxSide = len;
                                maxI = i + 1;
                                maxJ = j + 1;
                                maxX = x + 1;
                                maxY = y + 1;
                            }
                        }
                    }
                }
                
            }
        }

        long endTime = System.nanoTime();
        System.out.println("task1: " + maxX + " " + maxY + " " + maxI + " " + maxJ + " | runtime: " + (endTime - startTime));
    }
    public static boolean isValid1(int[][] matrix, int startI, int startJ, int endI, int endJ, int h)
    {
        for(int i = startI; i <= endI; i++)
        {
            for(int j = startJ; j <= endJ; j++)
            {
                if(matrix[i][j] < h)
                    return false;
            }
        }
        if(endI - startI != endJ - startJ)
            return false;
        return true;
    }
    

    public static void task2(String fileName)
    {
        int[][] matrix = new int[0][0];
        int h = 0;
        try
        {
            File inFile = new File(fileName);
            Scanner fileReader = new Scanner(inFile);

            String[] arr = fileReader.nextLine().split(" ");
            int m = Integer.valueOf(arr[0]);
            int n = Integer.valueOf(arr[1]);

            matrix = new int[m][n];
            h = Integer.valueOf(arr[2]);

            for(int i = 0; i < m; i++)
            {
                String[] row = fileReader.nextLine().split(" ");
                for(int j = 0; j < n; j++)
                {
                    matrix[i][j] = Integer.valueOf(row[j]);
                }
            }

            fileReader.close();
            // in.close();

        } 
        catch(Exception e){
            e.printStackTrace();
            return;
        } 



        int[][] rowDP = new int[matrix.length][matrix[0].length];
        int[][] colDP = new int[matrix.length][matrix[0].length];
        int[][] squareDP = new int[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++)
        {
            squareDP[i][0] = matrix[i][0] >= h ? 1 : 0;
        }
        for(int i = 1; i < matrix[0].length; i++)
        {
            squareDP[0][i] = matrix[0][i] >= h ? 1 : 0;
        }

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] >= h)
                {
                    if(i == 0)
                    {
                        colDP[i][j] = 1;
                    }
                    else
                    {
                        colDP[i][j] = colDP[i - 1][j] + 1;
                    }
                }
                
            }
        }

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] >= h)
                {
                    if(j == 0)
                    {
                        rowDP[i][j] = 1;
                    }
                    else
                    {
                        rowDP[i][j] = rowDP[i][j - 1] + 1;
                    }
                }
                
            }
        }

        long startTime = System.nanoTime();

        int maxI = 0;
        int maxJ = 0;
        int maxX = 0;
        int maxY = 0;
        int maxSide = 0;
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                for(int x = 0; x <= i; x++)
                {
                    for(int y = 0; y <= j; y++)
                    {
                        if(isValid2(colDP, rowDP, squareDP, x, y, i, j, h))
                        {
                            int len = i - x + 1;
                            squareDP[i][j] = Math.max(squareDP[i][j], len);
                            if(len > maxSide)
                            {
                                maxSide = len;
                                maxI = i + 1;
                                maxJ = j + 1;
                                maxX = x + 1;
                                maxY = y + 1;
                            }
                        }
                    }
                }
                
            }
        }

        long endTime = System.nanoTime();
        System.out.println("task2: " + maxX + " " + maxY + " " + maxI + " " + maxJ + " | runtime: " + (endTime - startTime));
    }
    public static boolean isValid2(int[][] rowDP, int[][] colDP, int[][] squareDP, int startI, int startJ, int endI, int endJ, int h)
    {
        if(endI - startI != endJ - startJ)
            return false;
        int sideLength = endI - startI + 1;

        if(rowDP[endI][endJ] < sideLength || colDP[endI][endJ] < sideLength)
        {
            return false;
        }
        if(endI > 0 && endJ > 0 && squareDP[endI - 1][endJ - 1] < sideLength - 1)
        {
            return false;
        }

        
        return true;
    }

    public static void task3(String fileName)
    {
        int[][] matrix = new int[0][0];
        int h = 0;
        try
        {
            File inFile = new File(fileName);
            Scanner fileReader = new Scanner(inFile);

            String[] arr = fileReader.nextLine().split(" ");
            int m = Integer.valueOf(arr[0]);
            int n = Integer.valueOf(arr[1]);

            matrix = new int[m][n];
            h = Integer.valueOf(arr[2]);

            for(int i = 0; i < m; i++)
            {
                String[] row = fileReader.nextLine().split(" ");
                for(int j = 0; j < n; j++)
                {
                    matrix[i][j] = Integer.valueOf(row[j]);
                }
            }

            fileReader.close();
            // in.close();

        } 
        catch(Exception e){
            e.printStackTrace();
        } 


        long startTime = System.nanoTime();

        int[][] dp = new int[matrix.length][matrix[0].length];
        int maxI = 0;
        int maxJ = 0;
        int maxSide = 0;
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] >= h)
                {
                    if(i - 1 < 0 || j - 1 < 0)
                    {
                        dp[i][j] = 1;
                    }
                    else
                    {
                        dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;

                        
                    }

                    if(dp[i][j] > maxSide)
                    {
                        maxSide = dp[i][j];
                        maxI = i + 1;
                        maxJ = j + 1;
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        System.out.println("task3: " + (maxI - maxSide + 1) + " " + (maxJ - maxSide + 1) + " " + maxI + " " + maxJ + " | runtime: " + (endTime - startTime));
    }
}
