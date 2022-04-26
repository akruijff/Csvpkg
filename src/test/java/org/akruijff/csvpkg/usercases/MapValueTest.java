package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.exceptions.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class MapValueTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The map help is shown when help command is followed by the map argument")
    public void help() {
        Command command = new Help(new MapValue());
        command.execute("help", "map");

        String actual = error.toString();
        assertTrue(actual.contains("map "));
    }

    @Test
    @DisplayName ("The map help is shown when the clear command is provided with to few arguments")
    public void toFewArguments() {
        Command command = new MapValue();
        command.execute("map");

        String actual = error.toString();
        assertTrue(actual.contains("map "));
    }
    @Test
    @DisplayName ("The clear command throws an exception when the condition column is not found")
    public void conditionColumnNotFound() {
        assertThrows(ColumnNotFound.class, () -> {
            Command command = new ClearCell();
            command.execute("map", "Z", "D", "a[12]");
        });
    }

    @Test
    @DisplayName ("The clear command changes nothing when the modification column is not found")
    public void modificationColumnNotFound() throws IOException {
        assertThrows(ColumnNotFound.class, () -> {
            Command command = new ClearCell();
            command.execute("map", "A", "Z", "a[12]");
        });
    }

    @Test
    @DisplayName ("The map command leaves the sheet untouched when no match is found")
    public void noMatch() throws IOException {
        Command command = new MapValue();
        command.execute("map", "B", "C", "X", "Z[24]");

        String expected = """
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """;
        String actual = output.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }

    @Test
    @DisplayName ("The map command replaces the modification column with the literal text when a match is found")
    public void matchDefault() throws IOException {
        Command command = new MapValue();
        command.execute("map", "B", "C", "X", "b[24]");

        String expected = """
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","X","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """;
        String actual = output.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }

    @Test
    @DisplayName ("The map command replaces the modification column with the literal text when a match is found")
    public void matchCaseInsensitive() throws IOException {
        Command command = new MapValue();
        command.execute("map", "B", "C", "X", "b[24]", "i");

        String expected = """
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","X","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","X","e","e4"
                """;
        String actual = output.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }
}
