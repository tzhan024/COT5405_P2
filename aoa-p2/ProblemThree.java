import java.util.*;

public class ProblemThree {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        // get input for n and m.
        System.out.println("Please enter m, n, h, and k separated by a single space: ");
        String s = in.nextLine();
        String[] arr = s.split(" ");
        int m = Integer.valueOf(arr[0]);
        int n = Integer.valueOf(arr[1]);
        int h = Integer.valueOf(arr[2]);
        int k = Integer.valueOf(arr[3]);
        
        int[][] matrix = new int[m][n];

        for(int i = 0; i < m; i++)
        {
            // get input for each house.
            System.out.println("Please enter the  " + i + "th line separated by a single space: ");
            String[] line = in.nextLine().split(" ");
            for(int j = 0; j < n; j++)
            {
                matrix[i][j] = Integer.valueOf(line[j]);
            }
        }

        in.close();

        // run corresponding strategy with different command.
        if(args[0].equals("6"))
        {
            task6(matrix, h, k);
        }
        else if(args[0].equals("7a"))
        {
            task7a(matrix, h, k);
        }
        else if(args[0].equals("7b"))
        {
            task7b(matrix, h, k);
        }
        
    }

    public static void task6(int[][] matrix, int h, int k)
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
                        if(isValid(matrix, x, y, i, j, h, k))
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
    public static boolean isValid(int[][] matrix, int startI, int startJ, int endI, int endJ, int h, int k)
    {
        if(endI - startI != endJ - startJ)
            return false;
        
        int count = 0;
        for(int i = startI; i <= endI; i++)
        {
            for(int j = startJ; j <= endJ; j++)
            {
                if(matrix[i][j] < h)
                    count++;
            }
        }
        return count > k ? false : true;
    }

    public static void task7a(int[][] matrix, int h, int k)
    {

    }

    public static void task7b(int[][] matrix, int h, int k)
    {
        int[][] rowDP = new int[matrix.length][matrix[0].length];
        int[][] colDP = new int[matrix.length][matrix[0].length];
        int[][][] squareDP = new int[matrix.length][matrix[0].length][k + 1];

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                
                if(i == 0)
                {
                    colDP[i][j] = matrix[i][j] < h ? 1 : 0;
                }
                else
                {
                    colDP[i][j] = colDP[i - 1][j] + (matrix[i][j] < h ? 1 : 0);
                }
            }
        }

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                
                if(j == 0)
                {
                    rowDP[i][j] = matrix[i][j] < h ? 1 : 0;
                }
                else
                {
                    rowDP[i][j] = rowDP[i - 1][j] + (matrix[i][j] < h ? 1 : 0);
                }
            }
        }

        int initLength = (int)Math.floor(Math.sqrt(k));

        int maxSide = (int)Math.floor(Math.sqrt(k));
        // int maxI = initLength;
        // int maxJ = initLength;

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                Arrays.fill(squareDP[i][j], -1);
                if(i == 0 || j == 0)
                {
                    if(matrix[i][j] < h && k > 0)
                    {
                        squareDP[i][j][1] = 1;
                    }
                    else
                    {
                        squareDP[i][j][0] = 1;
                    }
                }
                else
                {
                    for(int x = 0; i <= k; i++)
                    {

                    }
                }
            }
        }


    }
}
