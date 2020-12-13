package SimplexMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simplex {
    int n;
    int m;
    String search;

    double[] result;
    double[][] simplex_table;
    ArrayList<Integer> basis = new ArrayList<>();

    public double[][] getTableFromFile() {
        double[][] table;
        try (Scanner scanner = new Scanner(new File("data/matrix.txt"))) {
            n = scanner.nextInt()+1;
            m = scanner.nextInt()+1;
            search = scanner.nextLine();

            table = new double[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    table[i][j] = scanner.nextDouble();
                }
            }
            result = new double[m-1];
            return table;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void createSimplexTable(double[][] table)  {
        simplex_table = new double[n][n+m-1];

        for (int i = 0; i < n; i++)  {
            for (int j = 0; j < n+m-1; j++)  {
                if (j < m)
                    simplex_table[i][j] = table[i][j];
                else
                    simplex_table[i][j] = 0;
            }
            if ((m + i) < n+m-1) {
                simplex_table[i][m+i] = 1;
                basis.add(m+i);
            }
        }
        m = n+m-1;
    }

    public double[][] Calculate()  {
        int mainCol, mainRow;

        while (!finalCond())  {
            mainCol = getMainCol();
            mainRow = getMainRow(mainCol);
            basis.set(mainRow, mainCol);

            double[][] buffer = new double[n][m];

            for (int i = 0; i < m; i++)
                buffer[mainRow][i] = simplex_table[mainRow][i] / simplex_table[mainRow][mainCol];

            for (int i = 0; i < n; i++)  {
                if (i == mainRow) continue;
                for (int j = 0; j < m; j++)
                    buffer[i][j] = simplex_table[i][j] - simplex_table[i][mainCol] * buffer[mainRow][j];
            }
            simplex_table = buffer;
        }
        for (int i = 0; i < result.length; i++)  {
            int x = basis.indexOf(i + 1);
            if (x != -1)
                result[i] = simplex_table[x][0];
            else
                result[i] = 0;
        }

        return simplex_table;
    }

    private boolean finalCond()  {
        boolean flag = true;

        if (search.contains("max")) {
            for (int i = 1; i < m; i++)  {
                if (simplex_table[n - 1][i] < 0) {
                    flag = false;
                    break;
                }
            }
        } else if (search.contains("min")) {
            for (int i = 1; i < m; i++)  {
                if (simplex_table[n - 1][i] > 0) {
                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }

    public int getMainCol() {
        int mainCol = -1;

        if (search.contains("max")) {
            mainCol = 1;
            for (int j = 2; j < m; j++)
                if (simplex_table[n - 1][j] < simplex_table[n - 1][mainCol])
                    mainCol = j;
        }  else if (search.contains("min")) {
            int max = 999;
            for (int j = 1; j < m; j++) {
                if ( (Math.abs(simplex_table[n - 1][j]) < max ) && Math.abs(simplex_table[n - 1][j]) > 0)
                    mainCol = j;
            }
        }

        return mainCol;
    }

    public int getMainRow(int mainCol)  {
        int mainRow = 0;

        for (int i = 0; i < n - 1; i++)
            if (simplex_table[i][mainCol] > 0) {
                mainRow = i;
            break;
        }

        for (int i = mainRow + 1; i < n - 1; i++)
            if ((simplex_table[i][mainCol] > 0) &&
                ((simplex_table[i][0] / simplex_table[i][mainCol]) < (simplex_table[mainRow][0] / simplex_table[mainRow][mainCol])))
                mainRow = i;

        return mainRow;
    }

    public static void printMatrix(double[][] matrix, int n, int m) {
        System.out.println("\tB");
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                System.out.print("  "+matrix[i][j]);
            }
            System.out.println();
        }
    }



}
