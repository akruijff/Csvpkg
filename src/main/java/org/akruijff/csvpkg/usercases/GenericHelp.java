package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;

public class GenericHelp extends AbstractCommand {
    @Override
    protected boolean isInsufficient(String[] args) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doHelp(String[] args) {
        System.err.println("""
                usage: java -jar <path/to/csvpkg.jar> <command> <command arguments>
                                
                usage: help <command>
                                
                commands: cat, clear, invert, map, remove, select, and sum                
                """);
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        throw new UnsupportedOperationException();
    }
}
