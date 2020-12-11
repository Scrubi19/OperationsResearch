package BranchBoundMethod;

import DynamicMinPathInGraph.Graph;

import java.util.ArrayList;
import java.util.List;

public class BranchBoundMethod {

    public static void calculate() {
        BranchNode root = new BranchNode();

        root.graph = new Graph("data/graph2.txt");
        root.initAvailableVertices();

        int[][] matrix = root.copyMatrix(root.graph.matrix);

        root.reductionRowsDi();
        root.reductionColsDj();

        root.initH();
        root.searchZeroCellMaxRating();

        root.branching();

        int flag = 0;
        BranchNode node = new BranchNode();
        while (flag != 1) {
            node = getMinBranchNode(root);
            if (node.graph.size != 1) {
                node.branching();
            } else {
                flag = 1;
            }
        }
        printPath(node, matrix, root.graph.size);
    }

    public static void printPath(BranchNode node, int[][] matrix, int numberVertices) {
        int from, before;
        BranchNode buf = node;
        List<Section> path = new ArrayList<>();
        while (buf != null) {
            if (buf.parent != null) {
                from = buf.availableRows[buf.maxRateCell[0]];
                before = buf.availableCols[buf.maxRateCell[1]];
                path.add(new Section(from, before, matrix[from][before]));
            }
            buf = buf.parent;
        }

        int current = 0, sum = 0;
        for (int i = 0; i < numberVertices; i++) {
            for (Section s : path) {
                if (s.from == current) {
                    System.out.println(getLetter(s.from) + " -> " + getLetter(s.before) + " = " + s.distance);
                    current = s.before;
                    sum += s.distance;
                    break;
                }
            }
        }
        System.out.println("Total path length: " + sum);
    }

    public static char getLetter(int number) {
        return (char) (number + 'A');
    }

    public static BranchNode getMinBranchNode(BranchNode root) {
        List<BranchNode> listBranchNodes = new ArrayList<>();
        treeVisitor(root, listBranchNodes);

        int minBranchIndex = 0;
        for (int i = 1; i < listBranchNodes.size(); i++) {
            if (listBranchNodes.get(minBranchIndex).maxRating > listBranchNodes.get(i).maxRating)
                minBranchIndex = i;
        }

        return listBranchNodes.get(minBranchIndex);
    }

    public static void treeVisitor(BranchNode node, List<BranchNode> listBranchNodes) {
        if(node.left == null && node.right == null) {
            listBranchNodes.add(node);
        }

        if(node.left != null) {
            treeVisitor(node.left, listBranchNodes);
        }

        if (node.right != null) {
            treeVisitor(node.right, listBranchNodes);
        }
    }

}
