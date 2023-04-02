import java.io.*;
import java.lang.*;
import java.util.*;


public class TestCaseGenerator {
    public static void main(String[] args)
    {
        generateForP1P2(100, 100);
        generateForP1P2(200, 200);
        generateForP1P2(300, 300);
        generateForP1P2(400, 400);
        generateForP1P2(500, 500);

        generateForP3(100, 100, 5);
        generateForP3(200, 200, 10);
        generateForP3(300, 300, 15);
        generateForP3(400, 400, 20);
        generateForP3(500, 500, 25);
        
    }
    public static void generateForP1P2(int m, int n)
    {
        try{
            System.out.println("generating p1p2+" + m + "+" + n + ".txt ...");
            File file = new File("p1p2+" + m + "+" + n + ".txt");
            if(file.exists())
            {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file, true);
            Random r = new Random();

            fw.append(m + " " + n + " " + r.nextInt(50) + "\n");
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                {
                    fw.append(r.nextInt(100) + "");
                    if(j + 1 < n)
                    {
                        fw.append(" ");
                    }
                }
                fw.append("\n");
            }
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void generateForP2(int m, int n)
    {
        try{
            System.out.println("generating p2+" + m + "+" + n + ".txt ...");
            File file = new File("p2+" + m + "+" + n + ".txt");
            if(file.exists())
            {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file, true);
            Random r = new Random();

            fw.append(m + " " + n + " " + r.nextInt(50) + "\n");
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                {
                    fw.append(r.nextInt(100) + "");
                    if(j + 1 < n)
                    {
                        fw.append(" ");
                    }
                }
                fw.append("\n");
            }
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void generateForP3(int m, int n, int k)
    {
        try{
            System.out.println("generating p3+" + m + "+" + n + "+" + k + ".txt ...");
            File file = new File("p3+" + m + "+" + n + "+" + k + ".txt");
            if(file.exists())
            {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file, true);
            Random r = new Random();

            fw.append(m + " " + n + " " + r.nextInt(50) + "\n");
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                {
                    fw.append(r.nextInt(100) + "");
                    if(j + 1 < n)
                    {
                        fw.append(" ");
                    }
                }
                fw.append("\n");
            }
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
