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
public class QueryServlet extends HttpServlet {
    private final ProductRepository productRepository;

    public QueryServlet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try (HtmlWriter w = new HtmlWriter(response.getWriter())) {
            w.withHtml(() -> w.withBody(() -> {
                if ("max".equals(command)) {
                    Product product = productRepository.findByMaxPrice();
                    w.withTag("h1", () -> w.putText("Product with max price: "));
                    w.putText(product.getName() + "\t" + product.getPrice());
                    w.putBreak();
                } else if ("min".equals(command)) {
                    Product product = productRepository.findByMinPrice();
                    w.withTag("h1", () -> w.putText("Product with min price: "));
                    w.putText(product.getName() + "\t" + product.getPrice());
                    w.putBreak();
                } else if ("sum".equals(command)) {
                    long sum = productRepository.getAggregatePrice();
                    w.putTextLn("Summary price: \n" + sum);
                } else if ("count".equals(command)) {
                    int count = productRepository.getProductsCount();
                    w.putTextLn("Number of products: \n" + count);
                } else {
                    w.putTextLn("Unknown command: " + command);
                }

            }));
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
