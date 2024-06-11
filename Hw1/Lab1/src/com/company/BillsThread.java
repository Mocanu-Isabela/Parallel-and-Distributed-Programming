package com.company;
import java.util.List;

public record BillsThread(List<Bill> bills, Supermarket supermarket, int threadNumber) implements Runnable {
    @Override
    public void run() {
        for (Bill bill : bills) {
            System.out.println("Thread " + threadNumber + " is processing a bill");
            supermarket.parseBill(bill);
        }
    }
}