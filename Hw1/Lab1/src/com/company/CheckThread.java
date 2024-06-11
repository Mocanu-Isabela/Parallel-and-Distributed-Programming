package com.company;

public record CheckThread(Supermarket supermarket, int threadNumber) implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread " + threadNumber + " is checking the supermarket !!!!!!!!!!");
        boolean ok = supermarket.checkEverything();
        if (ok)
            System.out.println("Everything ok!");
        else System.out.println("NOK !!!");
    }
}
