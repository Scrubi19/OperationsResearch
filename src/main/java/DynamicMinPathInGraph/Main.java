package DynamicMinPathInGraph;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("data/graph.txt");
        Graph.printGraph("Graph", graph.matrix);

        int[][] parent =  DynamicMinPathSearch.floydWarshallCalculate(graph.matrix, graph.size);

        Graph.printGraph("Graph after Floyd Warshall Method", graph.matrix);
        Graph.printGraph("Parent after Floyd Warshall Method", parent);

        DynamicMinPathSearch.getMinPath(graph.matrix, parent, 1,7);
    }
}
