package SimplexMethod;

public class Main {
    public static void main(String[] args) {
        Simplex simplex = new Simplex();
        double[][] table =  simplex.getTableFromFile();

        System.out.println("Number of variables = "+simplex.m+"\nNumber of restrictions = "+simplex.n);
        Simplex.printMatrix(table, simplex.n, simplex.m);

        simplex.createSimplexTable(table);
        Simplex.printMatrix(simplex.simplex_table, simplex.n, simplex.m);

        int col = simplex.getMainCol();
        int row = simplex.getMainRow(col);
        System.out.println(row+";"+col+" = "+simplex.simplex_table[row][col]);
        simplex.Calculate();

        System.out.println("x1 = "+simplex.result[0]+" x2 = "+simplex.result[1]);
    }
}