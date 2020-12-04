package DynamicMinPathInGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph {
    public static int INF = 999;
    int size;

    int[][] matrix_G;

    public void getGraphFromFile() {
        try (Scanner scanner = new Scanner(new File("data/graph.txt"))) {
            size = scanner.nextInt();
            matrix_G = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix_G[i][j] = scanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void printGraph(String info, int[][] matrix) {
        System.out.println("\t"+info);
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                if (ints[j] == INF) {
                    System.out.print("∞ ");
                } else {
                    System.out.print(ints[j] + " ");

                }
            }
            System.out.print("\n");
        }
    }
}
