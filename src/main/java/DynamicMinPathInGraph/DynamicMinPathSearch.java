package DynamicMinPathInGraph;

import static DynamicMinPathInGraph.Graph.INF;

public class DynamicMinPathSearch {

    public static void floydWarshallCalculate(int[][] matrix, int size) {

        for(int k = 0; k < size; k++) {
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    if (matrix[i][k] < INF && matrix[k][j] < INF) {
                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                    }
                }
            }
        }
    }

}
