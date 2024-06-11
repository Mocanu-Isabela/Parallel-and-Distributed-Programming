package com.company;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductService extends Product {
    private int soldQuantity;
    private final Lock mutex;

    public ProductService(String name, int price, int quantity) {
        super(name, quantity, price);
        this.mutex = new ReentrantLock(false);
        this.soldQuantity = 0;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Lock getMutex() {
        return mutex;
    }
}