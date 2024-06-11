package com.company;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Create two threads, a producer and a consumer, with the producer feeding the consumer.
//        Requirement: Compute the scalar product of two vectors.
//
// Create two threads. The first thread (producer) will compute the products of pairs of elements - one from each vector - and will feed the second thread.
// The second thread (consumer) will sum up the products computed by the first one.
// The two threads will behind synchronized with a condition variable and a mutex.
// The consumer will be cleared to use each product as soon as it is computed by the producer thread.

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("The size of the vector: ");
        int vectorSize = scanner.nextInt();
        while(vectorSize < 2){
            System.out.print("The size is too small! It has to be greater than 1!\n");
            System.out.print("The size of the vector: ");
            vectorSize = scanner.nextInt();
        }

        List<Integer> firstVector = new ArrayList<>();
        List<Integer> secondVector = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < vectorSize; ++i) {
            firstVector.add(random.nextInt(100));
            secondVector.add(random.nextInt(100));
        }
        System.out.println("First vector: " + firstVector);
        System.out.println("Second vector: " + secondVector + "\n");
        int sharedDataSize = vectorSize / 2;
        Queue<Integer> sharedData = new LinkedList<>();
        Lock mutex = new ReentrantLock();
        Condition condition = mutex.newCondition();
        Producer producer = new Producer(sharedDataSize, sharedData, mutex, condition, firstVector, secondVector);
        Consumer consumer = new Consumer(sharedData, mutex, condition, vectorSize);
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch(InterruptedException ie) {
            System.out.println("Main Error: " + ie.getMessage());
        }
        System.out.println("Threaded sum: " + consumer.getSum());

        int sum = 0;
        for(int i = 0; i < vectorSize; ++i)
            sum += firstVector.get(i) * secondVector.get(i);
        System.out.println("Correct sum: " + sum);
    }
}
