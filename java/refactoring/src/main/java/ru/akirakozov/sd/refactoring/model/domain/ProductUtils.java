package ru.akirakozov.sd.refactoring.model.domain;

import java.util.Comparator;

public class ProductUtils {
    public static final Comparator<Product> structuralComparator = Comparator
            .comparing(Product::getName)
            .thenComparingLong(Product::getPrice);

    private ProductUtils() {
    }

    public static Product makeProduct(String name, long price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
