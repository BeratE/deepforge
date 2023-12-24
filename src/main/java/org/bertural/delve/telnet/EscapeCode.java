package org.bertural.delve.telnet;

import org.bertural.delve.Printable;

import java.io.PrintWriter;

public enum EscapeCode implements Printable {
    CLEAR_SCREEN("\033[2J"),
    RESET_CURSOR("\033[H");

    EscapeCode(String code) {
        this.text = code;
    }

    private String text;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void print(PrintWriter writer) {
        writer.print(text);
        writer.flush();
    }
}
