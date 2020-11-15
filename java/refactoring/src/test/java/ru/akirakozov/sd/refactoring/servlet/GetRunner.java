package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface GetRunner {
    void runGet(HttpServletResponse response) throws IOException;
}
