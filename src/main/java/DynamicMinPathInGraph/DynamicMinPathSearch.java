package DynamicMinPathInGraph;

public class DynamicMinPath {
    Graph graph;

    public DynamicMinPath(Graph graph) {
        this.graph = graph;
    }

    public void floydWarshallCalculate(int[][] matrix, int numberOfVert) {
        //Пробегаемся по всем вершинам и ищем более короткий путь
        //через вершину k
        for(int k = 0; k < numberOfVert; k++) {
            for(int i = 0; i < numberOfVert; i++) {
                for(int j = 0; j < numberOfVert; j++) {
                    //Новое значение ребра равно минимальному между старым
                    //и суммой ребер i <-> k + k <-> j (если через k пройти быстрее)
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }

        return;
    }
}
