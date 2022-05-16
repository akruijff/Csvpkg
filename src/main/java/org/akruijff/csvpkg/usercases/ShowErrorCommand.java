package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.usercases.util.*;

public class ShowErrorCommand extends DummyCommand {
    private final String message;

    public ShowErrorCommand(String message) {
        this.message = message;
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        System.err.println(message);
        return super.doExecute(sheet, args);
    }
}
