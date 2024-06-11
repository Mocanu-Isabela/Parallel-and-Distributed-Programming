package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Integer a, b, c;
    public static Integer functionType; //0 - task, 1 - pool
    public static Integer scanType; // 0 - row, 1 - column, 2 - kth
    public static Integer taskNumber;
    public static Matrix m1;
    public static Matrix m2;

    public static void getParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("a: ");
        a = scanner.nextInt();
        while (a<=1){
            System.out.println("a can't be less than 2");
            System.out.print("a: ");
            a = scanner.nextInt();
        }
        System.out.print("b: ");
        b = scanner.nextInt();
        while (b<=1){
            System.out.println("b can't be less than 2");
            System.out.print("b: ");
            b = scanner.nextInt();
        }
        System.out.print("c: ");
        c = scanner.nextInt();
        while (c<=1){
            System.out.println("c can't be less than 2");
            System.out.print("c: ");
            c = scanner.nextInt();
        }
        System.out.print("function type (0 - task, 1 - pool) : ");
        functionType = scanner.nextInt();
        System.out.print("scan type (0 - row, 1 - column, 2 - kth) : ");
        scanType = scanner.nextInt();
        System.out.print("task number: ");
        taskNumber = scanner.nextInt();
        while (taskNumber<1){
            System.out.println("task number can't be less than 1");
            taskNumber = scanner.nextInt();
        }
        genMatrix();
    }

    public static void genMatrix() {
        m1 = new Matrix(a, b);
        m2 = new Matrix(b, c);
    }

    public static void hardCodeParams() {
        a = 3;
        b = 2;
        c = 2;
        functionType = 0;
        scanType = 0;
        m1 = new Matrix(new Integer[][]{
                {1, 2},
                {3, 4},
                {5, 6}
        });
        m2 = new Matrix(new Integer[][]{
                {7, 8},
                {9, 10}
        });
        taskNumber = 3;
    }

    public static Matrix productByTasks() throws InterruptedException {
        Integer[][] answer = new Integer[a][c];
        List<Thread> threads = new ArrayList<>();
        int iterations = a*c/taskNumber;
        for (int i = 0; i < taskNumber; ++i) {
            int start = i * iterations;
            int end = Math.min((i+1) * iterations, a*c);
            if (scanType == 0)
                threads.add(new Thread(new RowThread(answer, start, end)));
            else if (scanType == 1)
                threads.add(new Thread(new ColumnThread(answer, start, end)));
            else threads.add(new Thread(new KthThread(answer, i, taskNumber)));
        }
        for (Thread thread : threads)
            thread.start();
        for (Thread thread : threads)
            thread.join();
        return new Matrix(answer);
    }

    public static Matrix productByThreadPool() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(taskNumber);
        Integer[][] answer = new Integer[a][c];
        List<Runnable> tasks = new ArrayList<>();
        int iterations = a*c/taskNumber;
        for (int i = 0; i < taskNumber; ++i) {
            int start = i * iterations;
            int end = Math.min((i+1) * iterations, a*c);
            if (scanType == 0)
                tasks.add(new Thread(new RowThread(answer, start, end)));
            else if (scanType == 1)
                tasks.add(new Thread(new ColumnThread(answer, start, end)));
            else tasks.add(new Thread(new KthThread(answer, i, taskNumber)));
        }
        for (Runnable task : tasks)
            executor.execute(task);
        executor.shutdown();
        while (!executor.awaitTermination(1, TimeUnit.DAYS)){
            System.out.println("WoW");
        }
        return new Matrix(answer);
    }

    public static void main(String[] args) throws InterruptedException {
//        hardCodeParams();
        getParams();

        System.out.println("\nThe first matrix: ");
        Matrix.print(m1);
        System.out.println("The second matrix: ");
        Matrix.print(m2);

        Matrix trueProduct = Matrix.getProductSequentially(m1, m2);
        Matrix computedProduct = null;

        float timp1 = System.nanoTime() / 1000000;
        if (functionType == 0)
            computedProduct = productByTasks();
        else computedProduct = productByThreadPool();
        float timp2 = System.nanoTime() / 1000000;

        if (trueProduct.equals(computedProduct)){
            System.out.println("Product: ");
        }
        else {
            System.out.println("nok :(");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("trueProduct: ");
            Matrix.print(trueProduct);
            System.out.println("computedProduct: ");
        }
        Matrix.print(computedProduct);

        System.out.println("Time: "+(timp2-timp1)/1000+"seconds");
    }
}

//Goal
//Divide a simple task between threads. The task can easily be divided in sub-tasks requiring no cooperation at all. See the caching effects, and the costs of creating threads and of switching between threads.
//
//Requirement
//Write several programs to compute the product of two matrices.
//
//Have a function that computes a single element of the resulting matrix.
//
//Have a second function whose each call will constitute a parallel task (that is, this function will be called on several threads in parallel). This function will call the above one several times consecutively to compute several elements of the resulting matrix. Consider the following ways of splitting the work betweeb tasks (for the examples, consider the final matrix being 9x9 and the work split into 4 tasks):
//
//Each task computes consecutive elements, going row after row. So, task 0 computes rows 0 and 1, plus elements 0-1 of row 2 (20 elements in total); task 1 computes the remainder of row 2, row 3, and elements 0-3 of row 4 (20 elements); task 2 computes the remainder of row 4, row 5, and elements 0-5 of row 6 (20 elements); finally, task 3 computes the remaining elements (21 elements).
//Each task computes consecutive elements, going column after column. This is like the previous example, but interchanging the rows with the columns: task 0 takes columns 0 and 1, plus elements 0 and 1 from column 2, and so on.
//Each task takes every k-th element (where k is the number of tasks), going row by row. So, task 0 takes elements (0,0), (0,4), (0,8), (1,3), (1,7), (2,2), (2,6), (3,1), (3,5), (4,0), etc.
//For running the tasks, also implement 2 approaches:
//
//Create an actual thread for each task (use the low-level thread mechanism from the programming language);
//Use a thread pool.
//Experiment with various values for (and document the attempts and their performance):
//
//The sizes of the matrix;
//The number of tasks (this is equal to the number of threads when not using a thread pool);
//The number of threads and other parameters for the thread pool (when using the thread pool).