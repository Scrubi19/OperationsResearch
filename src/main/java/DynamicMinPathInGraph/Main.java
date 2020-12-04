package DynamicMinPathInGraph;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.getGraphFromFile();
        Graph.printGraph("Graph", graph.matrix_G);

        int[][] parent =  DynamicMinPathSearch.floydWarshallCalculate(graph.matrix_G, graph.size);

        Graph.printGraph("Graph after Floyd Warshall Method", graph.matrix_G);
        Graph.printGraph("Parent after Floyd Warshall Method", parent);

        DynamicMinPathSearch.getMinPath(graph.matrix_G, parent, 1,7);
    }
}
