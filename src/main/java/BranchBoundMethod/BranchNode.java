package BranchBoundMethod;

import DynamicMinPathInGraph.Graph;
import static DynamicMinPathInGraph.Graph.INF;

public class BranchNode {
    int MAX = Integer.MAX_VALUE;
    Graph graph;

    int maxRating;
    int[] dI, dJ, maxRateCell, availableCols, availableRows;
    BranchNode parent;
    BranchNode left;
    BranchNode right;

    public BranchNode() {
        maxRating = 0;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public BranchNode(Graph graph, int maxRating, int[] availableCols, int[] availableRows, BranchNode parent) {
        this.graph = graph;
        this.maxRating = maxRating;
        this.availableCols = availableCols;
        this.availableRows = availableRows;

        this.parent = parent;
        this.left = null;
        this.right = null;
    }

    public void branching() {
        this.right = new BranchNode(this.matrixReduction(),
                this.maxRating,
                this.getAvailableCols(),
                this.getAvailableRows(),
                this
        );
        this.right.reductionRowsDi();
        this.right.reductionColsDj();

        this.right.initH();
        this.right.searchZeroCellMaxRating();

        int bottomLine = this.maxRating;
        this.left = new BranchNode(this.matrixWithoutSomePath(),
                bottomLine,
                this.availableCols,
                this.availableRows,
                this
        );
        this.left.reductionRowsDi();
        this.left.reductionColsDj();

        this.left.initH();
        this.left.searchZeroCellMaxRating();
    }

    public Graph matrixReduction() {
        int I = 0, J = 0;
        Graph newGraph = new Graph(graph.size-1);
        Graph copied = this.graph.getGraphDuplication();

        if(getIndex(availableRows, availableCols[maxRateCell[1]]) != -1 &&
                getIndex(availableCols, availableRows[maxRateCell[0]]) != -1) {
            copied.matrix[getIndex(availableRows, availableCols[maxRateCell[1]])][getIndex(availableCols, availableRows[maxRateCell[0]])] = -1;
        }

        for (int i = 0; i < graph.size; i++) {
            if (i != maxRateCell[0]) {
                for (int j = 0; j < graph.size; j++)
                    if (j != maxRateCell[1]) {
                        newGraph.matrix[I][J] = copied.matrix[i][j];
                        J++;
                    }
                I++;
                J = 0;
            }
        }
        return newGraph;
    }

    public int[][] copyMatrix(int[][] matrix) {
        int[][] newMatrix = new int[graph.size][graph.size];
        for (int i = 0; i < graph.size; i++)
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, graph.size);
        return newMatrix;
    }


    public Graph matrixWithoutSomePath() {
        Graph newGraph = new Graph(graph.size);
        for (int i = 0; i < graph.size; i++)
            System.arraycopy(graph.matrix[i], 0, newGraph.matrix[i], 0, graph.size);
        newGraph.matrix[maxRateCell[0]][maxRateCell[1]] = -1;
        return newGraph;
    }


    public void searchZeroCellMaxRating() {
        int[][] bufMatrix = new int[graph.size][graph.size];
        maxRateCell = new int[2];
        for (int i = 0; i < graph.size; i++)
            for (int j = 0; j < graph.size; j++) {
                if(graph.matrix[i][j] != 0)
                    bufMatrix[i][j] = -1;
                else
                    bufMatrix[i][j] = getSumMinValuesInColRow(i, j);
            }
        int max = -1;
        for (int i = 0; i < graph.size; i++)
            for (int j = 0; j < graph.size; j++) {
                if (max < bufMatrix[i][j]) {
                    max = bufMatrix[i][j];
                    maxRateCell[0] = i;
                    maxRateCell[1] = j;
                }
            }
    }

    public int getSumMinValuesInColRow(int I, int J) {
        int rowMin = MAX;
        int colMin = MAX;
        for (int j = 0; j < graph.size; j++) {
            if (rowMin > graph.matrix[I][j] && graph.matrix[I][j] != -1 && j != J)
                rowMin = graph.matrix[I][j];
            else if (rowMin == -1)
                rowMin = graph.matrix[I][j];
            if (colMin > graph.matrix[j][J] && graph.matrix[j][J] != -1 && j != I)
                colMin = graph.matrix[j][J];
            else if (colMin == -1)
                colMin = graph.matrix[j][J];
        }
        return rowMin + colMin;
    }

    public void initH() {
        for (int i = 0; i < graph.size; i++) {
            if (dI[i] == -1)
                maxRating += INF;
            else if (dJ[i] == -1)
                maxRating += INF;
            maxRating += dI[i] + dJ[i];
        }
    }

    public void reductionColsDj() {
        dJ = new int[graph.size];

        for (int j = 0; j < graph.size; j++) {
            dJ[j] = graph.matrix[0][j];
            for (int i = 1; i < graph.size; i++) {
                if (dJ[j] > graph.matrix[i][j] && graph.matrix[i][j] != -1) {
                    dJ[j] = graph.matrix[i][j];
                } else if (dJ[j] == -1) {
                    dJ[j] = graph.matrix[i][j];
                }
            }
        }
        for (int j = 0; j < graph.size; j++)
            for (int i = 0; i < graph.size; i++)
                if(graph.matrix[i][j] != -1)
                    graph.matrix[i][j] -= dJ[j];

    }

    public void reductionRowsDi() {
        dI = new int[graph.size];

        for (int i = 0; i < graph.size; i++) {
            dI[i] = graph.matrix[i][0];
            for (int j = 1; j < graph.size; j++) {
                if (dI[i] > graph.matrix[i][j] && graph.matrix[i][j] != -1) {
                    dI[i] = graph.matrix[i][j];
                } else if (dI[i] == -1) {
                    dI[i] = graph.matrix[i][j];
                }
            }
        }
        for (int i = 0; i < graph.size; i++)
            for (int j = 0; j < graph.size; j++)
                if(graph.matrix[i][j] != -1)
                    graph.matrix[i][j] -= dI[i];

    }

    public int[] getAvailableCols() {
        int[] ret = new int[graph.size - 1];
        for (int i = 0, I = 0; i < graph.size; i++) {
            if(i != maxRateCell[1]) {
                ret[I] = availableCols[i];
                I++;
            }
        }
        return ret;
    }

    public int[] getAvailableRows() {
        int[] ret = new int[graph.size - 1];
        for (int i = 0, I = 0; i < graph.size; i++)
            if(i != maxRateCell[0]) {
                ret[I] = availableRows[i];
                I++;
            }
        return ret;
    }

    public int getIndex(int[] array, int elem) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == elem)
                return i;
        return -1;
    }
    public void initAvailableVertices() {
        availableRows = new int[graph.size];
        availableCols = new int[graph.size];
        for (int i = 0; i < graph.size; i++) {
            availableRows[i] = i;
            availableCols[i] = i;
        }
    }
}
