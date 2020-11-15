package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.model.repository.impl.ProductRepositoryImpl;
import ru.akirakozov.sd.refactoring.web.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.web.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.web.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(new ProductRepositoryImpl())), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(new ProductRepositoryImpl())),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(new ProductRepositoryImpl())),"/query");

        server.start();
        server.join();
    }
}
