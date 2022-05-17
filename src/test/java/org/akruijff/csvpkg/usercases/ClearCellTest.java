package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.exceptions.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClearCellTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The clear help is shown when help command is followed by the clear argument")
    public void help() {
        Command command = new Help(new ClearCell());
        command.execute("help", "clear");

        String actual = error.toString();
        assertTrue(actual.contains("clear "));
    }

    @Test
    @DisplayName ("The clear help is shown when the clear command is provided with to few arguments")
    public void toFewArguments() {
        Command command = new ClearCell();
        command.execute("clear");

        String actual = error.toString();
        assertTrue(actual.contains("clear "));
    }

    @Test
    @DisplayName ("The clear command throws an exception when the condition column is not found")
    public void conditionColumnNotFound() {
        assertThrows(ColumnNotFound.class, () -> {
            Command command = new ClearCell();
            command.execute("clear", "Z", "D", "a[12]");
        }, "Column 'Z' not found.");
    }

    @Test
    @DisplayName ("The clear command changes nothing when the modification column is not found")
    public void modificationColumnNotFound() throws IOException {
        assertThrows(ColumnNotFound.class, () -> {
            Command command = new ClearCell();
            command.execute("clear", "A", "Z", "a[12]");
        }, "Column 'Z' not found.");
    }

    @Test
    @DisplayName ("The clear command alters no rows then none of the condition columns matches the pattern")
    public void noMatches() throws IOException {
        Command command = new ClearCell();
        command.execute("clear", "A", "E", "a5");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);
    }

    @Test
    @DisplayName ("The clear command clears modification column for rows of which the pattern matches the condition column")
    public void matches() throws IOException {
        Command command = new ClearCell();
        command.execute("clear", "B", "E", "b[23]");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d",""
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);
    }

    @Test
    @DisplayName ("The clear command clears modification column for rows of which the pattern matches the condition column")
    public void matchCaseInsensitive() throws IOException {
        Command command = new ClearCell();
        command.execute("clear", "B", "E", "b[23]", "i");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d",""
                "a3","B3","c","e",""
                "a4","B4","c","e","e4"
                """);
    }
}
