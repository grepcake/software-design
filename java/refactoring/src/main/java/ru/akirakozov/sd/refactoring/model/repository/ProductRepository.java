package ru.akirakozov.sd.refactoring.model.repository;

import ru.akirakozov.sd.refactoring.model.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    void save(Product product);

    Product findByMaxPrice();

    Product findByMinPrice();

    long getAggregatePrice();

    int getProductsCount();
}
