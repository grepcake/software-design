package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        MockHttpServletResponse response = new MockHttpServletResponse();
        new GetProductsServlet().doGet(request, response);

        String expectedResponse
                = "<html><body>\n"
                + "soap\t100</br>\n"
                + "chewing gum\t30</br>\n"
                + "tasty sandwich\t150</br>\n"
                + "</body></html>\n";
        assertEquals(response.getStatus(), HttpServletResponse.SC_OK);
        assertEquals(response.getContentType(), "text/html");
        assertEquals(response.getContentAsString(), expectedResponse);
    }
}