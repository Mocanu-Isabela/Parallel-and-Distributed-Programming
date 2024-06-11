package Hw5;

import Hw5.Algorithms.ParallelClassic;
import Hw5.Algorithms.ParallelKaratsuba;
import Hw5.Algorithms.SequentialClassic;
import Hw5.Algorithms.SequentialKaratsuba;
import Hw5.Model.AlgorithmChoice;
import Hw5.Model.MethodChoice;
import Hw5.Model.Polynomial;

import java.util.concurrent.ExecutionException;

import static Hw5.Model.AlgorithmChoice.*;
import static Hw5.Model.MethodChoice.*;

public class Main {
    private static final MethodChoice METHOD = SEQUENTIAL;
    private static final AlgorithmChoice ALGORITHM = KARATSUBA;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("method: " + METHOD);
        System.out.println("algorithm: " + ALGORITHM);
        int d = 1000;
        System.out.println("degree: " + d + "\n");
        Polynomial p1 = new Polynomial(d);
        System.out.println("p1=" + p1);
        Polynomial p2 = new Polynomial(d);
        System.out.println("p2=" + p2);
        long startTime = System.nanoTime();
        run(p1, p2);
        long stopTime = System.nanoTime();
        double totalTime = ((double) stopTime - (double) startTime) / 1_000_000_000.0;
        System.out.println("Elapsed running time: " + totalTime + "s");
    }

    private static void run(Polynomial polynomial1, Polynomial polynomial2) throws ExecutionException, InterruptedException {
        Polynomial result;
        if (METHOD.equals(SEQUENTIAL)) {
            if (ALGORITHM.equals(AlgorithmChoice.CLASSIC)) {
                result = SequentialClassic.multiply(polynomial1, polynomial2);
            } else { // KARATSUBA
                result = SequentialKaratsuba.multiply(polynomial1, polynomial2);
            }
        } else { // PARALLEL
            if (ALGORITHM.equals(AlgorithmChoice.CLASSIC)) {
                result = ParallelClassic.multiply(polynomial1, polynomial2);
            } else {
                result = ParallelKaratsuba.multiply(polynomial1, polynomial2, 1);
            }
        }
        System.out.println("p1*p2=" + result.toString());

    }
}
