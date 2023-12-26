package org.bertural.delve.telnet;

import org.bertural.delve.Printable;

import java.io.PrintWriter;

public enum EscapeCode implements Printable {
    CURSOR_UP("\033[M"),
    CURSOR_SAVE("\033[s"),
    CURSOR_RESET("\033[H"),
    CURSOR_RESTORE("\033[s"),

    CLEAR_SCREEN_BEG("\033[0J"),     // erase from cursor to beginning of screen
    CLEAR_SCREEN_END("\033[0J"),     // erase from cursor until end of screen
    CLEAR_LINE_BEG("\033[0K"),       // erase from cursor to end of line
    CLEAR_LINE_END("\033[1K"),       // erase start of line to the cursor
    CLEAR_SCREEN("\033[2J"),
    CLEAR_LINE("\033[2K"),              // erase the entire line

    FONT_RESET("\033[0m"),             // reset all modes (styles and colors)
    FONT_BOLD_SET("\033[1m"),          // set bold mode.
    FONT_BOLD_RES("\033[22m"),
    FONT_LINE_SET("\033[4m"),          // set underline mode.
    FONT_LINE_RES("\033[24m"),
    FONT_BLINK_SET("\033[5m"),         // set blinking mode
    FONT_BLINK_RES("\033[25m"),
    FONT_FAINT_SET("\033[2m"),         // set dim/faint mode.
    FONT_FAINT_RES("\033[22m"),
    FONT_ITALIC_SET("\033[3m"),        // set italic mode.
    FONT_ITALIC_RES("\033[23m"),
    FONT_INVERSE_SET("\033[7m"),       // set inverse/reverse mode
    FONT_INVERSE_RES("\033[27m"),
    FONT_HIDDEN_SET("\033[8m"),        // set hidden/invisible mode
    FONT_HIDDEN_RES("\033[28m"),
    FONT_STRIKE_SET("\033[9m"),        // set strikethrough mode.
    FONT_STRIKE_RES("\033[29m"),

    COLOR_FG_BLACK("\033[30m"),
    COLOR_BG_BLACK("\033[40m"),
    COLOR_FG_RED("\033[31m"),
    COLOR_BG_RED("\033[41m"),
    COLOR_FG_GREEN("\033[32m"),
    COLOR_BG_GREEN("\033[42m"),
    COLOR_FG_YELLOW("\033[33m"),
    COLOR_BG_YELLOW("\033[43m"),
    COLOR_FG_BLUE("\033[34m"),
    COLOR_BG_BLUE("\033[44m"),
    COLOR_FG_MAGENTA("\033[35m"),
    COLOR_BG_MAGENTA("\033[45m"),
    COLOR_FG_CYAN("\033[36m"),
    COLOR_BG_CYAN("\033[46m"),
    COLOR_FG_WHITE("\033[37m"),
    COLOR_BG_WHITE("\033[47m"),
    COLOR_FG_DEFAULT("\033[39m"),
    COLOR_BG_DEFAULT("\033[49m");


    /** TODO
     *  Combine codes: \x1b[1;31mHello  Set style to bold, red foreground.
     *  Include parameters: ESC[{line};{column}f	moves cursor to line #, column #
     */


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
