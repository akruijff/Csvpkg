package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.exceptions.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class CatTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The cat help is shown when help command is followed by the clear argument")
    public void help() {
        Command command = new Help(new Cat());
        command.execute("help", "cat");

        String actual = error.toString();
        assertTrue(actual.contains("cat "));
    }

    @Test
    @DisplayName ("The cat help is shown when the cat command is provided with to few arguments")
    public void toFewArguments() {
        Command command = new Cat();
        command.execute("cat");

        String actual = error.toString();
        assertTrue(actual.contains("cat "));
    }

    @Test
    public void fileNotFound() {
        assertThrows(FileNotFound.class, () -> {
            Command command = new Cat();
            command.execute("cat", "build/resources/test/non_existing.csv");
        }, "File not found: build/resources/test/non_existing.csv");
    }

    @Test
    public void fileFound() throws IOException {
        Command command = new Cat();
        command.execute("cat", "build/resources/test/example.csv");

        String expected = """
                "name","date","count","description","replaces_product","approved"
                "Inflatable Elephant, African","2013-09-23","5","Found in Africa.
                They live in dense forests, mopane and miombo woodlands, Sahelian scrub or deserts.","null","true"
                "Large Mouse","2013-08-19","3","A "largish" mouse","General Mouse","false"
                """;
        String actual = output.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }
}
