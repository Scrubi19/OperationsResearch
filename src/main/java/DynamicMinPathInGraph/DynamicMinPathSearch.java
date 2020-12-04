package DynamicMinPathInGraph;

import static DynamicMinPathInGraph.Graph.INF;

public class DynamicMinPathSearch {

    public static int[][] floydWarshallCalculate(int[][] matrix, int size) {
        int[][] parent = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] != 0 && matrix[i][j] != INF) {
                    parent[i][j] = i;

                }

            }
        }
        Graph.printGraph("Parent", parent);

        for(int k = 0; k < size; k++) {
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    if (matrix[i][k] < INF && matrix[k][j] < INF
                            && matrix[i][k] + matrix[k][j] < matrix[i][j]) {
//                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                        parent[i][j] = parent[k][j];
                    }
                }
            }
        }
        Graph.printGraph("Parent after for", parent);


        return parent;
    }

    public static void getShortestPath(int[][] matrix, int u,  int v) {
        int c = u;
        while (c != v) {
            System.out.print(c+" ");
            c = matrix[c][v];
        }
        System.out.print(v+" ");
    }

}
