package com.company;

public class ColumnThread implements Runnable{
    private final Integer[][] answer;
    private final Integer start;
    private final Integer end;

    ColumnThread(Integer[][] answer, int start, int end) {
        this.answer = answer;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int n = Main.a;
        int m = Main.c;
        int i = start % n;
        int j = start / n;
        int k = end-start;
        for (int index = 0; index < k; ++index) {
            this.answer[i][j] = Matrix.getCellValue(Main.m1, Main.m2, i, j);
            ++i;
            if (i == n) {
                i = 0;
                ++j;
            }
        }
    }
}