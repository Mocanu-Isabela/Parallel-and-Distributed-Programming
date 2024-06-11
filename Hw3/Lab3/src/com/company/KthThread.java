package com.company;

public class KthThread implements Runnable{
    private final Integer[][] answer;
    private final Integer k;
    private final Integer stepSize;

    KthThread(Integer[][] answer, int k, int stepSize) {
        this.answer = answer;
        this.k = k;
        this.stepSize = stepSize;
    }

    @Override
    public void run() {
        int n = Main.a;
        int m = Main.c;
        int i = 0;
        int j = k;
        while (true) {
            int over_edge = j/m;
            i += over_edge;
            j -= over_edge * m;
            if (i >= n)
                break;
            answer[i][j] = Matrix.getCellValue(Main.m1, Main.m2, i, j);
            j += stepSize;
        }
    }
}
