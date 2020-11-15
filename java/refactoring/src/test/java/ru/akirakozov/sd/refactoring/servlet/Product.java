package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Product implements Comparable<Product> {
    private final int id;
    private final @NotNull String name;
    private final int price;

    public Product(int id, @NotNull String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                price == product.price &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public int compareTo(@NotNull Product product) {
        int res = Integer.compare(this.id, product.id);
        if (res != 0) {
            return res;
        }
        res = this.name.compareTo(product.name);
        if (res != 0) {
            return res;
        }
        res = Integer.compare(this.price, product.price);
        return res;
    }
}
