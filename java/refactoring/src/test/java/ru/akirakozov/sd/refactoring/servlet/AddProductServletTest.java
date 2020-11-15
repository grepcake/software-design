package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class AddProductServletTest {
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
        }
    }

    @AfterEach
    public void teardown() throws IOException {
        Files.delete(DB_PATH);
    }


    public List<Product> listProducts() throws SQLException {
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

    void addItem(String name, int price) throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("name", name);
        request.addParameter("price", Integer.toString(price));

        MockHttpServletResponse response = new MockHttpServletResponse();

        new AddProductServlet().doGet(request, response);
    }
    
    @Test
    void oneItem() throws IOException, SQLException {
        addItem("whatever", 500);
        List<Product> expected = Collections.singletonList(new Product(1, "whatever", 500));
        List<Product> actual = listProducts();
        assertIterableEquals(expected, actual);
    }

    @Test
    void sameItemDifferentPrices() throws IOException, SQLException {
        addItem("same-item", 300);
        addItem("same-item", 400);

        List<Product> expected = Arrays.asList(
                new Product(1, "same-item", 300),
                new Product(2, "same-item", 400)
        );
        expected.sort(null);

        List<Product> actual = listProducts();
        assertIterableEquals(expected, actual);
    }
}