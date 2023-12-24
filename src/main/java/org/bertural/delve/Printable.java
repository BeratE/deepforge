package org.bertural.delve;

import java.io.PrintWriter;

public interface Printable {
    String getText();
    void print(PrintWriter writer);
}
