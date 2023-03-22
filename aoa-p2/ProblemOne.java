import java.util.*;

public class ProblemOne {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        // get input for n and m.
        System.out.println("Please enter m, n and h separated by a single space: ");
        String s = in.nextLine();
        String[] arr = s.split(" ");
        int m = Integer.valueOf(arr[0]);
        int n = Integer.valueOf(arr[1]);
        int h = Integer.valueOf(arr[2]);
        
        int[][] matrix = new int[m][n];

        for(int i = 0; i < m; i++)
        {
            // get input for each house.
            System.out.println("Please enter the  " + i + "th line separated by a single space: ");
            String[] line = in.nextLine().split(" ");
            if(line.length != n)
            {
                System.out.println("INVALID LINE!");
                return;
            }
            for(int j = 0; j < n; j++)
            {
                matrix[i][j] = Integer.valueOf(line[j]);
            }
        }

        in.close();

        // run corresponding strategy with different command.
        if(args[0].equals("1"))
        {
            task1(matrix, h);
        }
        else if(args[0].equals("2"))
        {
            task2(matrix, h);
        }
        else if(args[0].equals("3"))
        {
            task3(matrix, h);
        }
    }

    public static void task1(int[][] matrix, int h)
    {
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

        System.out.println("The answer indexes are: ");
        System.out.println(maxX + " " + maxY + " " + maxI + " " + maxJ);
    }
    public static boolean isValid1(int[][] matrix, int startI, int startJ, int endI, int endJ, int h)
    {
        if(endI - startI != endJ - startJ)
            return false;
        for(int i = startI; i <= endI; i++)
        {
            for(int j = startJ; j <= endJ; j++)
            {
                if(matrix[i][j] < h)
                    return false;
            }
        }
        return true;
    }
    

    public static void task2(int[][] matrix, int h)
    {
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

        System.out.println("The answer indexes are: ");
        System.out.println(maxX + " " + maxY + " " + maxI + " " + maxJ);
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

    public static void task3(int[][] matrix, int h)
    {
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

        System.out.println("The answer indexes are: ");
        System.out.println((maxI - maxSide + 1) + " " + (maxJ - maxSide + 1) + " " + maxI + " " + maxJ);
    }
}
