package DynamicMinPathInGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph {
    public static int INF = 999;
    public int size;
    public int[][] matrix;

    public Graph(int size) {
        this.size = size;
        matrix = new int[size][size];
    }

    public Graph(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            size = scanner.nextInt();
            matrix = new int[size][size];

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    matrix[i][j] = scanner.nextInt();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printGraph(String info, int[][] matrix) {
        System.out.println("\t"+info);
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                if (ints[j] == INF)
                    System.out.print("∞ ");
                else
                    System.out.print(ints[j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void printGraph(String info) {
        System.out.println("\t"+info);
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                if (ints[j] == INF)
                    System.out.print("∞ ");
                else
                    System.out.print(ints[j] + " ");
            }
            System.out.print("\n");
        }
    }

    public Graph getGraphDuplication() {
        Graph newGraph = new Graph(size);
        for (int i = 0 ; i < size; i++)
            System.arraycopy(this.matrix[i], 0, newGraph.matrix[i], 0, size);
        return newGraph;
    }

}
