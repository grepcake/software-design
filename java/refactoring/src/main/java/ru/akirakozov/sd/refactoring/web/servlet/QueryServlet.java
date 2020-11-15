package ru.akirakozov.sd.refactoring.web.servlet;

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

        if ("max".equals(command)) {
            Product product = productRepository.findByMaxPrice();
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");
        } else if ("min".equals(command)) {
            Product product = productRepository.findByMinPrice();
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            long sum = productRepository.getAggregatePrice();
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");
            response.getWriter().println(sum);
            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            int count = productRepository.getProductsCount();
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");
            response.getWriter().println(count);
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
