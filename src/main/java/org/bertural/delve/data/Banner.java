package org.bertural.delve.data;

import org.bertural.delve.Printable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/** Responsible for loading and caching banners */
public enum Banner implements Printable {
    WELCOME("welcome.txt");

    Banner(String file) {
        this.file = file;
        this.text = readFile(file);
    }

    private String file;
    private String text;

    public String getFile() {
        return file;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void print(PrintWriter writer) {
        writer.println(text);
        writer.flush();
    }

    private static String readFile(String file) {
        InputStream inputStream = Banner.class.getResourceAsStream("/banner/"+file);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        String out = "";
        try {
            out = reader.readLine();
            for (String line; (line = reader.readLine()) != null;) {
                out = out + "\n" + line;
            }
        } catch (IOException e) {
            out = "!!! BANNER UNAVAILABLE !!!";
        }
        return out;
    }


}
