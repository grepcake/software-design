package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

class QueryServletTest extends ServletTest {
    private static final String DATABASE_FILLING_SQL
            = "insert into product (name, price) values"
            + "  ('soap', 100),"
            + "  ('chewing gum', 30),"
            + "  ('tasty sandwich', 150)";

    @Override
    void setupDatabase(Statement statement) throws SQLException {
        statement.executeUpdate(DATABASE_FILLING_SQL);
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
        testHtmlResponse(response -> new QueryServlet().doGet(request, response), expectedResponse);
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
        testHtmlResponse(response -> new QueryServlet().doGet(request, response), expectedResponse);
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
        testHtmlResponse(response -> new QueryServlet().doGet(request, response), expectedResponse);
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
        testHtmlResponse(response -> new QueryServlet().doGet(request, response), expectedResponse);
    }

}