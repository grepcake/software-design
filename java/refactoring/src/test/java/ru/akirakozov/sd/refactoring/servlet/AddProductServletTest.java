package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class AddProductServletTest extends ServletTest {
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

    @Override
    void setupDatabase(Statement statement) {
        // let's leave it empty
    }
}