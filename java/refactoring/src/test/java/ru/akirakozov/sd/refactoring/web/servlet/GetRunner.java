package ru.akirakozov.sd.refactoring.web.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface GetRunner {
    void runGet(HttpServletResponse response) throws IOException;
}
