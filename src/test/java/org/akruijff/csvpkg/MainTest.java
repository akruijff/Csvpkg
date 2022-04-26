package org.akruijff.csvpkg;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    protected OutputStream error;
    private PrintStream oldErr;

    @BeforeEach
    public void setup() {
        oldErr = System.err;
        System.setErr(new PrintStream(error = new ByteArrayOutputStream()));
    }

    @AfterEach
    public void teardown() {
        System.setOut(oldErr);
    }

    @Test
    public void noArguments() {
        Main.main(new String[]{});

        String actual = error.toString();
        assertTrue(actual.contains("usage: "));
    }

    @ParameterizedTest
    @CsvSource ({"help", "man"})
    public void singleHelpArgument(String command) {
        Main.main(new String[]{command});

        String actual = error.toString();
        assertTrue(actual.contains("usage: "));
    }

    @ParameterizedTest
    @CsvSource ({"cat", "clear", "invert", "map", "remove", "select", "sum"})
    public void helpArgumentWithCommand(String command) {
        Main.main(new String[]{"help", command});

        String actual = error.toString();
        assertTrue(actual.contains(command + ' '));
    }

    @Test
    public void cat() {
        Main.main(new String[]{"help", "cat"});
    }
}
