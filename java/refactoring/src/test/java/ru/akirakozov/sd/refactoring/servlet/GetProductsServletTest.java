package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

class GetProductsServletTest extends ServletTest {
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
    void showAllProducts() throws IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        String expectedResponse
                = "<html><body>\n"
                + "soap\t100</br>\n"
                + "chewing gum\t30</br>\n"
                + "tasty sandwich\t150</br>\n"
                + "</body></html>\n";
        testHtmlResponse(response -> new GetProductsServlet().doGet(request, response),
                expectedResponse);
    }
}