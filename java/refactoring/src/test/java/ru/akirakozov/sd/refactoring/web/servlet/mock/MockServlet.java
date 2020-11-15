package ru.akirakozov.sd.refactoring.web.servlet.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MockServlet {
    void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
