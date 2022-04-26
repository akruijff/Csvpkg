package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GenericHelpTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The generic help is shown when there aren't any argument")
    public void help0() {
        Command command = new Help(new GenericHelp());
        command.execute();

        String actual = error.toString();
        assertTrue(actual.contains("usage: "));
    }

    @Test
    @DisplayName ("The generic help is shown when help command is not followed by any argument")
    public void help1() {
        Command command = new Help(new GenericHelp());
        command.execute("help");

        String actual = error.toString();
        assertTrue(actual.contains("usage: "));
    }

    @Test
    @DisplayName ("The generic help is shown when help command is followed by the help argument")
    public void help2() {
        Command command = new Help(new Help(new GenericHelp()));
        command.execute("help", "help");

        String actual = error.toString();
        assertTrue(actual.contains("usage: "));
    }
}
