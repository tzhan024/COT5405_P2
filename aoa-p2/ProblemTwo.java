import java.text.spi.CollatorProvider;
import java.util.*;

public class ProblemTwo {
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
            for(int j = 0; j < n; j++)
            {
                matrix[i][j] = Integer.valueOf(line[j]);
            }
        }

        in.close();

        // run corresponding strategy with different command.
        if(args[0].equals("4"))
        {
            task4(matrix, h);
        }
        else if(args[0].equals("5a"))
        {
            task5a(matrix, h);
        }
        else if(args[0].equals("5b"))
        {
            task5b(matrix, h);
        }
    }

    public static void task4(int[][] matrix, int h)
    {
        int[][] colDP = new int[matrix.length][matrix[0].length];
        int[][] squareDP = new int[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] >= h)
                {
                    if(i - 1 < 0 || j - 1 < 0)
                    {
                        squareDP[i][j] = 1;
                    }
                    else
                    {
                        squareDP[i][j] = Math.min(squareDP[i - 1][j - 1], Math.min(squareDP[i - 1][j], squareDP[i][j - 1])) + 1;
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

        int maxSide = 2;
        int maxI = 1;
        int maxJ = 1;

        for(int i = 2; i < matrix.length; i++)
        {
            for(int j = 2; j < matrix[i].length; j++)
            {
                int bottomRow = 0;
                int bottomJ = j - 1;
                while(bottomJ >= 0 && matrix[i][bottomJ] >= h)
                {
                    bottomRow++;
                    bottomJ--;
                }

                int side = Math.min(colDP[i - 1][j], bottomRow);
                int sqLength = squareDP[i - 1][j - 1];

                int finalSide = 0;

                if(side < sqLength)
                {
                    finalSide = side + 2;
                }
                else
                {
                    int topRow = 0;
                    int topJ = j - 1;
                    while(i - 1 - sqLength >= 0 && topJ >= 0 && matrix[i - 1 - sqLength][topJ] >= h)
                    {
                        topRow++;
                        topJ--;
                    }

                    if(i - 1 - sqLength >= 0 && j - 1 - sqLength >= 0 && topRow >= sqLength && colDP[i - 1][j - 1 - sqLength] >= sqLength)
                    {
                        finalSide = sqLength + 2;
                    }
                    else
                    {
                        finalSide = sqLength + 1;
                    }
                }

                if(finalSide > maxSide)
                {
                    maxSide = finalSide;
                    maxI = i + 1;
                    maxJ = j + 1;
                }
                
            }
        }

        System.out.println("The answer indexes are: ");
        System.out.println((maxI - maxSide + 1) + " " + (maxJ - maxSide + 1) + " " + maxI + " " + maxJ);
    }

    public static void task5a(int[][] matrix, int h)
    {
        int[][] rowDP = new int[matrix.length][matrix[0].length];
        int[][] colDP = new int[matrix.length][matrix[0].length];
        int[][] squareDP = new int[matrix.length][matrix[0].length];

        for(int[] arr : squareDP)
        {
            Arrays.fill(arr, -1);
        }
        getSquareMax(matrix.length - 1, matrix[0].length - 1, squareDP, matrix, h);

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

        int[][][] memo = new int[matrix.length][matrix[0].length][3];
        for(int[][] arr : memo)
        {
            for(int i = 0; i < arr.length; i++)
            {
                arr[i][0] = -1;
            }
        }

        int[] ans =  getMax(matrix.length - 1, matrix[0].length - 1, memo, matrix, h, rowDP, colDP, squareDP);

        System.out.println("The answer indexes are: ");
        System.out.println((ans[1] - ans[0] + 1) + " " + (ans[2] - ans[0] + 1) + " " + ans[1] + " " + ans[2]);
        
    }
    public static int getSquareMax(int i, int j, int[][] squareDP, int[][] matrix, int h)
    {
        if(i == 0 || j == 0)
        {
            if(squareDP[i][j] == -1)
            {
                squareDP[i][j] = matrix[i][j] >= h ? 1 : 0;
            }
            return squareDP[i][j];
        }
        if(squareDP[i][j] != -1)
        {
            return squareDP[i][j];
        }
        squareDP[i][j] = Math.min(getSquareMax(i - 1, j - 1, squareDP, matrix, h), Math.min(getSquareMax(i - 1, j, squareDP, matrix, h), getSquareMax(i, j - 1, squareDP, matrix, h))) + 1;
        return squareDP[i][j];
    }
    public static int[] getMax(int i, int j, int[][][] memo, int[][] matrix, int h, int[][] rowDP, int[][] colDP, int[][] squareDP)
    {
        

        if(i == 0 || j == 0)
        {
            if(memo[i][j][0] == -1)
            {
                memo[i][j][0] = 1;
                memo[i][j][1] = i + 1;
                memo[i][j][2] = j + 1;
            }
            return memo[i][j];
        }

        if(memo[i][j][0] != -1)
        {
            return memo[i][j];
        }

        
        int side = Math.min(colDP[i - 1][j], rowDP[i][j - 1]);
        int sqLength = squareDP[i - 1][j - 1];

        int finalSide = 0;

        if(side < sqLength)
        {
            finalSide = side + 2;
        }
        else
        {
            if(i - 1 - sqLength >= 0 && j - 1 - sqLength >= 0 && rowDP[i - 1 - sqLength][j - 1] >= sqLength && colDP[i - 1][j - 1 - sqLength] >= sqLength)
            {
                finalSide = sqLength + 2;
            }
            else
            {
                finalSide = sqLength + 1;
            }
        }

        memo[i][j][0] = finalSide;
        memo[i][j][1] = i + 1;
        memo[i][j][2] = j + 1;
            
        int[] topMax = getMax(i - 1, j, memo, matrix, h, rowDP, colDP, squareDP);
        int[] leftMax = getMax(i, j - 1, memo, matrix, h, rowDP, colDP, squareDP);
        int[] cornerMax = getMax(i - 1, j - 1, memo, matrix, h, rowDP, colDP, squareDP);
            
        if(topMax[0] > memo[i][j][0])
        {
            memo[i][j][0] = topMax[0];
            memo[i][j][1] = topMax[1];
            memo[i][j][2] = topMax[2];
        }
        if(leftMax[0] > memo[i][j][0])
        {
            memo[i][j][0] = leftMax[0];
            memo[i][j][1] = leftMax[1];
            memo[i][j][2] = leftMax[2];
        }
        if(cornerMax[0] > memo[i][j][0])
        {
            memo[i][j][0] = cornerMax[0];
            memo[i][j][1] = cornerMax[1];
            memo[i][j][2] = cornerMax[2];
        }
        
        
        return memo[i][j];
    }






    public static void task5b(int[][] matrix, int h)
    {
        int[][] rowDP = new int[matrix.length][matrix[0].length];
        int[][] colDP = new int[matrix.length][matrix[0].length];
        int[][] squareDP = new int[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] >= h)
                {
                    if(i - 1 < 0 || j - 1 < 0)
                    {
                        squareDP[i][j] = 1;
                    }
                    else
                    {
                        squareDP[i][j] = Math.min(squareDP[i - 1][j - 1], Math.min(squareDP[i - 1][j], squareDP[i][j - 1])) + 1;
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

        int maxSide = 2;
        int maxI = 1;
        int maxJ = 1;

        for(int i = 2; i < matrix.length; i++)
        {
            for(int j = 2; j < matrix[i].length; j++)
            {
                int side = Math.min(colDP[i - 1][j], rowDP[i][j - 1]);
                int sqLength = squareDP[i - 1][j - 1];

                int finalSide = 0;

                if(side < sqLength)
                {
                    finalSide = side + 2;
                }
                else
                {
                    if(i - 1 - sqLength >= 0 && j - 1 - sqLength >= 0 && rowDP[i - 1 - sqLength][j - 1] >= sqLength && colDP[i - 1][j - 1 - sqLength] >= sqLength)
                    {
                        finalSide = sqLength + 2;
                    }
                    else
                    {
                        finalSide = sqLength + 1;
                    }
                }

                if(finalSide > maxSide)
                {
                    maxSide = finalSide;
                    maxI = i + 1;
                    maxJ = j + 1;
                }
                
            }
        }

        System.out.println("The answer indexes are: ");
        System.out.println((maxI - maxSide + 1) + " " + (maxJ - maxSide + 1) + " " + maxI + " " + maxJ);
    }
}
