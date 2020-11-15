package ru.akirakozov.sd.refactoring.model.repository.impl;

import ru.akirakozov.sd.refactoring.model.domain.Product;
import ru.akirakozov.sd.refactoring.model.exception.RepositoryException;
import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.akirakozov.sd.refactoring.model.database.DatabaseUtils.getDataSource;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<Product> findAll() {
        List<Product> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("select id, name, price from product")) {
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                result.add(fetchNextProduct(cursor.getMetaData(), cursor));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't list products from the database");
        }
        return result;
    }

    @Override
    public void save(Product product) {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "insert into product (id, name, price) values (?, ?, ?)")) {
            product.setId(getMaxId() + 1);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setLong(3, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Can't save a product");
        }
    }

    @Override
    public Product findByMaxPrice() {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select id, name, price from product order by price desc limit 1")) {
            ResultSet cursor = statement.executeQuery();
            return fetchNextProduct(cursor.getMetaData(), cursor);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find a product");
        }
    }

    @Override
    public Product findByMinPrice() {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select id, name, price from product order by price limit 1")) {
            ResultSet cursor = statement.executeQuery();
            return fetchNextProduct(cursor.getMetaData(), cursor);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find a product");
        }
    }

    @Override
    public long getAggregatePrice() {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select sum(price) from product")) {
            return statement.executeQuery().getLong(1);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find a product");
        }
    }

    @Override
    public int getProductsCount() {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select count(*) from product")) {
            return statement.executeQuery().getInt(1);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find a product");
        }
    }

    private int getMaxId() {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select coalesce(max(id), 0) from product")) {
            return statement.executeQuery().getInt(1);
        } catch (SQLException e) {
            throw new RepositoryException("Can't get maximum product id");
        }
    }

    private Product fetchNextProduct(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Product product = new Product();
        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                product.setId(resultSet.getInt(i));
            } else if ("name".equalsIgnoreCase(columnName)) {
                product.setName(resultSet.getString(i));
            } else if ("price".equalsIgnoreCase(columnName)) {
                product.setPrice(resultSet.getLong(i));
            } else {
                throw new RepositoryException("Unexpected column 'Product." + columnName + "'");
            }
        }
        return product;
    }
}
