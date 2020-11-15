package ru.akirakozov.sd.refactoring.web.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;
import ru.akirakozov.sd.refactoring.web.servlet.mock.MockQueryServlet;
import ru.akirakozov.sd.refactoring.web.servlet.mock.MockServlet;

import java.io.IOException;

import static ru.akirakozov.sd.refactoring.model.domain.ProductUtils.makeProduct;

class QueryServletTest extends ServletTest {
    @Override
    void setupRepository() {
        productRepository.save(makeProduct("soap", 100));
        productRepository.save(makeProduct("chewing gum", 30));
        productRepository.save(makeProduct("tasty sandwich", 150));
    }

    @Override
    MockServlet makeServlet(ProductRepository productRepository) {
        return new MockQueryServlet(productRepository);
    }

    @Test
    void maxQuery() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("command", "max");
        String expectedResponse
                = "<html><body>\n"
                + "<h1>Product with max price: </h1>\n"
                + "tasty sandwich\t150</br>\n"
                + "</body></html>\n";
        testHtmlResponse(request, expectedResponse);
    }

    @Test
    void minQuery() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("command", "min");
        String expectedResponse
                = "<html><body>\n"
                + "<h1>Product with min price: </h1>\n"
                + "chewing gum\t30</br>\n"
                + "</body></html>\n";
        testHtmlResponse(request, expectedResponse);
    }

    @Test
    void sumQuery() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("command", "sum");
        String expectedResponse
                = "<html><body>\n"
                + "Summary price: \n"
                + "280\n"
                + "</body></html>\n";
        testHtmlResponse(request, expectedResponse);
    }

    @Test
    void countQuery() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("command", "count");
        String expectedResponse
                = "<html><body>\n"
                + "Number of products: \n"
                + "3\n"
                + "</body></html>\n";
        testHtmlResponse(request, expectedResponse);
    }

}