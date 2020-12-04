package zero_knowledge_proof_protocol;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Scanner;

public class Graph {
    int size;
    int n_ribs;

    int[][] matrix_G;
    int[][] hamilton_cycle;
    int[][] permutation_order;

    public void getGraphFromFile() {
        try (Scanner scanner = new Scanner(new File("files/graph.txt"))) {
            int element;
            size = scanner.nextInt();
            n_ribs = scanner.nextInt();

            matrix_G = new int[size][size];
            hamilton_cycle = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    element = scanner.nextInt();
                    if (element == 5) {
                        matrix_G[i][j] = 1;
                        hamilton_cycle[i][j] = 1;
                    } else {
                        matrix_G[i][j] = element;
                        hamilton_cycle[i][j] = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void swindler () {
        SecureRandom srand = new SecureRandom();
        srand.setSeed(System.currentTimeMillis());
        for(int i = 0; i < hamilton_cycle.length; i++) {
            for (int j = 0; j < hamilton_cycle.length; j++) {
                hamilton_cycle[i][srand.nextInt(hamilton_cycle.length)] = 1;
            }
        }
    }

    public int[][] getIsomorphicGraph () {
        SecureRandom srand = new SecureRandom();
        srand.setSeed(System.currentTimeMillis());

        int[][] matrix_H = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix_H[i][j] = (srand.nextInt((10-2))+1)*10 + matrix_G[i][j];
            }
        }
        printGraph("Matrix H", matrix_H);
        int num_permutations = srand.nextInt(size/2)+1;
        this.permutation_order = new int[num_permutations][2];
        HashSet<Integer> reiterative  = new HashSet<Integer>();

        for (int i = 0; i < num_permutations;i++) {
            do {
                permutation_order[i][0] = srand.nextInt(size);
            } while (!reiterative.add(permutation_order[i][0]));
            do {
                permutation_order[i][1] = srand.nextInt(size);
            } while (!reiterative.add(permutation_order[i][1]));
        }

        System.out.println("Permutation Order");
        for(int i = 0; i < num_permutations;i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print(permutation_order[i][j]+" ");
            }
            System.out.print("\n");
        }
        shuffleVertices(matrix_H, permutation_order);

        System.out.println("after 1 step trans");
        return matrix_H;
    }

    public static void shuffleVertices(int [][] matrix, int[][] permutation_order) {
        for (int[] value : permutation_order) {
            int[] temp = matrix[value[0]];
            matrix[value[0]] = matrix[value[1]];
            matrix[value[1]] = temp;
        }
        matrixTranspose(matrix);
        for (int[] ints : permutation_order) {
            int[] temp = matrix[ints[0]];
            matrix[ints[0]] = matrix[ints[1]];
            matrix[ints[1]] = temp;
        }
        matrixTranspose(matrix);
    }

    public static void returnShuffledVertices(int [][] matrix, int[][] permutation_order) {
        matrixTranspose(matrix);
        for (int[] value : permutation_order) {
            int[] temp = matrix[value[0]];
            matrix[value[0]] = matrix[value[1]];
            matrix[value[1]] = temp;
        }
        matrixTranspose(matrix);
        for (int[] ints : permutation_order) {
            int[] temp = matrix[ints[0]];
            matrix[ints[0]] = matrix[ints[1]];
            matrix[ints[1]] = temp;
        }
    }

    public static void matrixTranspose(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix.length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public static void printGraph(String info, int[][] matrix) {
        System.out.println("\t"+info);
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.print("\n");
        }
    }
}
