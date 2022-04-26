package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.exceptions.*;
import org.akruijff.csvpkg.usercases.util.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class InvertCellTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The invert help is shown when help command is followed by the invert argument")
    public void help() {
        Command command = new Help(new InvertCell());
        command.execute("help", "invert");

        String actual = error.toString();
        assertTrue(actual.contains("invert "));
    }

    @Test
    @DisplayName ("The invert help is shown when the clear command is provided with to few arguments")
    public void toFewArguments() {
        Command command = new InvertCell();
        command.execute("invert", "A", "a[13]");

        String actual = error.toString();
        assertTrue(actual.contains("invert "));
    }

    @Test
    @DisplayName ("The clear command throws an exception when the condition column is not found")
    public void conditionColumnNotFound() {
        assertThrows(ColumnNotFound.class, () -> {
            Command command = new ClearCell();
            command.execute("invert", "Z", "D", "a[12]");
        });
    }

    @Test
    @DisplayName ("The clear command changes nothing when the modification column is not found")
    public void modificationColumnNotFound() throws IOException {
        assertThrows(ColumnNotFound.class, () -> {
            Command command = new ClearCell();
            command.execute("invert", "A", "Z", "a[12]");
        });
    }

    @Test
    @DisplayName ("The invert command leaves the sheet untouched when no match is found")
    public void noMatches() throws IOException {
        Command command = new InvertCell();
        command.execute("invert", "A", "E", "a4");

        String expected = """
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """;
        String actual = output.toString();
        Assertions.assertEquals(TestUtil.unifyNewLine(expected), TestUtil.unifyNewLine(actual));
    }

    @Test
    @DisplayName ("The invert command inverts modification column for rows of which the pattern matches the condition column")
    public void matchDefault() throws IOException { // String conditionColumn, String pattern, String invertColumn
        Command command = new InvertCell();
        command.execute("invert", "B", "E", "B[1-3]");

        String expected = """
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","-3"
                "a4","B4","c","e","e4"
                """;
        String actual = output.toString();
        Assertions.assertEquals(TestUtil.unifyNewLine(expected), TestUtil.unifyNewLine(actual));
    }

    @Test
    @DisplayName ("The invert command inverts modification column for rows of which the pattern matches the condition column")
    public void matchCaseInsensitive() throws IOException { // String conditionColumn, String pattern, String invertColumn
        Command command = new InvertCell();
        command.execute("invert", "B", "E", "b[124]", "i");

        String expected = """
                "A","B","C","D","E"
                "a1","b1","c","d","-1"
                "a2","b2","c","d","-2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """;
        String actual = output.toString();
        Assertions.assertEquals(TestUtil.unifyNewLine(expected), TestUtil.unifyNewLine(actual));
    }
}
