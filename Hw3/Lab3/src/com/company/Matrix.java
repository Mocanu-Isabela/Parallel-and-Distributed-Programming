package com.company;

import java.util.Random;

public class Matrix {
    private final Integer[][] matrix;
    private final Integer n, m;

    Matrix(Integer[][] matrix) {
        this.matrix = matrix;
        this.n = matrix.length;
        this.m = matrix[0].length;
    }

    Matrix(int n, int m) {
        matrix = new Integer[n][m];
        this.n = n;
        this.m = m;
        Random random = new Random();
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < m; ++j)
                matrix[i][j] = random.nextInt(10);
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public static int getCellValue(Matrix m1, Matrix m2, int a, int b) {
        int answer = 0;
        for (int i = 0; i < m1.m; ++i)
            answer += m1.get(a, i) * m2.get(i, b);
        return answer;
    }

    public static Matrix getProductSequentially(Matrix m1, Matrix m2) {
        Integer[][] product = new Integer[m1.n][m2.m];
        for (int i = 0; i < m1.n; ++i)
            for (int j = 0; j < m2.m; ++j)
                product[i][j] = getCellValue(m1, m2, i, j);
        return new Matrix(product);
    }

    public static void print(Matrix matrx) {
        for (int i = 0; i < matrx.n; ++i) {
            for (int j = 0; j < matrx.m; ++j)
                System.out.print(matrx.matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix objMatrix))
            return false;
        if (!n.equals(objMatrix.n) || !m.equals(objMatrix.m))
            return false;
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < m; ++j)
                if (!matrix[i][j].equals(objMatrix.matrix[i][j]))
                    return false;
        return true;
    }
}