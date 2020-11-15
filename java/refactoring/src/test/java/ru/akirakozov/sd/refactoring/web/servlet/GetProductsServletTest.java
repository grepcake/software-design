package ru.akirakozov.sd.refactoring.web.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;
import ru.akirakozov.sd.refactoring.web.servlet.mock.MockGetProductsServlet;
import ru.akirakozov.sd.refactoring.web.servlet.mock.MockServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.model.domain.ProductUtils.makeProduct;

class GetProductsServletTest extends ServletTest {
    @Override
    void setupRepository() {
        productRepository.save(makeProduct("soap", 100));
        productRepository.save(makeProduct("chewing gum", 30));
        productRepository.save(makeProduct("tasty sandwich", 150));
    }

    @Override
    MockServlet makeServlet(ProductRepository productRepository) {
        return new MockGetProductsServlet(productRepository);
    }

    @Test
    void showAllProducts() throws IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        String expectedResponse
                = "<html><body>\n"
                + "soap\t100</br>\n"
                + "chewing gum\t30</br>\n"
                + "tasty sandwich\t150</br>\n"
                + "</body></html>\n";
        testHtmlResponse(request, expectedResponse);
    }
}