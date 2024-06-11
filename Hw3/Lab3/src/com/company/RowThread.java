package com.company;

public class RowThread implements Runnable{
    private final Integer[][] answer;
    private final Integer start;
    private final Integer end;

    RowThread(Integer[][] answer, int start, int end) {
        this.answer = answer;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int n = Main.a;
        int m = Main.c;
        int i = start / m;
        int j = start % m;
        int k = end-start;
        for (int index = 0; index < k; ++index) {
            this.answer[i][j] = Matrix.getCellValue(Main.m1, Main.m2, i, j);
            ++j;
            if (j == m) {
                j = 0;
                ++i;
            }
        }
    }
}
