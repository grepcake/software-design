package ru.akirakozov.sd.refactoring.web.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockHttpServletResponse;
import ru.akirakozov.sd.refactoring.model.domain.Product;
import ru.akirakozov.sd.refactoring.model.domain.ProductUtils;
import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;
import ru.akirakozov.sd.refactoring.model.repository.impl.ProductRepositoryTestImpl;
import ru.akirakozov.sd.refactoring.web.servlet.mock.MockServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    protected ProductRepository productRepository;
    protected MockServlet servlet;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepositoryTestImpl();
        setupRepository();
        servlet = makeServlet(productRepository);
    }

    protected List<Product> listProducts() {
        List<Product> result = productRepository.findAll();
        result.sort(ProductUtils.structuralComparator);
        return result;
    }

    protected void testHtmlResponse(HttpServletRequest request,
                                    String expectedHtmlResponse)
            throws IOException {
        MockHttpServletResponse response = new MockHttpServletResponse();
        servlet.doGet(request, response);
        assertEquals(response.getStatus(), HttpServletResponse.SC_OK);
        assertEquals(response.getContentType(), "text/html");
        assertEquals(response.getContentAsString(), expectedHtmlResponse);
    }

    void setupRepository() {
    }

    abstract MockServlet makeServlet(ProductRepository productRepository);
}
