//Common requirements
//The problems will require to execute a number of independent operations, that operate on shared data.
//There shall be several threads launched at the beginning, and each thread shall execute a lot of operations. The operations to be executed are to be randomly choosen, and with randomly choosen parameters.
//The main thread shall wait for all other threads to end and, then, it shall check that the invariants are obeyed.
//The operations must be synchronized in order to operate correctly. Write, in a documentation, the rules (which mutex what invariants it protects).
//You shall play with the number of threads and with the granularity of the locking, in order to asses the performance issues. Document what tests have you done, on what hardware platform, for what size of the data, and what was the time consumed.
//
//1. Supermarket inventory:
//
//There are several types of products, each having a known, constant, unit price. In the begining, we know the quantity of each product.
//We must keep track of the quantity of each product, the amount of money (initially zero), and the list of bills, corresponding to sales. Each bill is a list of items and quantities sold in a single operation, and their total price.
//We have sale operations running concurrently, on several threads. Each sale decreases the amounts of available products (corresponding to the sold items), increases the amount of money, and adds a bill to a record of all sales.
//From time to time, as well as at the end, an inventory check operation shall be run. It shall check that all the sold products and all the money are justified by the recorded bills.
//Two sales involving distinct products must be able to update their quantities independently (without having to wait for the same mutex).

package com.company;
import java.util.ArrayList;
        import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<ProductService> products = IOService.getProducts("products1.txt");
        Supermarket supermarket = new Supermarket(products);
        List<Bill> bills = Bill.getRandomBills(products, products.size()/5);
        int threadNumber = 400;
        int iterations = (int) Math.ceil((double)bills.size()/(double) threadNumber);
        System.out.println("iter " + iterations + " !!!!!!!!!!");
        List<Thread> threads = new ArrayList<>();
        int checkThreadNumber = threadNumber;
        for (int i = 0; i < threadNumber; ++i) {
            int start = i*iterations;
            int end = Math.min((i+1)*iterations, bills.size());
            threads.add(new Thread(new BillsThread(bills.subList(start, end), supermarket, i)));
            if (i%10 == 0) {
                threads.add(new Thread(new CheckThread(supermarket, checkThreadNumber)));
                ++checkThreadNumber;
            }
        }

        long start = System.currentTimeMillis();

        for (Thread thread : threads)
            thread.start();
        for (Thread thread : threads)
            thread.join();
        new CheckThread(supermarket, checkThreadNumber).run();

        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("start time: " + start + " ms ~~~~~:");
        System.out.println("end time: " + end + " ms ~~~~~:");
        System.out.println("final time: " + time + " ms ~~~~~:");
    }
}