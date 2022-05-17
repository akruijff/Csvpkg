package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveRowTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The remove help is shown when help command is followed by the remove argument")
    public void help() {
        Command command = new Help(new RemoveRow());
        command.execute("help", "remove");

        String actual = error.toString();
        assertTrue(actual.contains("remove "));
    }

    @Test
    @DisplayName ("The remove help is shown when the clear command is provided with to few arguments")
    public void toFewArguments() {
        Command command = new RemoveRow();
        command.execute("remove", "B");

        String actual = error.toString();
        assertTrue(actual.contains("remove "));
    }

    @Test
    @DisplayName ("The remove command removes rows that match the search pattern")
    public void matchDefault() throws IOException {
        Command command = new RemoveRow();
        command.execute("remove", "B", "b[24]");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);
    }

    @Test
    @DisplayName ("The remove command removes rows that match the search pattern")
    public void matchCaseInsensitive() throws IOException {
        Command command = new RemoveRow();
        command.execute("remove", "B", "b[13]", "i");

        assertOutput("""
                "A","B","C","D","E"
                "a2","b2","c","d","2.5"
                "a4","B4","c","e","e4"
                """);
    }
}
