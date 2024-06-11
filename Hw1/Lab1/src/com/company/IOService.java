package com.company;
import java.io.BufferedInputStream;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Scanner;

public class IOService {
    public static List<ProductService> getProducts(String filename) {
        Scanner reader;
        try {
            reader = new Scanner(new BufferedInputStream(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
        List<ProductService> products = new ArrayList<>();
        int n = reader.nextInt();
        for (int i = 0; i < n; ++i) {
            String name = reader.next();
            int quantity = reader.nextInt();
            int price = reader.nextInt();
            products.add(new ProductService(name, quantity, price));
        }
        return products;
    }
}