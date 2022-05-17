package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.exceptions.*;
import org.akruijff.csvpkg.usercases.util.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExecuteTest extends BasisSheetSetup {
    @Test
    @DisplayName ("The execute help is shown when help command is followed by the clear argument")
    public void help() {
        Command command = new Help(new Execute());
        command.execute("help", "execute");

        String actual = error.toString();
        assertTrue(actual.contains("execute "));
    }

    @Test
    @DisplayName ("The execute help is shown when the cat command is provided with to few arguments")
    public void tooFewArguments() {
        Command command = new Execute();
        command.execute("execute");

        String actual = error.toString();
        assertTrue(actual.contains("execute "));
    }

    @Test
    @DisplayName ("The execute command executes the commands within a file")
    public void fileNotFound() {
        assertThrows(FileNotFound.class, () -> {
            Command command = new Execute();
            command.execute("execute", "build/resources/test/data/non_existing.csv");
        }, "File not found: build/resources/test/data/non_existing.csv");
    }

    @Test
    @DisplayName ("The execute command executes the commands within a file")
    public void emptyFile() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/empty.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);
    }

    @Test
    public void tooFewArgumentInFile() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/execute_too_few_arguments.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);
        assertError("Too few arguments in line: \"invert\",\"B\",\"E\"");
    }

    @Test
    @DisplayName ("The execute command executes the clear command within a file")
    public void matchesClear() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/clear.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d",""
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);

    }

    @Test
    @DisplayName ("The execute command executes the clear command within a file")
    public void matchesCat_NonExistingFile() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/cat_non_existing.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);
        assertError("Failed to execute: \"cat\",\"build/resources/test/data/non_existing.csv\"");
    }

    @Test
    @DisplayName ("The execute command executes the clear command within a file")
    public void matchesCat() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/cat_example.csv");

        assertOutput("""
                "name","date","count","description","replaces_product","approved"
                "Inflatable Elephant, African","2013-09-23","5","Found in Africa.
                They live in dense forests, mopane and miombo woodlands, Sahelian scrub or deserts.","null","true"
                "Large Mouse","2013-08-19","3","A \"largish\" mouse","General Mouse","false"
                """);
    }

    @Test
    @DisplayName ("The execute command throws an exception upon recursive calls")
    public void matchExecute_Recursive() throws IOException {
        assertThrows(RecursiveCallDetected.class, () -> {
            Command command = new Execute();
            command.execute("execute", "build/resources/test/execute_recursive.csv");
        }, "Recursive call detected. Caller: build/resources/test/execute_recursive_helper.csv Executing: build/resources/test/execute_recursive.csv");
    }

    @Test
    @DisplayName ("The execute command prints an error for unsupported command")
    public void matchExecute_UnsupportedCommand() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/execute_unsupported_command.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);
        assertError("Unsupported command in line: \"unsupported command\",\"E\",\"C\",\"D\"");
    }

    @Test
    @DisplayName ("The execute command executes commands in another CSV file")
    public void matchExecute() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/execute.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","-1"
                "a2","b2","c","d","-2.5"
                "a3","B3","c","e","-3"
                "a4","B4","c","e","e4"
                """);
    }

    @Test
    @DisplayName ("The invert command inverts modification column for rows of which the pattern matches the condition column")
    public void matchInvert() throws IOException { // String conditionColumn, String pattern, String invertColumn
        Command command = new Execute();
        command.execute("execute", "build/resources/test/invert.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","c","d","-1"
                "a2","b2","c","d","-2.5"
                "a3","B3","c","e","-3"
                "a4","B4","c","e","e4"
                """);
    }

    @Test
    @DisplayName ("The map command replaces the modification column with the literal text when a match is found")
    public void matchMap() throws IOException { // String conditionColumn, String pattern, String invertColumn
        Command command = new Execute();
        command.execute("execute", "build/resources/test/map.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a1","b1","X","d","1"
                "a2","b2","X","d","2.5"
                "a3","B3","X","e","3"
                "a4","B4","c","e","e4"
                """);
    }

    @Test
    @DisplayName ("The remove command removes rows that match the search pattern")
    public void matchRemove() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/remove.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a4","B4","c","e","e4"
                """);
    }


    @Test
    @DisplayName ("The select command keeps the specified columns and removes all other columns")
    public void matchSelect() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/select.csv");

        assertOutput("""
                "A","B"
                "a1","b1"
                "a2","b2"
                "a3","B3"
                "a4","B4"
                """);
    }

    @Test
    @DisplayName ("The sum command sums the modification column for all unique combinations of the selection column")
    public void matchesSum() throws IOException {
        Command command = new Execute();
        command.execute("execute", "build/resources/test/sum.csv");

        assertOutput("""
                "A","B","C","D","E"
                "a4","B4","c","e","e4"
                "a1","b1","c","d","3.5"
                "a3","B3","c","e","3.0"
                """);
    }
}
