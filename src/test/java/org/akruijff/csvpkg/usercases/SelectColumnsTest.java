package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class SelectColumnsTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The filter help is shown when help command is followed by the filter argument")
    public void help() {
        Command command = new Help(new SelectColumns());
        command.execute("help", "select");

        String actual = error.toString();
        assertTrue(actual.contains("select "));
    }

    @Test
    @DisplayName ("The select help is shown when the clear command is provided with to few arguments")
    public void toFewArguments() {
        Command command = new SelectColumns();
        command.execute("select");

        String actual = error.toString();
        assertTrue(actual.contains("select "));
    }

    @Test
    @DisplayName ("The select command returns an empty sheet when no match is found")
    public void noMatches() throws IOException {
        Command command = new SelectColumns();
        command.execute("select", "Z");

        assertOutput("");
    }

    @Test
    @DisplayName ("The select command keeps the specified columns and removes all other columns")
    public void matches() throws IOException {
        Command command = new SelectColumns();
        command.execute("select", "A", "B");

        assertOutput("""
                "A","B"
                "a1","b1"
                "a2","b2"
                "a3","B3"
                "a4","B4"
                """);
    }
}
