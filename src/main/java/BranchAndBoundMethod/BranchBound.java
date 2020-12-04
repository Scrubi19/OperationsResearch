package BranchAndBoundMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BranchBound {
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
                    table[i][j] = scanner.nextInt();
                }
            }
            result = new double[m-1];
            return table;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
