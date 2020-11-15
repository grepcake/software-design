package ru.akirakozov.sd.refactoring.web.servlet;

import ru.akirakozov.sd.refactoring.HtmlWriter;
import ru.akirakozov.sd.refactoring.model.domain.Product;
import ru.akirakozov.sd.refactoring.model.repository.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final ProductRepository productRepository;

    public GetProductsServlet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (HtmlWriter w = new HtmlWriter(response.getWriter())) {
            w.withHtml(() -> w.withBody(() -> {
                for (Product product : productRepository.findAll()) {
                    w.putText(product.getName() + "\t" + product.getPrice());
                    w.putBreak();
                }
            }));
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
