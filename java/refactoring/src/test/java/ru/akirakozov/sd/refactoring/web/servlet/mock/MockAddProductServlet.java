package ru.akirakozov.sd.refactoring.web.servlet.mock;

import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;
import ru.akirakozov.sd.refactoring.web.servlet.AddProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MockAddProductServlet extends AddProductServlet implements MockServlet {
    public MockAddProductServlet(ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.doGet(request, response);
    }
}
