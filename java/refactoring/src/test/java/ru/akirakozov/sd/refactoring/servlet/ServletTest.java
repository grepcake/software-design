package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;

public abstract class ServletTest {
    private static final String DB_URL = "jdbc:sqlite:test.db";
    private static final Path DB_PATH = Paths.get("test.db");
    private static final Path DB_BACKUP_PATH = Paths.get("test.db.bkp");
    private static final String DB_CREATION_SQL
            = "create table product\n"
            + "(id integer primary key autoincrement not null,\n"
            + " name text not null,\n"
            + " price int not null)";

    private static final String DB_PRODUCTS_LISTING_SQL
            = "select id, name, price from product";

    @BeforeAll
    static void backupExistingDatabase() throws IOException {
        if (Files.exists(DB_PATH)) {
            Files.move(DB_PATH, DB_BACKUP_PATH, ATOMIC_MOVE);
        }
    }

    @AfterAll
    static void restoreExistingDatabase() throws IOException {
        if (Files.exists(DB_BACKUP_PATH)) {
            Files.move(DB_BACKUP_PATH, DB_PATH, ATOMIC_MOVE);
        }
    }

    @BeforeEach
    public void setup() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            Statement statement = connection.createStatement();
            statement.execute(DB_CREATION_SQL);
            setupDatabase(statement);
        }
    }

    @AfterEach
    public void teardown() throws IOException {
        Files.delete(DB_PATH);
    }

    protected List<Product> listProducts() throws SQLException {
        List<Product> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            Statement statement = connection.createStatement();
            ResultSet cursor = statement.executeQuery(DB_PRODUCTS_LISTING_SQL);
            while (cursor.next()) {
                result.add(new Product(cursor.getInt(1), cursor.getString(2), cursor.getInt(3)));
            }
        }
        result.sort(null);
        return result;
    }

    abstract void setupDatabase(Statement statement) throws SQLException;
}
