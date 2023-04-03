import java.util.Arrays;
import java.util.Scanner;

public class TestProblemThree {

  public static void main(String[] args) {
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

    for (int i = 0; i < m; i++) {
      // get input for each house.
      System.out.println("Please enter the  " + i + "th line separated by a single space: ");
      String[] line = in.nextLine().split(" ");
      for (int j = 0; j < n; j++) {
        matrix[i][j] = Integer.valueOf(line[j]);
      }
    }

    in.close();

    // run corresponding strategy with different command.
    if (args[0].equals("6")) {
      task6(matrix, h, k);
    } else if (args[0].equals("7a")) {
      task7a(matrix, h, k);
    } else if (args[0].equals("7b")) {
      task7b(matrix, h, k);
    }

  }

  public static void task6(int[][] matrix, int h, int k) {
    int maxI = 0;
    int maxJ = 0;
    int maxX = 0;
    int maxY = 0;
    int maxSide = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        for (int x = 0; x <= i; x++) {
          for (int y = 0; y <= j; y++) {
            if (isValid(matrix, x, y, i, j, h, k)) {
              int len = i - x + 1;
              if (len > maxSide) {
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

  public static boolean isValid(int[][] matrix, int startI, int startJ, int endI, int endJ, int h,
      int k) {
      if (endI - startI != endJ - startJ) {
          return false;
      }

    int count = 0;
    for (int i = startI; i <= endI; i++) {
      for (int j = startJ; j <= endJ; j++) {
          if (matrix[i][j] < h) {
              count++;
          }
      }
    }
    return count <= k;
  }

  public static void task7a(int[][] matrix, int h, int k) {
    int m = matrix.length;
    int n = matrix[0].length;
    int[][][] memo = new int[m][n][k + 1];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        Arrays.fill(memo[i][j], -1);
      }
    }
    int[] result = new int[4];
    findEnclosedPlots(matrix, memo, m, n, h, k, 0, 0, result);
    System.out.print("Task7a answer: ");
    System.out.println(result[0] + " " + result[1] + " " + result[2] + " " + result[3]);
  }

  public static int findEnclosedPlots(
      int[][] p, int[][][] memo, int m, int n, int h, int k, int i, int j, int[] result) {
    if (i >= m || j >= n) {
      return 0;
    }
    if (memo[i][j][k] != -1) {
      return memo[i][j][k];
    }
    int maxSide = 0;
    int minTrees = Integer.MAX_VALUE;
    int enclosedPlots = 0;
    for (int x = i; x < m; x++) {
      for (int y = j; y < n; y++) {
        if (p[x][y] < h) {
          enclosedPlots++;
        }
        if (enclosedPlots <= k) {
          int len = i - x + 1;
          if (len > maxSide) {
            maxSide = len;
            result[0] = i + 1;
            result[1] = j + 1;
            result[2] = x + 1;
            result[3] = y + 1;
          }
          int area = (x - i + 1) * (y - j + 1);
          if (area < minTrees) {
            minTrees = area;
          }
          int remainingPlots =
              findEnclosedPlots(p, memo, m, n, h, k - enclosedPlots, x + 1, j, result);
          minTrees = Math.min(minTrees, area + remainingPlots);
        }
      }
    }
    memo[i][j][k] = minTrees;
    return minTrees;
  }

  public static void task7b(int[][] matrix, int h, int k) {
    int m = matrix.length;
    int n = matrix[0].length;
    int[][][] dp = new int[m + 1][n + 1][k + 1];
    int[] result = new int[4];
    int max = Integer.MIN_VALUE;

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        for (int l = 1; l <= k; l++) {
          if (matrix[i - 1][j - 1] < h) {
            dp[i][j][1] = 1;
          } else {
            dp[i][j][1] = 0;
          }
          if (l > 1) {
            dp[i][j][l] =
                Math.min(
                    dp[i - 1][j][l - 1], Math.min(dp[i][j - 1][l - 1], dp[i - 1][j - 1][l - 1]))
                    + 1;
          }
          if (dp[i][j][l] > 0 && l >= max) {
            result[0] = i - l + 1;
            result[1] = j - l + 1;
            result[2] = i;
            result[3] = j;
            max = l;
          }
        }
      }
    }

    System.out.print("Task7b answer: ");
    System.out.println(result[0] + " " + result[1] + " " + result[2] + " " + result[3]);
  }
}
