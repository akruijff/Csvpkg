package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.exceptions.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class SumColumnTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The sum help is shown when help command is followed by the sum argument")
    public void help() {
        Command command = new Help(new SumColumn());
        command.execute("help", "sum");

        String actual = error.toString();
        assertTrue(actual.contains("sum "));
    }

    @Test
    @DisplayName ("The sum help is shown when the clear command is provided with to few arguments")
    public void toFewArguments() {
        Command command = new SumColumn();
        command.execute("sum");

        String actual = error.toString();
        assertTrue(actual.contains("sum "));
    }

    @Test
    @DisplayName ("The sum command throws an exception when a column can not be found")
    public void noMatches() {
        assertThrows(ColumnNotFound.class, () -> {
            Command command = new SumColumn();
            command.execute("sum", "A", "Z");
        });
    }

    @Test
    @DisplayName ("The sum command sums the modification column for all unique combinations of the selection column")
    public void matches() throws IOException {
        Command command = new SumColumn();
        command.execute("sum", "E", "C", "D");

        String expected = """
                "A","B","C","D","E"
                "a4","B4","c","e","e4"
                "a1","b1","c","d","3.5"
                "a3","B3","c","e","3.0"
                """;
        String actual = output.toString();
        Assertions.assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }
}
