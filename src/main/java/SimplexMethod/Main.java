package SimplexMethod;

public class Main {
    public static void main(String[] args) {
        Simplex simplex = new Simplex();
        double[][] table =  simplex.getTableFromFile();

        System.out.println("Number of variables = "+simplex.m+"\nNumber of restrictions = "+simplex.n);
        Simplex.printMatrix(table, simplex.n, simplex.m);

        simplex.createSimplexTable(table);
        Simplex.printMatrix(simplex.simplex_table, simplex.n, simplex.m);

        simplex.Calculate();

        for (int i = 0; i < simplex.result.length; i++) {
            System.out.print("\tx"+(i+1)+" = "+simplex.result[i]);
        }
    }
}