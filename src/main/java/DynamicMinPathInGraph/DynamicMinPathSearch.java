package DynamicMinPathInGraph;

import static DynamicMinPathInGraph.Graph.INF;

public class DynamicMinPathSearch {

    public static int[][] floydWarshallCalculate(int[][] matrix, int size) {
        int [][] parent = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == INF) {
                    parent[i][j] = INF;
                } else {
                    parent[i][j] = j;
                }
            }
        }
        for(int k = 0; k < size; k++) {
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    if (matrix[i][k] < INF && matrix[k][j] < INF && matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                        parent[i][j] = parent[i][k];
                    }
                }
            }
        }
        return parent;
    }

    public static void getMinPath(int[][]matrix, int [][] parent, int i, int j) {
        i--;
        j--;
        if(matrix[i][j] == INF)
            System.out.println("No path");
        int c = i;
        while (c != j) {
            System.out.print((c + 1) + " -> ");
            c = parent[c][j];
        }
        System.out.println((j + 1));
    }
}
