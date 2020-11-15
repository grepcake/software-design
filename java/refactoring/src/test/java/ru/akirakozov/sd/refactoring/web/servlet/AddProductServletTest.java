package ru.akirakozov.sd.refactoring.web.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import ru.akirakozov.sd.refactoring.model.domain.Product;
import ru.akirakozov.sd.refactoring.model.domain.ProductUtils;
import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;
import ru.akirakozov.sd.refactoring.web.servlet.mock.MockAddProductServlet;
import ru.akirakozov.sd.refactoring.web.servlet.mock.MockServlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.akirakozov.sd.refactoring.model.domain.ProductUtils.makeProduct;

class AddProductServletTest extends ServletTest {
    void addItem(String name, int price) throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("name", name);
        request.addParameter("price", Integer.toString(price));

        MockHttpServletResponse response = new MockHttpServletResponse();

        new AddProductServlet(productRepository).doGet(request, response);
    }

    @Override
    MockServlet makeServlet(ProductRepository productRepository) {
        return new MockAddProductServlet(productRepository);
    }

    @Test
    void oneItem() throws IOException {
        addItem("whatever", 500);
        List<Product> expected = Collections.singletonList(makeProduct("whatever", 500));
        List<Product> actual = listProducts();
        Product first = expected.get(0);
        Product second = actual.get(0);
        assertEquals(first.getName(), second.getName());
        assertTrue(first.getName().equals(second.getName()) && first.getPrice() == second.getPrice());
        assertEquals(expected.get(0), actual.get(0));
        assertIterableEquals(expected, actual);
    }

    @Test
    void sameItemDifferentPrices() throws IOException {
        addItem("same-item", 300);
        addItem("same-item", 400);

        List<Product> expected = Arrays.asList(
                makeProduct("same-item", 300),
                makeProduct("same-item", 400)
        );
        expected.sort(ProductUtils.structuralComparator);

        List<Product> actual = listProducts();
        assertIterableEquals(expected, actual);
    }
}