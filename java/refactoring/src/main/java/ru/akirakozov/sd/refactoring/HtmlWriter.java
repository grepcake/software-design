package ru.akirakozov.sd.refactoring;

import java.io.PrintWriter;

public class HtmlWriter implements AutoCloseable {
    private final PrintWriter writer;

    public HtmlWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public void withHtml(Runnable nested) {
        withTag("html", nested);
    }

    public void withBody(Runnable nested) {
        withTag("body", nested);
    }

    public void putText(String text) {
        writer.print(text);
    }

    public void putTextLn(String text) {
        writer.println(text);
    }

    public void putBreak() {
        writer.println("</br>");
    }

    public void withTag(String tag, Runnable nested) {
        writer.print("<" + tag + ">");
        nested.run();
        writer.println("</" + tag + ">");
    }

    @Override
    public void close() {
        writer.flush();
    }
}
