package ru.akirakozov.sd.refactoring.model.repository.impl;

import ru.akirakozov.sd.refactoring.model.domain.Product;
import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductRepositoryTestImpl implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public Product findByMaxPrice() {
        return products.stream().max(Comparator.comparingLong(Product::getPrice)).orElse(null);
    }

    @Override
    public Product findByMinPrice() {
        return products.stream().min(Comparator.comparingLong(Product::getPrice)).orElse(null);
    }

    @Override
    public long getAggregatePrice() {
        return products.stream().mapToLong(Product::getPrice).sum();
    }

    @Override
    public int getProductsCount() {
        return products.size();
    }

}
