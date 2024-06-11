package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Supermarket {
    private int money;
    private final List<ProductService> products;
    private final Lock moneyMutex;
    private final ReadWriteLock storageMutex;
    private final List<Bill> consumedBillList;

    public Supermarket(List<ProductService> products) {
        this.products = products;
        this.money = 0;
        this.moneyMutex = new ReentrantLock();
        this.storageMutex = new ReentrantReadWriteLock(false);
        this.consumedBillList = new ArrayList<>();
    }

    public void parseBill(Bill bill) {
        storageMutex.readLock().lock();
        int totalPrice = 0;
        for (Product billProduct : bill.getProducts())
            for (ProductService product : products)
                if (billProduct.getName().equals(product.getName())) {
                    product.getMutex().lock();
                    product.setSoldQuantity(product.getSoldQuantity() + billProduct.getQuantity());
                    totalPrice += product.getPrice() * billProduct.getQuantity();
                    product.getMutex().unlock();
                }
        moneyMutex.lock();
        consumedBillList.add(bill);
        money += totalPrice;
        moneyMutex.unlock();
        storageMutex.readLock().unlock();
    }

    public boolean checkEverything() {
        storageMutex.writeLock().lock();
        System.out.println("Checked so far " + consumedBillList.size() + " bills");
        Map<String, Integer> checkerMap = new HashMap<>();
        for (ProductService product : products)
            checkerMap.put(product.getName(), 0);
        int totalPrice = 0;
        for (Bill bill : consumedBillList)
            for (Product product : bill.getProducts()) {
                checkerMap.put(product.getName(), checkerMap.get(product.getName()) + product.getQuantity());
                totalPrice += product.getQuantity() * product.getPrice();
            }
        if (totalPrice != money) {
            storageMutex.writeLock().unlock();
            return false;
        }
        for (ProductService ProductService : products)
            if (!checkerMap.get(ProductService.getName()).equals(ProductService.getSoldQuantity())) {
                storageMutex.writeLock().unlock();
                return false;
            } else if (ProductService.getSoldQuantity() > ProductService.getQuantity()) {
                storageMutex.writeLock().unlock();
                return false;
            }
        storageMutex.writeLock().unlock();
        return true;
    }
}